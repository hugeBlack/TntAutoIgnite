package com.hb.tntautoignite.nms;

import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Trident;

public interface NMS {
    /**
     * 在指定的位置生成一个类似于原版的，有跳跃效果的，点燃的TNT，将点燃者设置为ignitePlayer
     * @param location 位置
     * @param ignitePlayer 点燃的玩家
     * @param fuseTicks 距离爆炸还剩多少tick，-1则不设置
     */
    void summonTnt(Location location, LivingEntity ignitePlayer, int fuseTicks);
    boolean checkTridentChannelling(Trident trident);
}
