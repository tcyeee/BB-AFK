package top.tcyeee.common;

import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * @author tcyeee
 * @date 2022/1/25 15:35
 */
public final class BaseUtils {


    /**
     * 在玩家周围生成粒子特效
     *
     * @param player 玩家信息
     * @param count  生成的粒子数量 1~50 之间
     */
    public static void spawn(Player player, int count) {
        if (count <= 0 || count > 50) {
            return;
        }

        World world = player.getWorld();
        double r = 0.7;
        double m = (2 * Math.PI) / count;   // 弧度
        double x, z;
        for (int i = 0; i < count; i++) {
            x = r * Math.sin(m * i);
            z = r * Math.cos(m * i);
            world.spawnParticle(Particle.ENCHANTMENT_TABLE, player.getLocation().add(x, Math.random() * 2, z), 1);
        }
    }
}
