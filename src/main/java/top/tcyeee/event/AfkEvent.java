package top.tcyeee.event;

import org.bukkit.entity.Player;

/**
 * 系统对外接口
 *
 * @author tcyeee
 * @date 2022/1/25 10:20
 */
public interface AfkEvent {
    /* 刚开始挂机时候触发 */
    void afkStart(Player player);

    /* 刚结束挂机时候触发 */
    void afkEnd(Player player);

    /* 在每个奖励发放时间点重复触发 */
    void afkCycle(Player player);
}
