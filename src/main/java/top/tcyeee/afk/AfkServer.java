package top.tcyeee.afk;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import top.tcyeee.Main;
import top.tcyeee.common.ConfigManager;

import java.util.Collection;
import java.util.List;

/**
 * 用于处理挂机奖励的服务类
 *
 * @author tcyeee
 * @date 2021/12/30 21:52
 */
@SuppressWarnings("unused")
public final class AfkServer {

    /**
     * 定时任务:
     * 每间隔1秒查询一次缓存:
     * 1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
     * 2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
     */
    public static void schedule() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.instance, AfkServer::checkUserStatus, 0L, 20L);
    }

    // 计算挂机玩家
    private static void checkUserStatus() {
        long ackCycle = ConfigManager.getConfig().getLong("afk-gift-cycle");

        // 1. 获取所有在线玩家,同时比对是否为活跃玩家
        Collection<? extends Player> onlinePlayers = Main.instance.getServer().getOnlinePlayers();
        onlinePlayers.forEach(player -> {
            // 2.挂机玩家经过特定时间间隔以后,可以获取奖励
            long afkSecond = BenBenPlayerMap.lastReflushTime(player.getUniqueId());
            if (afkSecond > 0 && afkSecond % ackCycle == 0) {
                gift(player);
            }
        });
    }

    // 给与奖励
    private static void gift(Player player) {
        int ackexp = ConfigManager.getConfig().getInt("afk-gift-exp");
        List<?> list = ConfigManager.getConfig().getList("afk-gift-excloud");
        boolean excloud = list != null && list.size() > 0 && list.contains(player.getDisplayName());
        if (player != null && !excloud) {
            player.sendMessage(ChatColor.GREEN + "[测试] 发放挂机奖励..");
            player.giveExp(ackexp);
        }
    }
}
