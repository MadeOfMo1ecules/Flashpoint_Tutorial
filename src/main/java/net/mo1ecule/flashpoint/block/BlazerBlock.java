package net.mo1ecule.flashpoint.block;

import com.petrolpark.destroy.block.shape.DestroyShapes;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock.HeatLevel;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import net.mo1ecule.flashpoint.block.entity.BlazerBlockEntity;
import net.mo1ecule.flashpoint.block.entity.BlazerBlockEntity.BlazeLevel;
import net.mo1ecule.flashpoint.block.entity.FlashpointBlockEntityTypes;

public class BlazerBlock extends Block implements IBE<BlazerBlockEntity> {

    public static final EnumProperty<BlazeLevel> BLAZE_LEVEL = EnumProperty.create("blazer", BlazeLevel.class);
    public static final IntegerProperty BURNRATE = IntegerProperty.create("burnrate", 0, 100);

    public BlazerBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(BLAZE_LEVEL, BlazeLevel.IDLE)
            .setValue(BlazeBurnerBlock.HEAT_LEVEL, HeatLevel.NONE)
            .setValue(BURNRATE, 0));
    };

    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BLAZE_LEVEL, BlazeBurnerBlock.HEAT_LEVEL, BURNRATE);
    };

    public static int getBurnRate(BlockState state) {
        if (state.hasProperty(BlazerBlock.BURNRATE))
            return state.getValue(BlazerBlock.BURNRATE);
        else return 0;
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState p_220082_4_, boolean p_220082_5_) {
        withBlockEntityDo(level, pos, be -> be.updateBlazeLevel(state.getValue(BLAZE_LEVEL)));
        
        if (level.isClientSide()) return;
        BlockEntity blockEntity = level.getBlockEntity(pos.above()); // Check for a Basin
        if (!(blockEntity instanceof BasinBlockEntity)) return;
        BasinBlockEntity basin = (BasinBlockEntity) blockEntity;
        basin.notifyChangeOfContents(); // Let the Basin know there's now a Cooler
    };

    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult blockRayTraceResult) {
        ItemStack stack = player.getItemInHand(hand);
        if (AllItems.CREATIVE_BLAZE_CAKE.isIn(stack)) {
            withBlockEntityDo(world, pos, blazer -> {
                if (getBlazeLevelOf(state) == BlazeLevel.BLAZING) {
                    blazer.blazingTicks = 0;
                    blazer.setBlazeOfBlock(BlazeLevel.IDLE);
                } else  {
                    blazer.blazingTicks = Integer.MAX_VALUE;
                    blazer.setBlazeOfBlock(BlazeLevel.BLAZING);
                };
            });
            if (!player.isCreative()) stack.shrink(1);
            player.setItemInHand(hand, stack);
            return InteractionResult.sidedSuccess(world.isClientSide());
        };
        return InteractionResult.PASS;
    };

    public static InteractionResultHolder<ItemStack> tryInsert(BlockState state, Level world, BlockPos pos, ItemStack stack, boolean doNotConsume, boolean forceOverflow, boolean simulate) { //this is an override
        return InteractionResultHolder.fail(ItemStack.EMPTY);
    };

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState();
    };

    @Override
    public VoxelShape getShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
        return DestroyShapes.COOLER;
    };

    @Override
	public VoxelShape getCollisionShape(BlockState blockState, BlockGetter level, BlockPos pos, CollisionContext context) {
		if (context == CollisionContext.empty()) return AllShapes.HEATER_BLOCK_SPECIAL_COLLISION_SHAPE;
		return getShape(blockState, level, pos, context);
	};

    public static BlazeLevel getBlazeLevelOf(BlockState blockState) {
        return blockState.getValue(BLAZE_LEVEL);
    };

    @Override
    public Class<BlazerBlockEntity> getBlockEntityClass() {
        return BlazerBlockEntity.class;
    };

    @Override
    public BlockEntityType<? extends BlazerBlockEntity> getBlockEntityType() {
        return FlashpointBlockEntityTypes.BLAZER.get();
    };

    @Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	};
    
};
