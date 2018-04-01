package shop.convertio.intf;

/**
 * @author zhaoht
 * @date 2017/6/26 10:27
 */

import java.io.File;

/**
 * 视频文件格式转换接口
 */
public interface IVideoProccess {

    File amrToMp4(File srcFile);

    File getScreenshot(File srcFile);

    long getPlayTime(File srcFile);

    public File rotate(File srcFile);

    /**
     * mp4转换为Ogg格式
     * @param srcFile
     * @return
     */
    File mp4ToOgg(File srcFile);

    /**
     * MP4转换为webM格式
     * @param srcFile
     * @return
     */
    File mp4ToWebM(File srcFile);

    /**
     * mp4重新编码
     */
    File mp4Codec(File srcFile);

}