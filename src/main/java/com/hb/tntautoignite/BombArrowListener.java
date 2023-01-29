package com.hb.tntautoignite;

import net.minecraft.world.entity.projectile.ThrownTrident;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftTNTPrimed;
import org.bukkit.craftbukkit.v1_18_R2.entity.CraftTrident;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class BombArrowListener implements Listener {

    public HashSet<Entity> bombArrowSet = new HashSet<>();
    public HashSet<Entity> tntSet = new HashSet<>();
    public HashMap<Entity,Integer> tickMapPlayerCanShoot = new HashMap<>();
    public int totalTick = 0;
    {
        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(TntAutoIgnite.thePlugin, () -> {
            totalTick++;
            for(Entity bombArrow: bombArrowSet){
                bombArrow.getWorld().spawnParticle(Particle.SMOKE_LARGE,bombArrow.getLocation(),100,0,0,0,0,null);
            }
        }, 0L, 1L);
    }

    @EventHandler
    public void onShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        boolean isLobbyArrow = HBItem.isItem("bomb_arrow_lobby",e.getConsumable());
        boolean isWarArrow = HBItem.isItem("bomb_arrow",e.getConsumable());
        if(!isLobbyArrow && !isWarArrow) return;
        Iterator<Map.Entry<Entity,Integer>> it = tickMapPlayerCanShoot.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Entity,Integer> entry = it.next();
            if(entry.getValue()<=totalTick) it.remove();
        }
        if(isWarArrow){
            if(tickMapPlayerCanShoot.containsKey(e.getEntity()) && tickMapPlayerCanShoot.get(e.getEntity())>totalTick){
                ((Player)e.getEntity()).sendMessage("§cTnt Arrow have not cooled down yet. Wait "+((double)(tickMapPlayerCanShoot.get(e.getEntity())-totalTick))/20+"s.");
                e.setCancelled(true);
                ((Player)e.getEntity()).updateInventory();
                return;
            }
            tickMapPlayerCanShoot.put(e.getEntity(),totalTick+TntAutoIgnite.arrowTntCoolDownTicks);
        }
        if(e.getBow().containsEnchantment(Enchantment.ARROW_INFINITE)){
            e.getConsumable().setAmount(e.getConsumable().getAmount()-1);
        }
        bombArrowSet.add(e.getProjectile());
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
        nmsTNT.setFuseTicks(TntAutoIgnite.arrowTntFuseTicks);
        nmsTNT.setSource(Objects.requireNonNull((Entity)arrow.getShooter()));
        Iterator it = tntSet.iterator();
        while(it.hasNext()){
            if(((Entity) it.next()).isDead()) it.remove();
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(tntSet.contains(e.getDamager()) && e.getEntity() instanceof Player){
            e.setDamage(e.getDamage()*TntAutoIgnite.arrowTntDamageMultiplier);
        }
    }



}
