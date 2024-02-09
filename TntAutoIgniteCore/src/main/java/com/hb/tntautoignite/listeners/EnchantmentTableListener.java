package com.hb.tntautoignite.listeners;

import com.hb.tntautoignite.TntAutoIgnite;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class EnchantmentTableListener implements Listener {
    public static final String lapisLazuliName = "&9&lAuto Filled Lapis";
    public static final List<String> lapisLazuliLore = List.of(new String[]{"&7This lapis is auto filled and", "&7not able to be removed"});
    public ItemStack getLapis() {
        ItemStack item = new ItemStack(Material.LAPIS_LAZULI, 64);
        ItemMeta itemMeta = item.getItemMeta();

        itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', lapisLazuliName));

        List<String> lore = new ArrayList<>();
        lapisLazuliLore.forEach(line -> lore.add(ChatColor.translateAlternateColorCodes('&', line)));
        itemMeta.setLore(lore);

        item.setItemMeta(itemMeta);
        return item;
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent inventoryOpenEvent) {
        Inventory inventory = inventoryOpenEvent.getInventory();
        Player player = (Player) inventoryOpenEvent.getPlayer();

        if (inventory.getType().equals(InventoryType.ENCHANTING)) {
            inventory.setItem(1, getLapis());
        }
    }


    @EventHandler
    public void onInventoryClose(InventoryCloseEvent inventoryCloseEvent) {
        Inventory inventory = inventoryCloseEvent.getInventory();

        if (inventory.getType().equals(InventoryType.ENCHANTING)) {
            inventory.setItem(1, null);
        }
    }


    @EventHandler
    public void onInventoryClick(InventoryClickEvent inventoryClickEvent) {
        Inventory inventory = inventoryClickEvent.getClickedInventory();
        Player player = (Player) inventoryClickEvent.getWhoClicked();

        if (inventory == null) {
            return;
        }

        if (inventory.getType().equals(InventoryType.ENCHANTING) && inventoryClickEvent.getSlot() == 1) {
                inventoryClickEvent.setCancelled(true);
                player.updateInventory();
            }

    }


    @EventHandler
    public void onItemEnchantment(EnchantItemEvent enchantItemEvent) {
        Inventory inventory = enchantItemEvent.getInventory();

        if (inventory.getType().equals(InventoryType.ENCHANTING)) {

            inventory.setItem(1, null);
            inventory.setItem(1, getLapis());
        }

    }
}
