package com.hb.tntautoignite.listeners;

import com.hb.tntautoignite.TntAutoIgnite;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.logging.Level;

public class TridentListener implements Listener {
    //Trident thrown event
    @EventHandler
    public void onTridentHit(ProjectileHitEvent e) {
        if(!e.getEntityType().equals(EntityType.TRIDENT)) return;
        Trident tridentEntity = (Trident) e.getEntity();
        if (e.getEntity().getShooter() instanceof Player && TntAutoIgnite.nms.checkTridentChannelling(tridentEntity) && !e.getEntity().getWorld().isThundering()) {
            e.getEntity().getWorld().strikeLightning(e.getEntity().getLocation());
        }
    }
}
