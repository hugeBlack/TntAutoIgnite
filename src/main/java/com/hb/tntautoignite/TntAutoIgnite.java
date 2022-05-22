package com.hb.tntautoignite;

import net.minecraft.commands.arguments.ParticleArgument;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.HashSet;
import java.util.logging.Level;

public final class TntAutoIgnite extends JavaPlugin {
    public static HashSet<Entity> bombArrowSet = new HashSet<>();
    public static HashSet<Entity> tntSet = new HashSet<>();
    public static Plugin thePlugin = null;

    @Override
    public void onEnable() {
        thePlugin = this;
        Bukkit.getPluginManager().registerEvents(new PlaceTntListener(), this);
        Bukkit.getPluginManager().registerEvents(new BombArrowListener(),this);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, () -> {
            Entity lastOne = null;
            for(Entity bombArrow: bombArrowSet){

                if(lastOne!=null) bombArrowSet.remove(lastOne);
                bombArrow.getWorld().spawnParticle(Particle.SMOKE_LARGE,bombArrow.getLocation(),100,0,0,0,0,null);
            }
            if(lastOne!=null) bombArrowSet.remove(lastOne);
        }, 0L, 1L);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "bye~");
    }
}
