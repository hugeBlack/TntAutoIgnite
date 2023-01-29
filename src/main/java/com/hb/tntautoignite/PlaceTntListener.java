package com.hb.tntautoignite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TntBlock;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public class PlaceTntListener implements Listener {

    public Method tntExplodeMethod;

    PlaceTntListener() {
        try {
            Class<?> tntClass = TntBlock.class;//find explode method by its accessibility and parameter types
            Method[] methods = tntClass.getDeclaredMethods();
            for (Method method : methods) {
                if (method.getModifiers() != 10) continue; //private static
                Type[] params = method.getParameterTypes();
                if (method.getReturnType() != void.class || params.length != 3) continue;
                if (params[0] == Level.class && params[1] == BlockPos.class && params[2] == LivingEntity.class) {
                    tntExplodeMethod = method;
                    tntExplodeMethod.setAccessible(true);
                    Bukkit.getLogger().log(java.util.logging.Level.INFO, "[TAI] Explode method found: " + method.toGenericString());
                    return;
                }
            }
            Bukkit.getLogger().log(java.util.logging.Level.SEVERE, "[TAI] Explode method not found.");
            Bukkit.getPluginManager().disablePlugin(TntAutoIgnite.thePlugin);
        } catch (Exception ex) {
            ex.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(TntAutoIgnite.thePlugin);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.TNT) {
            Block tntBlock = e.getBlock();
            try {
                BlockPos pos = new BlockPos(tntBlock.getX(), tntBlock.getY(), tntBlock.getZ());
                tntExplodeMethod.invoke(null, ((CraftWorld) e.getBlock().getWorld()).getHandle(), pos, ((CraftLivingEntity) e.getPlayer()).getHandle());
                (((CraftWorld) e.getBlock().getWorld()).getHandle()).removeBlock(pos, false);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
