//package top.tcyeee.server;
//
//import net.sf.ehcache.Cache;
//import net.sf.ehcache.CacheManager;
//import net.sf.ehcache.Element;
//import top.tcyeee.utils.EhCacheUtil;
//
//import java.io.InputStream;
//import java.util.List;
//
///**
// * <h5>描述:缓存管理类</h5>
// */
//public class EhcacheService {
//
//    private static final String CACHE_NAME = "active";
//    private static final String EHCACHE_CONFIG_NAME = "ehcache.xml";
//    private static final CacheManager cacheManager;
//
//    static {
//        final InputStream resourceAsStream = EhcacheService.class.getClassLoader().getResourceAsStream("ehcache.xml");
//        cacheManager = CacheManager.create();
//        CacheManager cacheMan2ager = CacheManager.create(resourceAsStream);
//    }
//
//    /**
//     * @param cache_name 配置文件中缓存的name
//     * @param key        键
//     * @param value      缓存值
//     * @功能描述： 添加一个缓存到指定的缓存中
//     */
//    public synchronized static void putObjectCache(String cache_name, String key, Object value) {
//        Cache cache = getCache(cache_name);
//        if (null != cache) {
//            cache.remove(key);
//            Element elem = new Element(key, value);
//            cache.put(elem);
//        }
//    }
//
//    /**
//     * @param cache_name 配置文件中缓存的name
//     * @param key        键
//     * @return
//     * @功能描述： 从指定的缓存中获取一个缓存数据的值
//     */
//    public static Object getObjectCache(String cache_name, String key) {
//        Cache cache = getCache(cache_name);
//        if (null != cache) {
//            Element element = cache.get(key);
//            // isExpired(Element element) 检查缓存是否过期
//            if (element != null && !cache.isExpired(element)) {
//                return element.getObjectValue();
//            }
//        }
//        return null;
//    }
//
//    /**
//     * <h5>功能:清空指定缓存的所有信息</h5>
//     *
//     * @param cache_name 配置文件中缓存的name
//     */
//    public static void removeCache(String cache_name) {
//        Cache cache = getCache(cache_name);
//        cache.removeAll();
//    }
//
//    /**
//     * <h5>功能:清空指定缓存的指定信息</h5>
//     *
//     * @param cache_name 配置文件中缓存的name
//     * @param key        键
//     * @return boolean 是否移除成功
//     */
//    public static boolean removeCache(String cache_name, String key) {
//        Cache cache = getCache(cache_name);
//        return cache.remove(key);
//    }
//
//    /**
//     * 停止缓存管理器
//     */
//    public static void shutdown() {
//        if (null != cacheManager)
//            cacheManager.shutdown();
//    }
//
//// ==================== private method ====================
//
//    /**
//     * @return classes绝对路径
//     * @功能描述： 获取 classes 文件夹路径
//     */
//    private static String getClassesPath() {
//        String classesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
//        return classesPath;
//    }
//
//    /**
//     * @param cache_name 配置文件中缓存的name
//     * @return Cache 缓存
//     * @功能描述： 获取指定名称的缓存
//     */
//    private static Cache getCache(String cache_name) {
//        return cacheManager.getCache(cache_name);
//    }
//
//    public static void main(String[] args) {
//
//        EhcacheService.putObjectCache(CACHE_NAME, "key01", 1000);
//        EhcacheService.putObjectCache(CACHE_NAME, "key02", "abc");
//        EhcacheService.putObjectCache(CACHE_NAME, "key03", 3.1415);
//        EhcacheService.putObjectCache(CACHE_NAME, "key04", "哈哈");
//        EhcacheService.putObjectCache(CACHE_NAME, "key05", "{\"id\":100}");
//
//        EhcacheService.putObjectCache(CACHE_NAME, "key04", "Hello World");
//
////		removeCache(CACHE_NAME);
////		removeCache(CACHE_NAME, "key05");
//        Cache demoCache = cacheManager.getCache(CACHE_NAME);
//        if (null != demoCache) {
//            List<?> demoList = demoCache.getKeys();
//            for (Object myKey : demoList) {
//                Element myElement = demoCache.get(myKey);
//                System.out.println(myKey + "|" + myElement.getObjectValue());
//            }
//        }
//        EhcacheService.putObjectCache(CACHE_NAME, "key03", 3.1666);
//        System.out.println("---------" + EhcacheService.getObjectCache(CACHE_NAME, "key03"));
//
//        System.exit(0);
//    }
//}
