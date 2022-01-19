package top.tcyeee.common;

import org.bukkit.configuration.file.FileConfiguration;
import top.tcyeee.Main;

/**
 * @author tcyeee
 * @date 2021/12/30 19:20
 */
public class ConfigManager {
    public static FileConfiguration getConfig() {
        return Main.instance.getConfig();
    }
}
