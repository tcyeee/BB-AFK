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

    static {
        map = new ConcurrentHashMap<>();
    }

    // 设置为活跃玩家
    public static void add(Player player) {
        map.put(player.getUniqueId(), System.currentTimeMillis());
    }

    /**
     * 获取挂机时长(秒)
     *
     * @param uid 用户ID
     * @return 0: 未挂机  >0:正常挂机时长
     */
    public static long lastReflushTime(UUID uid) {
        final long ackTime = ConfigManager.getConfig().getLong("afk.scope-time");
        if (map.containsKey(uid)) {
            Long lastReflush = map.get(uid);
            long lag = (System.currentTimeMillis() - lastReflush) / 1000;
            return lag - ackTime > 0 ? lag - ackTime : 0;
        }
        return 0;
    }
}
