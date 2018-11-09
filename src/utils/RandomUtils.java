package utils;

import java.util.Random;

/**
 * 生成任意长度的字符串
 * Created by Administrator on  2018/9/21
 */
public class RandomUtils {

    /**
     * 生成任意长度的字符串
     * @param int 字符串长度
     * @return
     */
    public static String getRandomChars(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<length;i++){
            //获取一个随机数，范围：0——base.length
            int randomInt = random.nextInt(base.length());
            builder.append(base.charAt(randomInt));
        }
        return builder.toString();
    }


}
