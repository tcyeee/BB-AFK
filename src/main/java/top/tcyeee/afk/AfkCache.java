package top.tcyeee.afk;

import org.bukkit.entity.Player;
import top.tcyeee.common.ConfigManager;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.expiry.AccessedExpiryPolicy;
import javax.cache.expiry.Duration;

import java.util.UUID;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * {@link AfkCache#activeCache} 活跃玩家缓存池, 玩家活跃时加入, 静止超时则移除
 *
 * @author tcyeee
 * @date 2022/1/18 15:09
 */
public final class AfkCache {
    static Cache<UUID, Player> activeCache;

    static {
        CacheManager cacheManager = Caching.getCachingProvider().getCacheManager();
        long ackTime = ConfigManager.getConfig().getLong("afk-time");
        MutableConfiguration<UUID, Player> config = new MutableConfiguration<UUID, Player>()
                .setExpiryPolicyFactory(AccessedExpiryPolicy.factoryOf(new Duration(SECONDS, ackTime)))
                .setTypes(UUID.class, Player.class)
                .setStatisticsEnabled(true);
        activeCache = cacheManager.createCache("activeCache", config);
    }

    // 设置为活跃玩家
    public static void add(Player player) {
        activeCache.put(player.getUniqueId(), player);
    }

    // 检测是否为活跃玩家
    public static boolean isActive(UUID uid) {
        return activeCache.containsKey(uid);
    }

    // 状态检测
    public static boolean showStatus() {
        return activeCache.isClosed();
    }

}
