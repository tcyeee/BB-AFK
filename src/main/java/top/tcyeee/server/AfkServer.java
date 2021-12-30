package top.tcyeee.server;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import top.tcyeee.Main;
import top.tcyeee.controller.ConfigManager;
import top.tcyeee.utils.EhCacheUtil;

import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 用于处理挂机奖励的服务类
 *
 * @author tcyeee
 * @date 2021/12/30 21:52
 */
public class AfkServer {

    /**
     * 定时任务:
     * 每间隔1分钟查询一次缓存:
     * 1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
     * 2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
     */
    public static void schedule() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.instance, AfkServer::checkUserStatus, 0L, 20L);
    }


    private static void checkUserStatus() {
        long ackCycle = ConfigManager.getConfig().getLong("afk-gift-cycle");
        // 1.找到挂机玩家, 加入挂机缓存 因为ehcache不会删除不活跃获取元素的特性, 这里需要直接查询, 如果value为null, 则表示超时
        Set<String> active = EhCacheUtil.keys(EhCacheUtil.status.active);
        active.forEach(uuid -> {
            Player player = EhCacheUtil.get(EhCacheUtil.status.active, uuid);
            if (player == null) {
                player = Main.instance.getServer().getPlayer(UUID.fromString(uuid));
                if (player != null) {
                    EhCacheUtil.set(EhCacheUtil.status.afk, player.getUniqueId().toString(), player);
                    Main.instance.getLogger().info(player.getDisplayName() + "挂机了");
                    Main.instance.getLogger().info("当前有" + EhCacheUtil.keys(EhCacheUtil.status.afk).size() + "人挂机");
                }
            } else {
                // 删除挂机状态
                EhCacheUtil.del(EhCacheUtil.status.afk, player.getUniqueId().toString());
            }
        });

        // 2.查询所有挂机玩家挂机时间, 距离现在为 ackTime 的整数倍, 则给予经验和金币
        if ((System.currentTimeMillis() / 1000) % ackCycle == 0) {
            Set<String> afk = EhCacheUtil.keys(EhCacheUtil.status.afk);
            afk.forEach(uuid -> gift(EhCacheUtil.get(EhCacheUtil.status.afk, uuid)));
        }
    }

    // 给与奖励
    private static void gift(Player player) {
        int ackexp = ConfigManager.getConfig().getInt("afk-gift-exp");
        List<?> list = ConfigManager.getConfig().getList("afk-gift-excloud");
        boolean excloud = list != null && list.size() > 0 && list.contains(player.getDisplayName());
        if (player != null && !excloud) {
            player.giveExp(ackexp);
        }
    }
}
