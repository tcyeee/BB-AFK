package top.tcyeee;

import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.listener.PlayListener;
import top.tcyeee.utils.EhCacheUtil;

import java.util.Timer;
import java.util.TimerTask;

public final class minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // 添加轮询任务
        timer2();

        System.out.println("插件加载成功");

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
    public void timer2() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                checkUserStatus();
            }
        }, 1000, 1000);
    }

    private void checkUserStatus() {
        System.out.println("[轮询] =====");
        // 1. 计算挂机玩家  = 全部玩家 - 活跃玩家
        EhCacheUtil.findAll(EhCacheUtil.status.active);
        EhCacheUtil.findAll(EhCacheUtil.status.afk);
        // 2. 查询所有挂机玩家挂机时间, 距离现在为5的整数倍, 则给予经验和金币
    }
}
