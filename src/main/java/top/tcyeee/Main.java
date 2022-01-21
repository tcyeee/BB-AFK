package top.tcyeee;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import top.tcyeee.afk.AfkServer;
import top.tcyeee.afk.BenBenPlayerMap;
import top.tcyeee.listener.PlayListener;

public final class Main extends JavaPlugin {
    public static Main instance;
    public static Economy econ = null;

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();

        instance = this;
        saveDefaultConfig();

        // 添加轮询任务
        AfkServer.schedule();

        //插件装载的时候,更新一次在线玩家
        Main.instance.getServer().getOnlinePlayers().forEach(BenBenPlayerMap::add);

        // 添加监听
        getServer().getPluginManager().registerEvents(new PlayListener(), this);

        // [校验] 必须装载有经济插件
        boolean setVaultStatus = setVaultPlugin();
        if (!setVaultStatus) {
            getLogger().severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        getLogger().info("🎉🎉plugin benben start success !!🎉🎉");
        getLogger().info("elapsed timeMillis " + (System.currentTimeMillis() - start) + " timeMillis!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    // 加载经济插件
    private boolean setVaultPlugin() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }
}
