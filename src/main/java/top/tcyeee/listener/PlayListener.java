package top.tcyeee.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import top.tcyeee.utils.EhCacheUtil;

import java.io.IOException;

/**
 * 玩家进入服务器/移动 -> 设置为活跃状态(5分钟过期)
 *
 * @author tcyeee
 * @date 2021/12/23 16:36
 */
public class PlayListener implements Listener {

    // 玩家移动事件 => 设为活跃
    @EventHandler
    public void event(PlayerMoveEvent event) {
        EhCacheUtil.setActive(event.getPlayer());
    }

    // 进入服务器 => 设为活跃
    @EventHandler
    public void event(PlayerJoinEvent event) {
        EhCacheUtil.setActive(event.getPlayer());
    }

    // 退出服务器 => 删除挂机
    @EventHandler
    public void event(PlayerQuitEvent event) {
        EhCacheUtil.back(event.getPlayer());
    }


    // ------------------------------------ test -----------------------------------------------------

    // 玩家切换左右手事件
    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) throws IOException {
    }

    // 玩家丢弃物品
    @EventHandler
    public void event(PlayerDropItemEvent event) {
    }
}
