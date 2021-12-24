package top.tcyeee.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.bukkit.entity.Player;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import java.util.Set;

/**
 * 缓存工具类
 * 未添加日志和异常管理
 *
 * @author tcyeee
 * @date 2021/12/24 13:19
 */
@Component
@SuppressWarnings("unused")
public final class EhCacheUtil {

    static CacheManager cacheManager;
    static Cache afkCache;
    static Cache activeCache;

    static {
        cacheManager = CacheManager.create();
        activeCache = cacheManager.getCache(status.active.name());
        afkCache = cacheManager.getCache(status.afk.name());
    }

    /* 玩家状态 */
    public enum status {
        afk, active
    }

    /**
     * 添加玩家到活跃缓存池,超过五分钟则自动移除
     * 如需要调整时间,请在ehcache配置中修改
     *
     * @param player 活跃用户
     */
    public static void setActive(Player player) {
        activeCache.put(new Element(player.getDisplayName(), player));
    }

    /**
     * 添加玩家到挂机缓存池
     *
     * @param player 挂机玩家
     */
    public static void setAfk(Player player) {
        afkCache.put(new Element(player.getDisplayName(), player));
    }

    /**
     * 从挂机缓存池中移除
     *
     * @param player 脱离挂机状态的玩家
     */
    public static void back(Player player) {
        afkCache.remove(player.getDisplayName());
    }

    /**
     * 根据玩家状态查看所有此状态玩家
     *
     * @param status 玩家状态
     * @return 玩家名称列表
     */
    public static Set<String> findAll(status status) {
        System.out.println(activeCache.getKeys().size());
//        if (activeCache.getKeys().size() > 0) {
//            Object o = activeCache.getKeys().get(0);
//            System.out.println(o.toString());
//            System.out.println(activeCache.get("tcyeee"));
//            System.out.println(activeCache.get("tcyeee").getExpirationTime());
//            System.out.println("该缓存创建时间:" + activeCache.get("tcyeee").getCreationTime());
//            System.out.println("该缓存过期时间:" + activeCache.get("tcyeee").getExpirationTime());
//        }

//        Cache cache = status == EhCacheUtil.status.afk ? afkCache : activeCache;
//        List keys = cache.getKeys();
//        System.out.println(keys.size());
        return null;
    }
}

