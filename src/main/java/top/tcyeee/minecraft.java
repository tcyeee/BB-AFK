package top.tcyeee;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.listener.PlayListener;
import top.tcyeee.world.Plains;

public final class minecraft extends JavaPlugin {

    @Override
    public void onEnable() {
        Bukkit.createWorld(new WorldCreator("RuaWorld").generator(new Plains()));

        // Plugin startup logic
        System.out.println("插件加载成功");
        getServer().getPluginManager().registerEvents(new PlayListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
