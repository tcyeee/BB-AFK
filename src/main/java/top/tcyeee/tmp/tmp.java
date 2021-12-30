package top.tcyeee.tmp;

import top.tcyeee.utils.EhCacheUtil;

/**
 * @author tcyeee
 * @date 2021/12/28 下午10:11
 */
public class tmp {

    public static void main(String[] args) throws InterruptedException {
        EhCacheUtil.set("key", "value");

//        for (int i = 0; i < 5; i++) {
//            System.out.println(i + 1);


            Thread.sleep(3000);
//        }

        String value = EhCacheUtil.get("key");
        System.out.println(value);


    }
}
