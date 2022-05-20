package com.hb.tntautoignite;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftTNTPrimed;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;

import static com.hb.tntautoignite.TntAutoIgnite.bombSet;

public class BombArrowListener implements Listener {
    @EventHandler
    public void onShoot(EntityShootBowEvent e){
        if(!HBItem.isItem("bomb_arrow",e.getConsumable())) return;
        TntAutoIgnite.bombSet.add(e.getProjectile());
    }

    @EventHandler
    public void onLand(ProjectileHitEvent e){
        if(!bombSet.contains(e.getEntity())) return;
        summonTnt(e.getEntity());
        bombSet.remove(e.getEntity());
        e.getEntity().remove();
    }


    public void summonTnt(Projectile arrow){
        Entity newTnt = arrow.getWorld().spawnEntity(arrow.getLocation(), EntityType.PRIMED_TNT);
        CraftTNTPrimed nmsTNT = (CraftTNTPrimed) newTnt;
        nmsTNT.setFuseTicks(15);
        nmsTNT.setSource(Objects.requireNonNull((Entity)arrow.getShooter()));
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
}
