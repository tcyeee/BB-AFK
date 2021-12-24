package top.tcyeee.listener;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import top.tcyeee.utils.EhCacheUtil;

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
        System.out.println(event.getPlayer().getDisplayName() + "进入了游戏!");
        EhCacheUtil.setActive(event.getPlayer());
    }

    // 退出服务器 => 删除挂机
    @EventHandler
    public void event(PlayerQuitEvent event) {
        System.out.println(event.getPlayer().getDisplayName() + "离开了游戏!");
        EhCacheUtil.back(event.getPlayer());
    }


    // ------------------------------------ test -----------------------------------------------------

    // 玩家切换左右手事件
    @EventHandler
    public void event(PlayerSwapHandItemsEvent event) {
        EhCacheUtil.setActive(event.getPlayer());
        event.getPlayer().sendMessage(ChatColor.YELLOW + "切换为活跃状态");
    }

    // 玩家丢弃物品
    @EventHandler
    public void event(PlayerDropItemEvent event) {
        EhCacheUtil.findAll(EhCacheUtil.status.active);
        System.out.println("玩家丢弃了物品");
    }
}
