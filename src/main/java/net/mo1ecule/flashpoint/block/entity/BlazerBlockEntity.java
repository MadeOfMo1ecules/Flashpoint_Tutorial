package net.mo1ecule.flashpoint.block.entity;

import com.google.common.collect.ImmutableList;
import com.ibm.icu.text.DecimalFormat;
import com.petrolpark.destroy.chemistry.legacy.LegacySpecies;
import com.petrolpark.destroy.chemistry.legacy.ReadOnlyMixture;
import com.petrolpark.destroy.fluid.DestroyFluids;
import com.petrolpark.destroy.util.DestroyTags.DestroyFluidTags;
import com.petrolpark.destroy.util.PollutionHelper;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsBoard;
import com.simibubi.create.foundation.blockEntity.behaviour.ValueSettingsFormatter;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.scrollValue.ScrollValueBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;
import net.mo1ecule.flashpoint.chemistry.legacy.index.FlashpointMolecules;
import net.mo1ecule.flashpoint.config.FlashpointAllConfigs;
import net.mo1ecule.flashpoint.util.FlashpointLang;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.mo1ecule.flashpoint.block.BlazerBlock;

import java.util.List;

// Much of this code was taken from Destroy's CoolerBlockEntity class and heavily modified
public class BlazerBlockEntity extends SmartBlockEntity implements IHaveGoggleInformation {

    public ScrollValueBehaviour burnRateBehaviour;
    private SmartFluidTankBehaviour tank;

    public float blazingTicks; // How many ticks this Bunsen Blazer has left to blaze for
    //public int maxBurnRate = 250; // Max burn rate of the Bunsen Blazer in mB/s, it didn't like this for some reason
    private static final int TANK_CAPACITY = 1000;

    protected LerpedFloat headAnimation;
	protected LerpedFloat headAngle;

