package top.tcyeee.event;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
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
    PotionEffect effect = new PotionEffect(PotionEffectType.BLINDNESS, scopeEndSecond * 20, 2, true, false, false);

    /**
     * 开始挂机
     * 1.发送挂机信息
     * 2.设置玩家页面title
     *
     * @param player player info
     */
    @Override
    public void afkStart(Player player) {

        // 发送挂机信息
        if (afkMessage != null && !afkMessage.equals("") && !afkMessage.equals("null")) {
            String coloredText = translateAlternateColorCodes('&', afkMessage);
            player.sendMessage(coloredText);
        }

        // 设置title 和失明状态
        if (title != null && !title.equals("") && !title.equals("null")
                && subTitle != null && !subTitle.equals("") && !subTitle.equals("null")) {
            String titleCode = translateAlternateColorCodes('&', title);
            String subTitleCode = translateAlternateColorCodes('&', subTitle);
            player.sendTitle(titleCode, subTitleCode, 10, scopeEndSecond * 20, 10);
            player.addPotionEffect(effect);
        }
    }

    /**
     * 玩家结束挂机
     * 1.发送信息
     * 2.删除title
     * 3.结束失明状态
     *
     * @param player player info
     */
    @Override
    public void afkEnd(Player player) {
        if (activeMessage != null && !activeMessage.equals("") && !activeMessage.equals("null")) {
            String coloredText = translateAlternateColorCodes('&', activeMessage);
            player.sendMessage(coloredText);
        }
        player.resetTitle();
        player.removePotionEffect(PotionEffectType.BLINDNESS);
    }

    @Override
    public void afkCycle(Player player) {
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
