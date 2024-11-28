package net.mo1ecule.flashpoint.item.custom;

import com.petrolpark.destroy.config.DestroyAllConfigs;
import com.petrolpark.destroy.config.DestroySubstancesConfigs;
import com.petrolpark.destroy.effect.DestroyMobEffects;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

//
public class BottledDrinkItem extends DrinkItem {

    private ItemStack container;

    public BottledDrinkItem(Properties properties, ItemStack container) {
        super(properties);
        this.container = container;
    };

    // Copied from Destroy's code
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
        super.finishUsingItem(stack, level, entityLiving);

        if (entityLiving instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        };

        if (stack.isEmpty()) {
//          return new ItemStack(Items.GLASS_BOTTLE);
            return container;
        } else {
            if (entityLiving instanceof Player player && !player.getAbilities().instabuild) {
//                ItemStack itemstack = new ItemStack(Items.GLASS_BOTTLE);
                if (!player.getInventory().add(container)) {
                    player.drop(container, false);
                }
            }
            return stack;
        }
    };

}
