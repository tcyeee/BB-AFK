package top.tcyeee.afk;

import org.bukkit.entity.Player;
import top.tcyeee.common.ConfigManager;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 暂存玩家数据
 *
 * @author tcyeee
 * @date 2022/1/19 11:36
 */
public final class BenBenPlayerMap {
    static ConcurrentHashMap<UUID, Long> map;
    final static long ackTime = ConfigManager.getConfig().getLong("afk.scope-start");

    static {
        map = new ConcurrentHashMap<>();
    }

    // 存值/刷新 玩家最后活跃时间
    public static void add(Player player) {
        map.put(player.getUniqueId(), System.currentTimeMillis());
    }

    // 存值/刷新 玩家最后活跃时间
    public static void del(Player player) {
        map.remove(player.getUniqueId());
    }


    // 检查玩家是否为挂机状态
    public static boolean isAkf(Player player) {
        UUID uid = player.getUniqueId();
        if (!map.containsKey(uid)) {
            return false;
        }
        long lag = (System.currentTimeMillis() - map.get(uid)) / 1000;
        return lag > ackTime;
    }

    /**
     * 获取挂机时长(秒)
     *
     * @param uid 用户ID
     * @return 0: 未挂机  >0:正常挂机时长
     */
    public static long lastReflushTime(UUID uid) {
        if (map.containsKey(uid)) {
            Long lastReflush = map.get(uid);
            long lag = (System.currentTimeMillis() - lastReflush) / 1000;
            return lag - ackTime > 0 ? lag - ackTime : 0;
        }
        return 0;
    }
}
