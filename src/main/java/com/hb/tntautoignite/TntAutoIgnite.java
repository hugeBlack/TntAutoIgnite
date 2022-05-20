package com.hb.tntautoignite;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class TntAutoIgnite extends JavaPlugin {

    public static Plugin thePlugin = null;

    @Override
    public void onEnable() {
        thePlugin = this;
        Bukkit.getPluginManager().registerEvents(new PlaceTntListener(), this);
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().log(Level.WARNING, "bye~");
    }
}
