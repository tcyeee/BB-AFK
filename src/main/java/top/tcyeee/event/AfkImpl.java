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
    String title = ConfigManager.getConfig().getString("afk.title.main");
    String subTitle = ConfigManager.getConfig().getString("afk.title.sub");
    int scopeEndSecond = ConfigManager.getConfig().getInt("afk.scope-end");

    @Override
    public void afkStart(Player player) {

        // 发送挂机信息
        if (afkMessage != null && !afkMessage.equals("") && !afkMessage.equals("null")) {
            String coloredText = translateAlternateColorCodes('&', afkMessage);
            player.sendMessage(coloredText);
        }

        // 设置title
        if (title != null && !title.equals("") && !title.equals("null")
                && subTitle != null && !subTitle.equals("") && !subTitle.equals("null")) {
            String titleCode = translateAlternateColorCodes('&', title);
            String subTitleCode = translateAlternateColorCodes('&', subTitle);
            player.sendTitle(titleCode, subTitleCode, 10, scopeEndSecond * 20, 10);
        }
    }

    @Override
    public void afkEnd(Player player) {
        if (activeMessage != null && !activeMessage.equals("") && !activeMessage.equals("null")) {
            String coloredText = translateAlternateColorCodes('&', activeMessage);
            player.sendMessage(coloredText);
        }
        player.resetTitle();
    }

    @Override
    public void afkCycle(Player player) {
        // 检查黑名单
        boolean excloud = list != null && list.size() > 0 && list.contains(player.getDisplayName());

        if (player != null && !excloud) {
            player.giveExp(ackexp);
            econ.depositPlayer(player, ackVault);
            if (giftMessage != null && !giftMessage.equals("") && !giftMessage.equals("null")) {
                String coloredText = translateAlternateColorCodes('&', giftMessage);
                player.sendMessage(coloredText);
            }
        }
    }
}
