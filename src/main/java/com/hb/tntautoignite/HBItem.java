package com.hb.tntautoignite;


import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public class HBItem {
    public static NamespacedKey ItemKey = null;
    public static ItemStack getBukkitStack(Material itemType, String hbItemId, int count){
        ItemStack is =new ItemStack(itemType,count);
        ItemMeta im = is.getItemMeta();
        im.getPersistentDataContainer().set(getKey(), PersistentDataType.STRING,hbItemId);
        is.setItemMeta(im);
        return is;

    }
    public static boolean isItem(String id,ItemStack stack){
        String itemId = stack.getItemMeta().getPersistentDataContainer().get(getKey(),PersistentDataType.STRING);
        return itemId!=null && itemId.equals(id);
    }

    private static NamespacedKey getKey(){
        if(ItemKey==null){
            ItemKey = new NamespacedKey(TntAutoIgnite.thePlugin,"ItemId");
        }
        return  ItemKey;
    }
}
