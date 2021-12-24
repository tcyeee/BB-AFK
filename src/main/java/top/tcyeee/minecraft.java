package top.tcyeee;

import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.listener.PlayListener;

public final class minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        System.out.println("插件加载成功");
        getServer().getPluginManager().registerEvents(new PlayListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
