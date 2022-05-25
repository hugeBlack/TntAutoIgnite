package com.hb.tntautoignite;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftTNTPrimed;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.Iterator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.hb.tntautoignite.TntAutoIgnite.bombArrowSet;
import static com.hb.tntautoignite.TntAutoIgnite.tntSet;

public class BombArrowListener implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent e){
        if(!HBItem.isItem("bomb_arrow",e.getConsumable())) return;
        TntAutoIgnite.bombArrowSet.add(e.getProjectile());
    }

    @EventHandler
    public void onLand(ProjectileHitEvent e){
        if(!bombArrowSet.contains(e.getEntity())) return;
        e.setCancelled(true);
        summonTnt(e.getEntity());
        bombArrowSet.remove(e.getEntity());
        e.getEntity().remove();
    }


    public void summonTnt(Projectile arrow){
        Entity newTnt = arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.PRIMED_TNT);
        CraftTNTPrimed nmsTNT = (CraftTNTPrimed) newTnt;
        tntSet.add(nmsTNT);
        nmsTNT.setFuseTicks(15);
        nmsTNT.setSource(Objects.requireNonNull((Entity)arrow.getShooter()));
        Iterator it = tntSet.iterator();
        while(it.hasNext()){
            if(((Entity) it.next()).isDead()) it.remove();
        }
    }
    /*@EventHandler(priority = EventPriority.LOW)
    public void onPlayerBucket(PlayerBucketFillEvent e){
        ItemStack is = HBItem.getBukkitStack(Material.ARROW,"bomb_arrow",64);
        ItemMeta im = is.getItemMeta();
        im.setDisplayName("§r§e§lRocket");
        List<String> lore = new ArrayList<>();
        lore.add("§r§7Launch with a bow");
        im.setLore(lore);
        is.setItemMeta(im);
        e.getPlayer().getInventory().addItem(is);
        Bukkit.getLogger().log(Level.WARNING,"NMSL");
    }*/

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(tntSet.contains(e.getDamager()) && e.getEntity() instanceof Player){
            e.setDamage(e.getDamage()/4);
        }
    }
}
