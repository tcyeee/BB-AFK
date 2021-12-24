package top.tcyeee.scheduled;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.tcyeee.utils.EhCacheUtil;

/**
 * 游戏中定时检查组件
 *
 * @author tcyeee
 * @date 2021/12/24 下午10:25
 */
@Component
public class UserStatusCheck {


    /**
     * 定时任务:
     * 每间隔1分钟查询一次缓存:
     * 1.比对playerlist和缓存的差值,其中没有的,存入挂机缓存{user,setTime},表示为挂机状态
     * 2.查询挂机缓存, 挂机时间与当前时间差值为 n 的倍数, 则给与经验x,金币x
     */
    @Scheduled(cron = "0 * * * * ? *")
    public void checkUserStatus() {
        // 1. 计算挂机玩家  = 全部玩家 - 活跃玩家
        EhCacheUtil.findAll(EhCacheUtil.status.active);
        EhCacheUtil.findAll(EhCacheUtil.status.afk);

        // 2. 查询所有挂机玩家挂机时间, 距离现在为5的整数倍, 则给予经验和金币


    }
}
