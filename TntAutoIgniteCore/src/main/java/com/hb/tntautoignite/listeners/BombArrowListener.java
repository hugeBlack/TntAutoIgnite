package com.hb.tntautoignite.listeners;

import com.hb.tntautoignite.HBItem;
import com.hb.tntautoignite.TntAutoIgnite;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Level;


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
    public void onStraightArrowShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        boolean isStraightArrow = HBItem.isStraightArrow(e.getConsumable());
        if(!isStraightArrow) return;
        e.getProjectile().setGravity(false);
        //设置速度修正
        e.getProjectile().setVelocity(e.getProjectile().getVelocity().multiply(TntAutoIgnite.straightArrowVelocityMultiplier));
        //设置寿命，减小服务器压力
        Arrow nmsArrowEntity = (Arrow) e.getProjectile();
        nmsArrowEntity.setTicksLived(TntAutoIgnite.straightArrowLiftTickOnShot);
    }
    @EventHandler
    public void onBombArrowShoot(EntityShootBowEvent e){
        if(!(e.getEntity() instanceof Player)) return;
        boolean isLobbyArrow = HBItem.isLobbyBombArrow(e.getConsumable());
        boolean isWarArrow = HBItem.isBombArrow(e.getConsumable());
        if(!isLobbyArrow && !isWarArrow) return;
        Iterator<Map.Entry<Entity,Integer>> it = tickMapPlayerCanShoot.entrySet().iterator();
        while(it.hasNext()){
            Map.Entry<Entity,Integer> entry = it.next();
            if(entry.getValue()<=totalTick) it.remove();
        }
        if(isWarArrow){
            if(tickMapPlayerCanShoot.containsKey(e.getEntity()) && tickMapPlayerCanShoot.get(e.getEntity())>totalTick){
                e.getEntity().sendMessage("§cTnt Arrow have not cooled down yet. Wait "+((double)(tickMapPlayerCanShoot.get(e.getEntity())-totalTick))/20+"s.");
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
        // TntAutoIgnite.nms.summonTnt(arrow.getLocation(), (LivingEntity) arrow.getShooter(), TntAutoIgnite.arrowTntFuseTicks);
        TNTPrimed tntEntity = Objects.requireNonNull(arrow.getLocation().getWorld()).spawn(arrow.getLocation(), TNTPrimed.class);
        tntEntity.setFuseTicks(TntAutoIgnite.arrowTntFuseTicks);
        tntEntity.setSource((Entity) arrow.getShooter());

        Iterator<Entity> it = tntSet.iterator();
        while(it.hasNext()){
            if(it.next().isDead()) it.remove();
        }

        tntSet.add(tntEntity);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(tntSet.contains(e.getDamager()) && e.getEntity() instanceof Player){
            e.setDamage(e.getDamage()*TntAutoIgnite.arrowTntDamageMultiplier);
        }
    }

}
