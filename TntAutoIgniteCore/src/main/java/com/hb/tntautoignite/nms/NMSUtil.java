package com.hb.tntautoignite.nms;

import com.hb.tntautoignite.TntAutoIgnite;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class NMSUtil {
    public static NMS getNMSInstance(){
        String packageName = TntAutoIgnite.thePlugin.getServer().getClass().getPackage().getName();
        String version = packageName.substring(packageName.lastIndexOf('.') + 1);

        try {
            Class<?> nmsClass = Class.forName("com.hb.tntautoignite.nms.NMS" + version);
            if (NMS.class.isAssignableFrom(nmsClass)) {
                TntAutoIgnite.thePlugin.getLogger().info("Loaded support for NMS server version " + version + ".");
                return (NMS) nmsClass.getConstructor().newInstance();
            }
        } catch (Exception e) {
            Bukkit.getPluginManager().disablePlugin(TntAutoIgnite.thePlugin);
            e.printStackTrace();
            throw new RuntimeException("Version " + version + " not supported. Disabling this plugin...");
        }
        return null;
    }
}
