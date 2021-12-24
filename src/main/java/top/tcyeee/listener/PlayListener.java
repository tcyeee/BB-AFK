package top.tcyeee.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import top.tcyeee.utils.EhCacheUtil;

/**
 * @author tcyeee
 * @date 2021/12/23 16:36
 */
public class PlayListener implements Listener {
    // 玩家切换左右手事件
    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) {
        EhCacheUtil.setActive(event.getPlayer());
        event.getPlayer().sendMessage(ChatColor.YELLOW + "切换为活跃状态");
    }
    // 玩家移动 => ehcache存值 {user,5分钟过期}
    // 定时任务
    // 每间隔1分钟查询一次缓存:
    //       1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
    //       2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
    //       3.退出服务器时候,删除挂机缓存

    // 进入服务器
    @EventHandler
    public void event(PlayerJoinEvent event) {
        System.out.println(event.getPlayer().getDisplayName() + "进入了游戏!");
        // 进入服务器时候,设置用户为活跃状态
        EhCacheUtil.setActive(event.getPlayer());
    }

    // 玩家丢弃物品
    @EventHandler
    public void event(PlayerDropItemEvent event) {
        EhCacheUtil.findAll(EhCacheUtil.status.active);
        System.out.println("玩家丢弃了物品");
    }
}
