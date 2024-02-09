package com.hb.tntautoignite.listeners;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class PlayerPetListener implements Listener {

    public HashMap<Entity,Player> entityPlayerHashMap = new HashMap<>();

    @EventHandler
    public void onPlayerUseSpawnEggOnEntity(PlayerInteractEntityEvent e){
        ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
        if(item != null){
            EntityType newEntityType = null;
            boolean shouldAddHat = false;
            switch (item.getType()){
                case ZOMBIE_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.ZOMBIE;
                    break;
                case SKELETON_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.SKELETON;
                    break;
                case STRAY_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.STRAY;
                    break;
                case CREEPER_SPAWN_EGG:
                    shouldAddHat = false;
                    newEntityType = EntityType.CREEPER;
                    break;
                case BLAZE_SPAWN_EGG:
                    shouldAddHat = false;
                    newEntityType = EntityType.BLAZE;
                    break;
            }
            if(newEntityType == null) return;
            e.setCancelled(true);
            if(e.getPlayer().getGameMode() != GameMode.CREATIVE) item.setAmount(item.getAmount()-1);
            Entity entity = e.getPlayer().getWorld().spawnEntity(e.getRightClicked().getLocation(), newEntityType);
            ItemStack hatIS = new ItemStack(Material.LEATHER_HELMET);
            if(shouldAddHat) ((LivingEntity) entity).getEquipment().setHelmet(hatIS);
            entityPlayerHashMap.put(entity,e.getPlayer());
        }
    }

    @EventHandler
    public void onUseSpawnEgg(PlayerInteractEvent e){
        if(e.getAction()== Action.RIGHT_CLICK_BLOCK && e.getItem() != null){
            EntityType newEntityType = null;
            boolean shouldAddHat = false;
            switch (e.getItem().getType()){
                case ZOMBIE_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.ZOMBIE;
                    break;
                case SKELETON_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.SKELETON;
                    break;
                case STRAY_SPAWN_EGG:
                    shouldAddHat = true;
                    newEntityType = EntityType.STRAY;
                    break;
                case CREEPER_SPAWN_EGG:
                    shouldAddHat = false;
                    newEntityType = EntityType.CREEPER;
                    break;
                case BLAZE_SPAWN_EGG:
                    shouldAddHat = false;
                    newEntityType = EntityType.BLAZE;
                    break;
            }
            if(newEntityType == null) return;
            e.setCancelled(true);
            if(e.getPlayer().getGameMode() != GameMode.CREATIVE) e.getItem().setAmount(e.getItem().getAmount()-1);
            Entity entity = e.getPlayer().getWorld().spawnEntity(e.getClickedBlock().getLocation().add(0.5,1,0.5), newEntityType);
            ItemStack hatIS = new ItemStack(Material.LEATHER_HELMET);
            if(shouldAddHat) ((LivingEntity) entity).getEquipment().setHelmet(hatIS);
            entityPlayerHashMap.put(entity,e.getPlayer());
        }

    }

    @EventHandler
    public void onEntityTargetEntity(EntityTargetLivingEntityEvent e){
        if(!entityPlayerHashMap.containsKey(e.getEntity())) return;
        Player p =entityPlayerHashMap.get(e.getEntity());
        if(p == e.getTarget() || p == entityPlayerHashMap.get(e.getTarget())) e.setCancelled(true);
    }

    @EventHandler
    public void onPetMobHitTarget(EntityDamageByEntityEvent e){
        Entity dealer = e.getDamager();
        if(dealer instanceof Projectile) dealer = (Entity) ((Projectile)dealer).getShooter();
        if(!entityPlayerHashMap.containsKey(dealer)) return;
        if(!(e.getEntity() instanceof Player)) return;
        Player p = entityPlayerHashMap.get(dealer);

        if(p == e.getEntity()) {
            e.setCancelled(true);
        }else{
            if(e.getDamager() instanceof Projectile){
                ((Projectile)e.getDamager()).setShooter(p);
            }else{
                e.setCancelled(true);
                EntityDamageByEntityEvent newEvent = new EntityDamageByEntityEvent(p,e.getEntity(),e.getCause(),e.getDamage());
                e.getEntity().setLastDamageCause(newEvent);
                Bukkit.getServer().getPluginManager().callEvent(newEvent);
            }

            //((Player) e.getEntity()).damage(damage,p);
        }
    }
}
