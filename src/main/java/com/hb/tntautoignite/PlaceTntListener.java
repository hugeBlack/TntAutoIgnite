package com.hb.tntautoignite;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.PrimedTnt;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class PlaceTntListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.TNT) {
            Block tntBlock = e.getBlock();
            Level world = ((CraftWorld) e.getBlock().getWorld()).getHandle();
            BlockPos blockPosition = new BlockPos(tntBlock.getX(), tntBlock.getY(), tntBlock.getZ());
            LivingEntity entityLiving = ((CraftLivingEntity) e.getPlayer()).getHandle();
            //从TntBlock.class的explode方法抄的，略有修改
            PrimedTnt entityTntPrimed = new PrimedTnt(world, blockPosition.getX() + 0.5, blockPosition.getY(), blockPosition.getZ() + 0.5, entityLiving);
            world.addFreshEntity(entityTntPrimed);
            world.playSound(null, entityTntPrimed.getX(), entityTntPrimed.getY(), entityTntPrimed.getZ(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 1.0F, 1.0F);
            world.gameEvent(entityLiving, GameEvent.PRIME_FUSE, blockPosition);
            //设置fuseTick
            entityTntPrimed.setFuse(TntAutoIgnite.blockTntFuseTick);
            tntBlock.setType(Material.AIR);
        }
    }
}
