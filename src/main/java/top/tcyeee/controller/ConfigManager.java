package top.tcyeee.controller;

import org.bukkit.configuration.file.FileConfiguration;
import top.tcyeee.Main;

/**
 * @author tcyeee
 * @date 2021/12/30 19:20
 */
public class ConfigManager {
    public static void reloadConfig() {
        Main.instance.reloadConfig();
    }

    public static FileConfiguration getConfig() {
        return Main.instance.getConfig();
    }
}
