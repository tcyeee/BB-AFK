package top.tcyeee.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.stereotype.Component;
import top.tcyeee.controller.ConfigManager;

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
@SuppressWarnings("all")
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
        long ackTime = ConfigManager.getConfig().getLong("afk-time");
        Cache memoryOnlyCache = new Cache(status.active.name(), 1000, false, false,
                ackTime, 0);
        cacheManager.addCache(memoryOnlyCache);
        activeCache = cacheManager.getCache(status.active.name());
    }

    /* 根据玩家状态划分的缓存分区 */
    public enum status {
        afk, active
    }

    // 存值
    public static void set(status status, String key, Object value) {
        getCache(status).put(new Element(key, value));
    }

    // 删值
    public static void del(status status, String key) {
        getCache(status).remove(key);
    }

    // 取值
    public static <T> T get(status status, String uuid) {
        Element element = getCache(status).get(uuid);
        boolean notNull = element != null && element.getObjectValue() != null;
        return notNull ? (T) element.getObjectValue() : null;
    }

    // 查看所有值
    public static Set<String> keys(status status) {
        Cache cache = getCache(status);
        return new HashSet<String>(cache.getKeys());
    }

    // 获取对应存储空间
    public static Cache getCache(status status) {
        return status == EhCacheUtil.status.afk ? afkCache : activeCache;
    }
}

