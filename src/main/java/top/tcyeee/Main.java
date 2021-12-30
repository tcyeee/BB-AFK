package top.tcyeee;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import top.tcyeee.listener.PlayListener;
import top.tcyeee.utils.EhCacheUtil;

import java.util.*;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;

        // 添加轮询任务
        schedule();
        System.out.println("plugin benben start success !");

        // 添加监听
        getServer().getPluginManager().registerEvents(new PlayListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    /**
     * 定时任务:
     * 每间隔1分钟查询一次缓存:
     * 1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
     * 2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
     */
    public void schedule() {
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, this::checkUserStatus, 0L, 20L);
    }


    private void checkUserStatus() {
        // 1.找到挂机玩家, 加入挂机缓存 因为ehcache不会删除不活跃获取元素的特性, 这里需要直接查询, 如果value为null, 则表示超时
        Set<String> active = EhCacheUtil.findAll(EhCacheUtil.status.active);
        active.forEach(uuid -> {
            Player player = EhCacheUtil.getPlayer(EhCacheUtil.status.active, uuid);
            if (player == null) {
                player = getServer().getPlayer(UUID.fromString(uuid));
                if (player != null) {
                    player.sendMessage(ChatColor.RED + "你挂机了");
                    EhCacheUtil.setAfk(player);
                }
            } else {
                // 删除挂机状态
                EhCacheUtil.back(player);
            }
        });

        // 2.查询所有挂机玩家挂机时间, 距离现在为5的整数倍, 则给予经验和金币
        int currentMin = Calendar.getInstance().get(Calendar.SECOND);
        if (currentMin % 5 == 0) {
            Set<String> afk = EhCacheUtil.findAll(EhCacheUtil.status.afk);
            System.out.println("当前有" + afk.size() + "人挂机");
            afk.forEach(uuid -> {
                Player player = EhCacheUtil.getPlayer(EhCacheUtil.status.afk, uuid);
                if (player != null) {
                    player.sendMessage(ChatColor.BLUE + "给你亿点经验");
                    player.giveExp(10);
                }
            });
        }
    }
}
