package shop.convertio.utils;

import java.util.Random;

/**
 * @author zhaoht
 * @date 2017/6/26 11:51
 */
public class GenerateFileaNameUtil {

    public static String generate(String extName){
        Random random = new Random();
        long currentTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder();
        String fileName = sb.append(currentTime)
                .append(random.nextInt())
                .append(".")
                .append(extName)
                .toString();
        return fileName;
    }
}
