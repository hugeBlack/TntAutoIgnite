package com.hb.tntautoignite.listeners;


import com.hb.tntautoignite.TntAutoIgnite;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class PlaceTntListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        if (e.getBlock().getType() == Material.TNT) {
            Block tntBlock = e.getBlock();
            //从TntBlock.class的explode方法抄的，略有修改
            TntAutoIgnite.nms.summonTnt(e.getBlock().getLocation(), e.getPlayer(), TntAutoIgnite.blockTntFuseTick);
            tntBlock.setType(Material.AIR);
        }
    }
}
