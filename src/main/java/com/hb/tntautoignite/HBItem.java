package com.hb.tntautoignite;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HBItem {
    private static NamespacedKey lobbyBombArrowKey = new NamespacedKey(TntAutoIgnite.thePlugin,"lobby_bomb_arrow");;
    private static NamespacedKey bombArrowKey = new NamespacedKey(TntAutoIgnite.thePlugin,"bomb_arrow");
    private static NamespacedKey straightArrowKey = new NamespacedKey(TntAutoIgnite.thePlugin,"straight_arrow");
    private static boolean isItem(NamespacedKey namespacedKey,ItemStack stack){
        ItemMeta im = stack.getItemMeta();
        if(im==null) return false;
        Byte isItem = im.getPersistentDataContainer().get(namespacedKey,PersistentDataType.BYTE);
        return isItem!=null && isItem==1;
    }

    public static boolean isLobbyBombArrow(ItemStack stack){
        return isItem(lobbyBombArrowKey,stack);
    }
    public static boolean isBombArrow(ItemStack stack){
        return isItem(bombArrowKey,stack);
    }
    public static boolean isStraightArrow(ItemStack stack){
        return isItem(straightArrowKey,stack);
    }
}
