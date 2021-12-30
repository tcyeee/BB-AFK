package top.tcyeee;

import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.listener.PlayListener;
import top.tcyeee.server.AfkServer;

public final class Main extends JavaPlugin {
    public static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        System.out.println("plugin benben start success !");

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
