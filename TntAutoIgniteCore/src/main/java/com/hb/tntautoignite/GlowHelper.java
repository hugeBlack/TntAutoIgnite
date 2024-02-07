package com.hb.tntautoignite;

//import com.comphenix.protocol.PacketType;
//import com.comphenix.protocol.ProtocolLibrary;
//import com.comphenix.protocol.ProtocolManager;
//import com.comphenix.protocol.events.ListenerPriority;
//import com.comphenix.protocol.events.PacketAdapter;
//import com.comphenix.protocol.events.PacketContainer;
//import com.comphenix.protocol.events.PacketEvent;
//import com.comphenix.protocol.wrappers.WrappedDataWatcher;
//import com.comphenix.protocol.wrappers.WrappedWatchableObject;
//import org.bukkit.Bukkit;
//import org.bukkit.entity.Entity;
//import org.bukkit.entity.Player;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.logging.Level;

public class GlowHelper {
//    public static HashMap<String, HashSet<String>> playerGlowSet = new HashMap<>();//<Receiver,Set of glowing players ENTITY_ID receiver can see>
//    public static ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//    static {
//        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
//        protocolManager.addPacketListener(new PacketAdapter(TntAutoIgnite.thePlugin, ListenerPriority.HIGHEST,PacketType.Play.Server.ENTITY_METADATA) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                PacketContainer packet = event.getPacket();
//                Player player = event.getPlayer(); //player who receive
//                HashSet<String> glowList = playerGlowSet.get(player.getName());
//                if(glowList==null) return;
//                int entityId = packet.getIntegers().read(0);
//                Entity entity = protocolManager.getEntityFromID(player.getWorld(),entityId);
//                if(!(entity instanceof Player)) return;
//                Player glowPlayer = (Player) entity;
//                if(glowList.contains(glowPlayer.getName())){
//                    modifyPacket(packet,glowPlayer,true,false);
//                }else{
//                    modifyPacket(packet,glowPlayer,false,false);
//                }
//            }
//        });
//    }
//
//    public static void setGlowing(Player player, Player receiver) {
//        HashSet<String> entitiesName = playerGlowSet.get(receiver.getName());
//        if(entitiesName==null){
//            entitiesName = new HashSet<>();
//            playerGlowSet.put(receiver.getName(),entitiesName);
//        }
//        entitiesName.add(player.getName());
//        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.ENTITY_METADATA);
//        modifyPacket(packet,player,true,true);
//        try {
//            protocolManager.sendServerPacket(receiver, packet);
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static void modifyPacket(PacketContainer packet,Entity playerToGlow,boolean shouldGlow,boolean firstTime){
//        List<WrappedWatchableObject> metaDataFields = packet.getWatchableCollectionModifier().read(0);
//        WrappedDataWatcher watcher;
//        WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);//Found this through google, needed for some stupid reason
//        if(firstTime){
//            packet.getIntegers().write(0, playerToGlow.getEntityId()); //Set packet's entity id
//            watcher = new WrappedDataWatcher();
//            watcher.setEntity(playerToGlow); //Set the new data watcher's target
//            watcher.setObject(0, serializer, (byte)0x40);
//            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects());
//        }else{
//            if(metaDataFields==null) return;
//            watcher = new WrappedDataWatcher(metaDataFields);
//            WrappedWatchableObject orgValObj = watcher.getWatchableObject(0);
//            if(orgValObj==null) return;
//            Byte orgVal = (Byte) orgValObj.getValue();
//            if(orgVal==null) return;
//            byte newVal;
//            if(shouldGlow){
//                newVal = (byte) ((orgVal) | (byte)0x40);
//            }else{
//                newVal = (byte) ((orgVal) & (byte)0xbf);
//            }
//            watcher.setObject(0, serializer, newVal); //Set status to glowing, found on protocol page
//            packet.getWatchableCollectionModifier().write(0, watcher.getWatchableObjects()); //Make the packet's datawatcher the one we created
//        }
//    }
}
