package top.tcyeee.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import top.tcyeee.afk.AfkServer;
import top.tcyeee.afk.BenBenPlayerMap;
import top.tcyeee.event.AfkImpl;

/**
 * 时间检测
 *
 * @author tcyeee
 * @date 2021/12/23 16:36
 */
public class PlayListener implements Listener {
    private final AfkServer afkServer = new AfkServer(new AfkImpl());

    // 玩家移动事件 => 设为活跃
    @EventHandler
    public void event(PlayerMoveEvent event) {
        afkServer.setActive(event.getPlayer());
    }

    // 进入服务器 => 设为活跃
    @EventHandler
    public void event(PlayerJoinEvent event) {
        afkServer.setActive(event.getPlayer());
    }

    // 离开服务器 => 删除状态
    @EventHandler
    public void event(PlayerQuitEvent event) {
        BenBenPlayerMap.del(event.getPlayer());
    }

    // ------------------------------------ test -----------------------------------------------------

    // 玩家切换左右手事件
    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) {
    }
}
