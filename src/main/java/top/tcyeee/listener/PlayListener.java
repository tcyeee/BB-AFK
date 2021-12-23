package top.tcyeee.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * @author tcyeee
 * @date 2021/12/23 16:36
 */
public class PlayListener implements Listener {
    @EventHandler
    public void event(PlayerMoveEvent event) {
        System.out.println("玩家开始了移动");
        System.out.println(event.toString());
    }

    @EventHandler
    public void event(PlayerJoinEvent event) {
        System.out.println("玩家进入了服务器");
        System.out.println(event.toString());
    }

    // 玩家移动 => ehcache存值 {user,5分钟过期}




    // 定时任务
    // 每间隔1分钟查询一次redis, 比对playerlist和redis差值,其中没有的,存入缓存2,设置为挂机状态
}
