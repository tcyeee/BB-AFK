package top.tcyeee.event;

import org.bukkit.entity.Player;
import top.tcyeee.common.ConfigManager;

import java.util.List;

import static org.bukkit.ChatColor.translateAlternateColorCodes;
import static top.tcyeee.Main.econ;

/**
 * @author tcyeee
 * @date 2022/1/25 10:44
 */
public class AfkImpl implements AfkEvent {
    int ackexp = ConfigManager.getConfig().getInt("afk.gift.exp");
    int ackVault = ConfigManager.getConfig().getInt("afk.gift.vault");
    List<?> list = ConfigManager.getConfig().getList("afk.gift.blacklist");
    String afkMessage = ConfigManager.getConfig().getString("afk.message.afk");
    String activeMessage = ConfigManager.getConfig().getString("afk.message.active");
    String giftMessage = ConfigManager.getConfig().getString("afk.message.gift");

    @Override
    public void afkStart(Player player) {
        if (afkMessage == null || afkMessage.equals("") || afkMessage.equals("null")) {
            return;
        }
        String coloredText = translateAlternateColorCodes('&', afkMessage);
        player.sendMessage(coloredText);
    }

    @Override
    public void afkEnd(Player player) {
        if (activeMessage == null || activeMessage.equals("") || activeMessage.equals("null")) {
            return;
        }
        String coloredText = translateAlternateColorCodes('&', activeMessage);
        player.sendMessage(coloredText);
    }

    @Override
    public void afkCycle(Player player) {
        // 检查黑名单
        boolean excloud = list != null && list.size() > 0 && list.contains(player.getDisplayName());

        if (player != null && !excloud) {
            player.giveExp(ackexp);
            econ.depositPlayer(player, ackVault);
            if (giftMessage == null || giftMessage.equals("") || giftMessage.equals("null")) {
                return;
            }
            String coloredText = translateAlternateColorCodes('&', giftMessage);
            player.sendMessage(coloredText);
        }
    }
}
