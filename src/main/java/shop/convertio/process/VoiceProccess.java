package shop.convertio.process;

import it.sauronsoftware.jave.*;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.convertio.intf.IVoiceProccess;
import shop.convertio.utils.GenerateFileaNameUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author zhaoht
 * @date 2017/6/23 16:53
 */

@Component
public class VoiceProccess implements IVoiceProccess {

    static final Logger logger = LoggerFactory.getLogger(VoiceProccess.class);

    @Value("${storge.tmpDir}")
    private String tmpDir;


    public File amrToMp3(File srcFile) {
        AudioAttributes audioAttributes = new AudioAttributes();
        Encoder encoder = new Encoder();
        audioAttributes.setCodec("libmp3lame");
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);
        audioAttributes.setBitRate(new Integer(128000));
        audioAttributes.setSamplingRate(new Integer(44100));
        audioAttributes.setChannels(new Integer(2));

        //生成临时文件名
        String tmpFileName = GenerateFileaNameUtil.generate("mp3");
        logger.debug("tmp file is {}", tmpDir + tmpFileName);
        File targetFile = new File(tmpDir + tmpFileName);
        try {
            encoder.encode(srcFile, targetFile, encodingAttributes);
        } catch (Exception e) {
            logger.error("文件 {}生成MP3文件失败",srcFile.getAbsolutePath());
            e.printStackTrace();
            return targetFile;
        }
        return targetFile;
    }


   /* public File amrToMp3(File srcFile){
        //生成临时文件名
        String tmpFileName = GenerateFileaNameUtil.generate("mp3");
        AudioUtils.arm2mp3(srcFile.getAbsolutePath(),this.tmpDir + File.separator + tmpFileName);
        File targetFile = new File(this.tmpDir + File.separator + tmpFileName);

        logger.info("targetFile Name = {}",targetFile.getAbsoluteFile());
        return targetFile;
    }*/

    public File silkToMp3(File srcFile) {
        String os = System.getProperty("os.name").toLowerCase();
        String srcFilePath = srcFile.getAbsolutePath();
        String dir = srcFile.getParent();
        String cmd = "converter.sh" + " " + srcFilePath + " " + "mp3";
        File targetFile = null;
        logger.info("cmd = {}",cmd);
        try {
            if("Windows".equals(os)){
                logger.info("Windows 不支持");
                return null;
            }else{
                Runtime.getRuntime().exec(cmd);
                targetFile = new File(srcFilePath + ".mp3");
                logger.debug("target file is {}",targetFile.getAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return targetFile;
    }

    public int getPlayTime(File file) {
        int playTime = 0;
        try {
            MP3File mp3File = new MP3File(file, 1);
            MP3AudioHeader audioHeader = (MP3AudioHeader) mp3File.getAudioHeader();
            String strlen = audioHeader.getTrackLengthAsString();

            //播放时长，单位s
            playTime = audioHeader.getTrackLength();
            return playTime;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return playTime;
    }


    public static void main(String[] args) throws FileNotFoundException, EncoderException {


        VoiceProccess voiceProccess = new VoiceProccess();


       // voiceProccess.silkToMp3(new File("D:\\迅雷下载\\ffmpeg-20170620-ae6f6d4-win64-static\\ffmpeg-20170620-ae6f6d4-win64-static\\bin\\3.silk"));


        File file = new File("/Users/zhaohongtao/tmp/1506417900929190537862.amr");

        System.out.println(file.getAbsoluteFile());
        System.out.println(file.canRead());
        //new FileInputStream(file);
       File targetFile = voiceProccess.amrToMp3(file);


    }
}
