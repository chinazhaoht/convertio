package shop.convertio.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class AudioUtils {

    final static Logger logger = LoggerFactory.getLogger(AudioUtils.class);

    public static void arm2mp3(String amrFileName,String mp3FileName){
        Runtime runtime = Runtime.getRuntime();

        try {
            String cmd = "ffmpeg -i " + amrFileName + " -ar 8000 -ac 1 -y -ab 12.4k " + mp3FileName ;
            logger.info("cmd = <{}>",cmd);
            Process process = runtime.exec(cmd);

            if(process.exitValue() != 0){
                logger.error("转换失败");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
