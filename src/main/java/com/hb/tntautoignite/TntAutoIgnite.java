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
    public static ArrayList<String> savingDisabledWorldList = null;

    @Override
    public void onEnable() {
        thePlugin = this;
        loadConfig();
        GlowHelper.init();
        if(getConfig().getBoolean("tntAutoIgniteEnabled"))
            Bukkit.getPluginManager().registerEvents(new PlaceTntListener(), this);

        Bukkit.getPluginManager().registerEvents(new BombArrowListener(),this);
        Bukkit.getPluginManager().registerEvents(new MiscellaneousListeners(),this);
        this.getCommand("tai").setExecutor(new TntAutoIgniteCommand());

        //disable saving of specified worlds
        for(String worldName : savingDisabledWorldList){
            World world = Bukkit.getWorld(worldName);
            if(world != null){
                world.setAutoSave(false);
            }else{
                Bukkit.getLogger().log(Level.WARNING, "[TAI] world '"+ worldName + "' does not exist!");
            }
        }
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "[TAI] bye~");
        for(String worldName : savingDisabledWorldList){
            World world = Bukkit.getWorld(worldName);
            if(world != null){
                Bukkit.getServer().unloadWorld(world,false);
            }
        }
    }

    public static void loadConfig(){
        thePlugin.saveDefaultConfig();
        thePlugin.reloadConfig();
        arrowTntCoolDownTicks = thePlugin.getConfig().getInt("arrowTntCoolDownTicks");
        arrowTntDamageMultiplier = thePlugin.getConfig().getDouble("arrowTntDamageMultiplier");
        arrowTntFuseTicks = thePlugin.getConfig().getInt("arrowTntFuseTicks");
        savingDisabledWorldList = (ArrayList<String>) thePlugin.getConfig().getList("savingDisabledWorld");
    }


}
