package shop.convertio.utils;

import java.util.Random;

public class KeyUtil {
    public static String getKey(){
        String key = System.currentTimeMillis() + String.valueOf(new Random().nextInt());

        return key;
    }
}
