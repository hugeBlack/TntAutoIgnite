package com.hb.tntautoignite;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;

public class GlowHelper {
    public static HashMap<Player, HashSet<Entity>> playerGlowSet = new HashMap<>();//<Receiver,Set of glowing players ENTITY_ID receiver can see>
    public static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
    public static void init(){
        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.addPacketListener(new PacketAdapter(TntAutoIgnite.thePlugin, ListenerPriority.LOWEST,PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player player = event.getPlayer(); //player who receive
                HashSet<Entity> glowList = playerGlowSet.get(player);
                if(glowList==null) return;
                int entityId = packet.getIntegers().read(0);
                Entity entity = protocolManager.getEntityFromID(player.getWorld(),entityId);
                if(glowList.contains(entity)){
                    modifyPacket(packet,entity,true);
                }
            }
        });
    }

    public static void setGlowing(Player player, Player receiver) {
        HashSet<Entity> entities = playerGlowSet.get(receiver);
        if(entities==null){
            entities = new HashSet<>();
            playerGlowSet.put(receiver,entities);
        }
        entities.add(player);
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
        modifyPacket(packet,player,true);
        try {
            protocolManager.sendServerPacket(receiver, packet);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Bukkit.getLogger().log(Level.INFO,player.getDisplayName()+" -> "+receiver.getDisplayName());

    }

    private static void modifyPacket(PacketContainer packet,Entity playerToGlow,boolean shouldGlow){
        packet.getIntegers().write(0, playerToGlow.getEntityId()); //Set packet's entity id
        List<WrappedWatchableObject> metaDataFields = packet.getWatchableCollectionModifier().read(0);
        WrappedDataWatcher watcher ;
        if(metaDataFields==null)
            watcher = new WrappedDataWatcher(); //Create data watcher, the Entity Metadata packet requires this
        else
            watcher = new WrappedDataWatcher(metaDataFields);
        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class); //Found this through google, needed for some stupid reason
        watcher.setEntity(playerToGlow); //Set the new data watcher's target
        Byte orgVal = (Byte) watcher.getWatchableObject(0).getValue();
        byte newVal = (byte) ((orgVal==null?0:orgVal) | (shouldGlow?0x40:0x0));
        watcher.setObject(0, serializer, newVal); //Set status to glowing, found on protocol page
        packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created
    }
}