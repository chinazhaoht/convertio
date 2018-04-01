package shop.convertio.process;

import it.sauronsoftware.jave.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import shop.convertio.intf.IVideoProccess;
import shop.convertio.utils.FileNameUtil;
import shop.convertio.utils.GenerateFileaNameUtil;

import java.io.File;
import java.io.IOException;

/**
 * @author zhaoht
 * @date 2017/6/26 10:28
 * <p>
 * <p>
 * 视频旋转方法介绍url:http://blog.csdn.net/lhtzbj12/article/details/53538857
 */

@Component
public class VideoProccess implements IVideoProccess {


    static final Logger logger = LoggerFactory.getLogger(VideoProccess.class);

    @Value("${storge.tmpDir}")
    private String tmpDir;


    /**
     * amr格式转成MP4格式
     *
     * @param srcFile
     * @return
     */
    public File amrToMp4(File srcFile) {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libfaac");
        audio.setBitRate(new Integer(128000));
        audio.setSamplingRate(new Integer(44100));
        audio.setChannels(new Integer(2));
        VideoAttributes videoAttributes = new VideoAttributes();
        videoAttributes.setCodec("mpeg4");
        videoAttributes.setBitRate(new Integer(160000));
        videoAttributes.setFrameRate(new Integer(15));
        videoAttributes.setSize(new VideoSize(176, 144));

        EncodingAttributes attributes = new EncodingAttributes();
        attributes.setFormat("mp4");
        attributes.setAudioAttributes(audio);
        attributes.setVideoAttributes(videoAttributes);
        Encoder encoder = new Encoder();
        String targetFileName = GenerateFileaNameUtil.generate("mp4");
        File targetFile = new File(tmpDir + targetFileName);

        try {

            encoder.encode(srcFile, targetFile, attributes);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }


    /**
     *mp4转ogg格式
     */

    public File mp4ToOgg(File srcFile){

        AudioAttributes audio = new AudioAttributes();
       // audio.setCodec("libfaac");
        audio.setCodec("vorbis");
        audio.setBitRate(new Integer(128000));
        audio.setSamplingRate(new Integer(44100));
        audio.setChannels(new Integer(2));
        VideoAttributes videoAttributes = new VideoAttributes();
       // videoAttributes.setCodec("mpeg4");
        videoAttributes.setCodec("libtheora");

        videoAttributes.setBitRate(new Integer(160000));
        videoAttributes.setFrameRate(new Integer(15));
        videoAttributes.setSize(new VideoSize(176, 144));


        EncodingAttributes attributes = new EncodingAttributes();
        attributes.setFormat("ogg");
        attributes.setAudioAttributes(audio);
        attributes.setVideoAttributes(videoAttributes);
        Encoder encoder = new Encoder();
        String targetFileName = GenerateFileaNameUtil.generate("ogg");
        File targetFile = new File(tmpDir + targetFileName);

        try {
            encoder.encode(srcFile, targetFile, attributes);
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        return targetFile;
    }

    /**
     * MP4转webm格式的文件
     * @param srcFile
     * @return
     */
    public File mp4ToWebM(File srcFile){


        String newFileName = GenerateFileaNameUtil.generate("webm");

        File newFile = null;

        String command = "ffmpeg -i "+ srcFile.getAbsolutePath() +" -c:v libvpx -crf 10 -b:v 1M -c:a libvorbis" + " " + newFileName;

        System.out.println(command);
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(command);
            process.waitFor();
            newFile = new File(newFileName);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e1) {
            e1.printStackTrace();
        } finally {

            //销毁子进程
            if(process != null){
                process.destroy();
            }
        }

        return newFile;
    }

    /**
     * mp4文件,视频编码格式H.264,音频编码格式aac
     * @param srcFile
     * @return
     */

    // TODO: 2018/1/25 InputFormatException 输入格式异常
    public File mp4Codec(File srcFile){
        AudioAttributes audio = new AudioAttributes();
        // audio.setCodec("libfaac");
        audio.setCodec("libfaac");
        audio.setBitRate(new Integer(128000));
        audio.setSamplingRate(new Integer(44100));
        audio.setChannels(new Integer(2));
        VideoAttributes videoAttributes = new VideoAttributes();
        // videoAttributes.setCodec("mpeg4");
        videoAttributes.setCodec("libx264");

        videoAttributes.setBitRate(new Integer(160000));
        videoAttributes.setFrameRate(new Integer(15));
        videoAttributes.setSize(new VideoSize(176, 144));


        EncodingAttributes attributes = new EncodingAttributes();
        attributes.setFormat("mp4");
        attributes.setAudioAttributes(audio);
        attributes.setVideoAttributes(videoAttributes);
        Encoder encoder = new Encoder();
        String targetFileName = GenerateFileaNameUtil.generate("mp4");
        File targetFile = new File(tmpDir + targetFileName);

        try {
            encoder.encode(srcFile, targetFile, attributes);
        } catch (EncoderException e) {
            e.printStackTrace();
        }

        return targetFile;
    }
    /**
     * 生成视频截图
     *
     * @param srcFile
     * @return
     */

    public File getScreenshot(File srcFile) {
        File target = new File(tmpDir + GenerateFileaNameUtil.generate("png"));
        VideoAttributes videoAttributes = new VideoAttributes();
        videoAttributes.setCodec("png");
        videoAttributes.setSize(new VideoSize(600, 500));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("image2");
        attrs.setOffset(3f);
        attrs.setDuration(0.01f);
        attrs.setVideoAttributes(videoAttributes);
        Encoder encoder = new Encoder();
        try {
            //获取播放时长
            MultimediaInfo m = encoder.getInfo(srcFile);
            long playTime = m.getDuration();
            m.getVideo().getBitRate();

            logger.debug("playtime = {}", playTime);

            encoder.encode(srcFile, target, attrs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return target;
    }

    /**
     * 获取播放时长
     *
     * @param srcFile
     * @return
     */
    public long getPlayTime(File srcFile) {

        Encoder encoder = new Encoder();
        //获取播放时长
        MultimediaInfo m = null;
        long playTime = 0;
        try {
            m = encoder.getInfo(srcFile);
            playTime = m.getDuration();
            logger.debug("playtime = {}", playTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return playTime;
    }


    public File rotate(File srcFile) {
        String os = System.getProperty("os.name").toLowerCase();
        String cmd = null;
        String dir = srcFile.getAbsolutePath();
        String extName = FileNameUtil.getFileExtName(srcFile.getName());
        String newFileName = GenerateFileaNameUtil.generate(extName);
        if ("Windows".equals(os)) {
            cmd = " ffprobe.exe -print_format json -select_streams v -show_streams -i" + " " + dir;
        } else {
            //cmd = " ffprobe -print_format json -select_streams v -show_streams -i" + " " + srcFile.getAbsolutePath();
            StringBuilder sb = new StringBuilder("ffmpeg -i ").append(srcFile.getAbsoluteFile()).append(" ")
                    .append("-c copy -metadate:s:v:0 rotate=90").append(srcFile.getParent()).append(newFileName);
            cmd = sb.toString();

        }
        File newFile = null;
        try {
            Runtime.getRuntime().exec(cmd);
            newFile = new File(newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return newFile;
    }


   public File toMp4(File srcFile,String targetFileName){

       File newFile = null;

       String command = "ffmpeg -i "+ srcFile.getAbsolutePath() + " "+  targetFileName;

       System.out.println(command);
       Process process = null;
       try {
           process = Runtime.getRuntime().exec(command);
           process.waitFor();
           newFile = new File(targetFileName);

       } catch (IOException e) {
           e.printStackTrace();
       } catch (InterruptedException e1) {
           e1.printStackTrace();
       } finally {

           //销毁子进程
           if(process != null){
               process.destroy();
           }
       }
       return newFile;
   }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public static void main(String[] args) {
        VideoProccess videoProccess = new VideoProccess();
        videoProccess.setTmpDir("~/tmp/");

       // File file = videoProccess.movToMp4(new File("/Users/zhaohongtao/Downloads/test1.mov"));
       // File file = videoProccess.mp4Codec(new File("~/project/ffmpeg/hello.mp4"));
        //System.out.println(file.getAbsolutePath());

    }
}
