package com.hb.tntautoignite;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Level;

public final class TntAutoIgnite extends JavaPlugin {
    public static Plugin thePlugin = null;
    public static double arrowTntDamageMultiplier = 1;
    public static int arrowTntCoolDownTicks = 0;
    public static int arrowTntFuseTicks = 0;

    @Override
    public void onEnable() {
        thePlugin = this;
        Bukkit.getPluginManager().registerEvents(new PlaceTntListener(), this);
        Bukkit.getPluginManager().registerEvents(new BombArrowListener(),this);
        loadConfig();
        this.getCommand("tai").setExecutor(new TntAutoIgniteCommand());
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "bye~");
    }

    public static void loadConfig(){
        thePlugin.saveDefaultConfig();
        thePlugin.reloadConfig();
        arrowTntCoolDownTicks = thePlugin.getConfig().getInt("arrowTntCoolDownTicks");
        arrowTntDamageMultiplier = thePlugin.getConfig().getDouble("arrowTntDamageMultiplier");
        arrowTntFuseTicks = thePlugin.getConfig().getInt("arrowTntFuseTicks");
    }
}
