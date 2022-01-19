package top.tcyeee;

import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.afk.AfkCache;
import top.tcyeee.afk.AfkServer;
import top.tcyeee.listener.PlayListener;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        getLogger().info("🎉🎉plugin benben start success !!🎉🎉");

        instance = this;
        saveDefaultConfig();

        System.out.println("当前缓存状态:" + AfkCache.showStatus());

        // 添加轮询任务
        AfkServer.schedule();

        // 添加监听
        getServer().getPluginManager().registerEvents(new PlayListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
