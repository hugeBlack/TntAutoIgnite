package com.hb.tntautoignite;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.logging.Level;

public final class TntAutoIgnite extends JavaPlugin {
    public static Plugin thePlugin = null;
    public static double arrowTntDamageMultiplier = 1;
    public static int arrowTntCoolDownTicks = 0;
    public static int arrowTntFuseTicks = 0;
    public static int blockTntFuseTick = 0;
    public static double straightArrowVelocityMultiplier = 2.0;
    public static int straightArrowLiftTickOnShot = 0;

    @Override
    public void onEnable() {
        thePlugin = this;
        loadConfig();
        if(getConfig().getBoolean("tntAutoIgniteEnabled"))
            Bukkit.getPluginManager().registerEvents(new PlaceTntListener(), this);

        Bukkit.getPluginManager().registerEvents(new BombArrowListener(),this);
        Bukkit.getPluginManager().registerEvents(new MiscellaneousListeners(),this);
        this.getCommand("tai").setExecutor(new TntAutoIgniteCommand());

    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "[TAI] bye~");
    }

    public static void loadConfig(){
        thePlugin.saveDefaultConfig();
        thePlugin.reloadConfig();
        arrowTntCoolDownTicks = thePlugin.getConfig().getInt("arrowTntCoolDownTicks");
        arrowTntDamageMultiplier = thePlugin.getConfig().getDouble("arrowTntDamageMultiplier");
        arrowTntFuseTicks = thePlugin.getConfig().getInt("arrowTntFuseTicks");
        straightArrowVelocityMultiplier = thePlugin.getConfig().getDouble("straightArrowVelocityMultiplier");
        blockTntFuseTick = thePlugin.getConfig().getInt("blockTntFuseTick");
        straightArrowLiftTickOnShot= thePlugin.getConfig().getInt("straightArrowLiftTickOnShot");
    }


}
