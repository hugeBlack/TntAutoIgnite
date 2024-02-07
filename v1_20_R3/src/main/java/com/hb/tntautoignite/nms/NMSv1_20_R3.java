package com.hb.tntautoignite.nms;

import net.minecraft.core.BlockPosition;
import net.minecraft.server.level.WorldServer;
import net.minecraft.sounds.SoundCategory;
import net.minecraft.sounds.SoundEffects;
import net.minecraft.world.entity.EntityLiving;
import net.minecraft.world.entity.item.EntityTNTPrimed;
import net.minecraft.world.entity.player.EntityHuman;
import net.minecraft.world.entity.projectile.EntityThrownTrident;
import net.minecraft.world.level.gameevent.GameEvent;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_20_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftTrident;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Trident;

public class NMSv1_20_R3 implements NMS{
    @Override
    public void summonTnt(Location location, LivingEntity ignitePlayer, int fuseTicks) {

        WorldServer world = ((CraftWorld) location.getBlock().getWorld()).getHandle();
        BlockPosition blockPosition = new BlockPosition((int) location.getX(), (int) location.getY(), (int) location.getZ());
        EntityLiving entityLiving = ((CraftLivingEntity) ignitePlayer).getHandle();
        //从TntBlock.class的explode方法抄的，略有修改
        EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed(world, (double)blockPosition.u() + 0.5, (double)blockPosition.v(), (double)blockPosition.w() + 0.5, entityLiving);
        world.b(entityTNTPrimed);
        // world.playSound
        world.a((EntityHuman)null, entityTNTPrimed.dr(), entityTNTPrimed.dt(), entityTNTPrimed.dx(), SoundEffects.yB, SoundCategory.e, 1.0F, 1.0F);
        // world.gameEvent
        world.a(entityLiving, GameEvent.I, blockPosition);

        if(fuseTicks > -1){
            // EntityTNTPrimed.setFuse
            entityTNTPrimed.b(fuseTicks);
        }


    }

    @Override
    public boolean checkTridentChannelling(Trident trident) {
        EntityThrownTrident nmsTrident =((CraftTrident) trident).getHandle();
        return nmsTrident.M();
    }
}
