package top.tcyeee.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.bukkit.entity.Player;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashSet;
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
        final InputStream resourceAsStream = EhCacheUtil.class.getClassLoader().getResourceAsStream("ehcache.xml");
        cacheManager = CacheManager.create(resourceAsStream);
        activeCache = cacheManager.getCache(status.active.name());
        afkCache = cacheManager.getCache(status.afk.name());

        // 创建活跃缓存
        Cache memoryOnlyCache = new Cache(status.active.name(), 1000, false, false,
                1, 1);
        cacheManager.addCache(memoryOnlyCache);
        activeCache = cacheManager.getCache(status.active.name());
    }

    /* 根据玩家状态划分的缓存分区 */
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
        activeCache.put(new Element(player.getUniqueId().toString(), player));
    }

    /**
     * 添加玩家到挂机缓存池
     *
     * @param player 挂机玩家
     */
    public static void setAfk(Player player) {
        afkCache.put(new Element(player.getUniqueId().toString(), player));
    }

    /**
     * 从挂机缓存池中移除
     *
     * @param player 脱离挂机状态的玩家
     */
    public static void back(Player player) {
        afkCache.remove(player.getUniqueId().toString());
    }


    /**
     * 获取玩家信息
     *
     * @param uuid 脱离挂机状态的玩家的UUID
     * @return 挂机玩家
     */
    public static Player getPlayer(status status, String uuid) {
        Element element = afkCache.get(uuid);
        boolean notNull = element != null && element.getObjectValue() != null;
        return notNull ? (Player) element.getObjectValue() : null;
    }

    /**
     * 根据玩家状态查看所有此状态玩家
     *
     * @param status 玩家状态
     * @return 玩家名称列表
     */
    public static Set<String> findAll(status status) {
        Cache cache = status == EhCacheUtil.status.afk ? afkCache : activeCache;
        return new HashSet<String>(cache.getKeys());
    }

    /*********************************************************************************************************************/

    // 存值
    public static void set(String key, String value) {
        activeCache.put(new Element(key, value));
    }

    // 取值
    public static <T> T get(String uuid) {
        Element element = activeCache.get(uuid);
        boolean notNull = element != null && element.getObjectValue() != null;
        return notNull ? (T) element.getObjectValue() : null;
    }
}

