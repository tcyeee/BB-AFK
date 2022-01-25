package top.tcyeee.afk;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import top.tcyeee.Main;
import top.tcyeee.common.BaseUtils;
import top.tcyeee.common.ConfigManager;
import top.tcyeee.event.AfkEvent;

/**
 * 用于处理挂机奖励的服务类
 *
 * @author tcyeee
 * @date 2021/12/30 21:52
 */
public final class AfkServer {
    private final AfkEvent afkEvent;

    public AfkServer(AfkEvent afkEvent) {
        this.afkEvent = afkEvent;
    }

    /**
     * 定时任务:
     * 每间隔1秒查询一次缓存:
     * 1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
     * 2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
     */
    public void schedule() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(Main.instance, this::checkUserStatus, 0L, 20L);
    }

    // 设置玩家状态 为 '活跃'
    public void setActive(Player player) {
        // 如果之前是挂机状态,则提示
        if (BenBenPlayerMap.isAkf(player)) {
            afkEvent.afkEnd(player);
        }
        BenBenPlayerMap.add(player);
    }

    // 查询当前在线玩家的各种目前状态
    private void checkUserStatus() {
        long ackCycle = ConfigManager.getConfig().getLong("afk.gift.cycle");
        long scopeEnd = ConfigManager.getConfig().getLong("afk.scope-end");
        int particle = ConfigManager.getConfig().getInt("afk.particle", 0);

        Main.instance.getServer().getOnlinePlayers().forEach(player -> {
            // 开始挂机触发
            long afkSecond = BenBenPlayerMap.lastReflushTime(player.getUniqueId());
            if (afkSecond == 1) {
                afkEvent.afkStart(player);
            }

            // 挂机玩家每秒触发
            if (afkSecond > 0) {
                BaseUtils.spawn(player, particle);
            }

            // 挂机玩家特定周期触发
            if (afkSecond > 0 && afkSecond % ackCycle == 0 && afkSecond < scopeEnd) {
                afkEvent.afkCycle(player);
            }
        });
    }
}