    public BlazerBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);

        blazingTicks = 0f;
        headAnimation = LerpedFloat.linear();
		headAngle = LerpedFloat.angular();
        headAngle.startWithValue((AngleHelper.horizontalAngle(Direction.NORTH) + 180) % 360);
    };

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        tank = new SmartFluidTankBehaviour(SmartFluidTankBehaviour.INPUT, this, 1, TANK_CAPACITY, true)
            .whenFluidUpdates(this::consumeFluid)
            .forbidExtraction();
        behaviours.add(tank);

        burnRateBehaviour = new ScrollValueBehaviour(Component.translatable("block.flashpoint.blazer.burn_rate"),
                            // This is the title of the scroll bar, appears above it
                            this, new BlazerValueSlot()) {
            @Override
            public ValueSettingsBoard createBoard(Player player, BlockHitResult hitResult) {
                return new ValueSettingsBoard(label, max, 10,
                ImmutableList.of(Component.translatable("block.flashpoint.blazer.burn_rate_unit")), // Appears left of bar, should be units
                new ValueSettingsFormatter(this::formatSettings));
            };
            public MutableComponent formatSettings(ValueSettings burnRateBehaviour) {
                return Components.literal(df.format(burnRateBehaviour.value()) + "%"); // This is displayed on the little scroll thing that players set
            };
        }//timeRemaining = "" + minutes + ":" + ((seconds % 60) < 10 ? "0" : "") + (seconds % 60);
        .between(0, 100);
        burnRateBehaviour.setValue(0);
        behaviours.add(burnRateBehaviour);
    };

    public static class BlazerValueSlot extends ValueBoxTransform.Sided {

       // @Override
       // protected boolean isSideActive(BlockState state, Direction direction) {
       //     return state.getValue(BlazerBlock.FACING).getAxis() != direction.getAxis();
       // };

        @Override
        protected Vec3 getSouthLocation() {
            return VecHelper.voxelSpace(8d, 8d, 12.5d);
        };

    };

    private static DecimalFormat df = new DecimalFormat();
    static {
        df.setMinimumFractionDigits(1);
        df.setMaximumFractionDigits(1);
    };


    /**
     * Use up the Fluid in this Blazer, turning it into ticks of heating.
     */
    @SuppressWarnings("deprecation")
    private void consumeFluid() {
        if (!hasLevel()) return;

        float blazingPower = 0f;

        FluidStack fluidStack = tank.getPrimaryHandler().getFluid();
        if (DestroyFluids.isMixture(fluidStack)) {

            int amount = fluidStack.getAmount();
            ReadOnlyMixture mixture = ReadOnlyMixture.readNBT(ReadOnlyMixture::new, fluidStack.getOrCreateChildTag("Mixture"));

            float totalMolesPerBucket = 0f;
            float totalCombustibleMolesPerBucket = 0f;
            for (LegacySpecies molecule : mixture.getContents(true)) {
                float concentration = mixture.getConcentrationOf(molecule);
                totalMolesPerBucket += concentration;
                if (molecule.hasTag(FlashpointMolecules.Tags.COMBUSTIBLE)) {
                    totalCombustibleMolesPerBucket += concentration;
                    blazingPower += FlashpointAllConfigs.SERVER.blocks.blazerEfficiency.getF() * concentration * amount * 300/*molecule.getHeatOfCombustion()*/ / 100;
                };
            };

            if (FlashpointAllConfigs.SERVER.blocks.blazerEnhancedByPurity.get()) blazingPower *= totalCombustibleMolesPerBucket / totalMolesPerBucket; // Scale the effectiveness of the combustible with its purity

            
        } else if (fluidStack.getFluid().is(DestroyFluidTags.COOLANT.tag)) { // Needs to be changed to a combustible tag, for things like crude oil
            blazingPower += fluidStack.getAmount(); // One bucket of coolant = 50 seconds of cooling
        }

        if (blazingPower > 0f) {

            // Begin blazing as long as the Burner is turned on
            if(burnRateBehaviour.getValue() != 0) {
                setBlazeOfBlock(BlazeLevel.BLAZING);
            }
            blazingTicks += blazingPower;

            // Stop insertion if necessary
            if (blazingTicks >= getMaxBlazingTicks()) {
                tank.forbidInsertion();
            };
        };

        // Discard the Fluid
        tank.getPrimaryHandler().drain(TANK_CAPACITY, FluidAction.EXECUTE);
        PollutionHelper.pollute(getLevel(), getBlockPos(), fluidStack);

        notifyUpdate();
    };

    @Override
    @SuppressWarnings("null")
    public void tick() {
        super.tick();
        if (!hasLevel()) return;

        if (getLevel().isClientSide()) { // Level is not null I checked
            if (!isVirtual()) {
                spawnParticles(getBlazeFromBlock());
            };
            return;
        };
        if (blazingTicks > 0) {
            blazingTicks -= (float)burnRateBehaviour.getValue();
            if (blazingTicks < getMaxBlazingTicks()) {
                tank.allowInsertion();
            };
            if (blazingTicks <= 0) {
                //blazingTicks = 0;
                setBlazeOfBlock(BlazeLevel.IDLE);
            };
            BlockState oldState = getBlockState();
            oldState = oldState.setValue(BlazerBlock.BURNRATE, burnRateBehaviour.getValue());
            getLevel().setBlockAndUpdate(getBlockPos(), oldState); // This is the bit it thinks might be null
            sendData();
        };
    };

    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        blazingTicks = tag.getFloat("Timer");
    };

    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        super.write(tag, clientPacket);
        tag.putFloat("Timer", blazingTicks);
    };

