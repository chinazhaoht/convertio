package shop.convertio.intf;

import java.io.File;

/**
 * @author zhaoht
 * @date 2017/6/23 16:53
 */


/**
 * 音频文件格式转换接口
 */
public interface IVoiceProccess {

    File amrToMp3(File srcFile);

    int getPlayTime(File file);

    File silkToMp3(File srcFile);
}
