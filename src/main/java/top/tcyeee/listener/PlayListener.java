package top.tcyeee.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import top.tcyeee.utils.EhCacheUtil;

/**
 * 时间检测
 *
 * @author tcyeee
 * @date 2021/12/23 16:36
 */
public class PlayListener implements Listener {

    // 玩家移动事件 => 设为活跃
    @EventHandler
    public void event(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        EhCacheUtil.set(EhCacheUtil.status.active, player.getUniqueId().toString(), player);
    }

    // 进入服务器 => 设为活跃
    @EventHandler
    public void event(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        EhCacheUtil.set(EhCacheUtil.status.active, player.getUniqueId().toString(), player);
    }

    // 退出服务器 => 删除挂机
    @EventHandler
    public void event(PlayerQuitEvent event) {
        EhCacheUtil.del(EhCacheUtil.status.afk, event.getPlayer().getUniqueId().toString());
    }


    // ------------------------------------ test -----------------------------------------------------

    // 玩家切换左右手事件
    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) {

    }

    // 玩家丢弃物品
    @EventHandler
    public void event(PlayerDropItemEvent event) {
    }
}