//    public SmartFluidTank getInputTank() {
//        return tank.getPrimaryHandler();
//    };
//
//    public LerpedFloat getHeadAnimation() {
//        return headAnimation;
//    };
//
//    public LerpedFloat getHeadAngle() {
//        return headAngle;
//    };

    /**
     * Essentially trick the game into thinking this is a Blaze Burner.
     * The 'BLAZING' value gets {@link net.mo1ecule.flashpoint.mixin.HeatLevelMixin mixed in} to the Heat Level enum.
     * @param blazeLevel
     */
    @SuppressWarnings("null")
    public void updateBlazeLevel(BlazeLevel blazeLevel) {
        if (!hasLevel()) return;
        BlockState newState = getBlockState().setValue(BlazeBurnerBlock.HEAT_LEVEL, blazeLevel == BlazeLevel.BLAZING ? HeatLevel.valueOf("BLAZING") : HeatLevel.NONE);
        if (!newState.equals(getBlockState())) {
            getLevel().setBlockAndUpdate(getBlockPos(), newState); // Level is not null
        };
    };

    @SuppressWarnings("null")
    protected void spawnParticles(BlazeLevel blazeLevel) {
		if (!hasLevel()) return;
		if (blazeLevel == BlazeLevel.NONE || blazeLevel == BlazeLevel.IDLE || burnRateBehaviour.getValue() == 0) return;

		RandomSource r = getLevel().getRandom(); // It thinks getLevel() might be null (it's not)

		Vec3 c = VecHelper.getCenterOf(getBlockPos());
		Vec3 v = c.add(VecHelper.offsetRandomly(Vec3.ZERO, r, .0625f)
			.multiply(1, 0, 1));

//		if (r.nextInt(blazeLevel == BlazeLevel.IDLE ? 32 : 2) != 0) return; // Spawn more Particles if we're Frosting vs not doing anything

		//boolean empty = level.getBlockState(getBlockPos().above()) // If the Block above is occupied we want to spawn fewer Particles
		//	.getCollisionShape(getLevel(), getBlockPos().above())
		//	.isEmpty();

        float partWanderX = r.nextInt(3)-1; // Should generate either -1, 0, or 1 to give particles some random movement
        float partWanderZ = r.nextInt(3)-1; // Should generate either -1, 0, or 1 to give particles some random movement


        if(burnRateBehaviour.getValue() <= 25){
            if (r.nextInt(2) == 0) getLevel().addParticle(ParticleTypes.SMALL_FLAME, v.x, v.y+0.15, v.z, (double)(0.0075*partWanderX), 0.02D, (double)0.0075*partWanderZ);
        }else if(burnRateBehaviour.getValue() <= 50){
            if (r.nextInt(2) == 0) getLevel().addParticle(ParticleTypes.FLAME, v.x, v.y+0.1, v.z, 0, 0.02D, 0);
        }else if(burnRateBehaviour.getValue() <= 75){
            if (r.nextInt(1) == 0) getLevel().addParticle(ParticleTypes.FLAME, v.x, v.y + 0.3, v.z, 0, 0.005D, 0);
        }else{
            if (r.nextInt(1) == 0) getLevel().addParticle(ParticleTypes.SOUL_FIRE_FLAME, v.x, v.y + 0.3, v.z, 0, 0.005D, 0);
        }
	};

    /**
     * Whether the Blazer interacts with the Block State above it.
     */
    @SuppressWarnings("null")
    private boolean validBlockAbove() {
        if (!hasLevel()) return false;
        BlockState blockState = getLevel().getBlockState(worldPosition.above()); // Level isn't null I checked
		return AllBlocks.BASIN.has(blockState) || blockState.getBlock() instanceof FluidTankBlock;
    };

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.FLUID_HANDLER && side == Direction.DOWN) {
            return tank.getCapability().cast();
        };
        return super.getCapability(cap, side);
    };

    @Override
    public void invalidate() {
        super.invalidate();
        tank.getCapability().invalidate();
    };

    public BlazeLevel getBlazeFromBlock() {
        return BlazerBlock.getBlazeLevelOf(getBlockState());
    };

    @SuppressWarnings("null")
    public void setBlazeOfBlock(BlazeLevel blazeLevel) {
        if (!hasLevel()) return;
        getLevel().setBlockAndUpdate(getBlockPos(), getBlockState().setValue(BlazerBlock.BLAZE_LEVEL, blazeLevel)); // It thinks getLevel() might be null (it's not)
        updateBlazeLevel(blazeLevel);
    };

    public enum BlazeLevel implements StringRepresentable {
        NONE,
        IDLE,
        FROSTING,
        BLAZING;

        @Override
        public String getSerializedName() {
            return Lang.asId(name());
        };
    };

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        if (blazingTicks <= 0) return false;
        final String timeRemaining;
        if (blazingTicks < ((float)Integer.MAX_VALUE*3/4) && burnRateBehaviour.getValue() != 0) {
            int seconds = (int)blazingTicks / 20 / burnRateBehaviour.getValue(); // Number of seconds left
            int minutes = seconds / 60; // Number of minutes left
            timeRemaining = "" + minutes + ":" + ((seconds % 60) < 10 ? "0" : "") + (seconds % 60);
            //timeRemaining = "" + minutes + ":" + (seconds < 10 ? "0" : "") + seconds; // MM:SS
        } else {
            timeRemaining = FlashpointLang.translate("tooltip.blazer.long_time_remaining").string();
        };
        FlashpointLang.translate("tooltip.blazer.time_remaining", timeRemaining)
            .forGoggles(tooltip);
        return true;
    };

    /**
     * How much the Blazer will fill before stopping any excess Fluid
     * @return 500 minutes at 1 mB/s by default?
     */
    public int getMaxBlazingTicks() {
        return FlashpointAllConfigs.SERVER.blocks.maximumBlazingTicks.get();
    };
    
};
