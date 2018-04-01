package shop.convertio.process;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import shop.convertio.config.StorgeConfig;
import shop.convertio.intf.IImageProccess;
import shop.convertio.utils.GenerateFileaNameUtil;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author zhaoht
 * @date 2017/6/23 15:28
 */

@org.springframework.stereotype.Component
public class ThumbnailImageProcess implements IImageProccess {

    @Autowired
    private StorgeConfig config;


    public File resize(Integer w, Integer h, InputStream inputStream) {
        try {
            Image image = ImageIO.read(inputStream);
            int sourceW = image.getWidth(null);
            int sourceH = image.getHeight(null);

            float defaultW = 192f;
            float defaultH = 96f;
            float thumRate = 0.0f;
            if(w == null || h == null){
                if(defaultW / sourceW < defaultH / sourceH){
                    thumRate = defaultW / sourceW;
                }else{
                    thumRate = defaultH / sourceH;
                }

                w = new Float(sourceW * thumRate).intValue();
                h = new Float(sourceH * thumRate).intValue();
            }



            BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            bufferedImage.getGraphics().drawImage(image, 0, 0, w, h, null);
            String fileName = GenerateFileaNameUtil.generate("jpg");
            File targetFile = new File(config.getTmpDir() + File.separator + fileName);

            ImageIO.write(bufferedImage,"jpg",targetFile);
            if(w < defaultW  && h < defaultH){
                IOUtils.copy(inputStream,new FileOutputStream(targetFile));
            }
           FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fileOutputStream);
            JPEGEncodeParam encodeParam = JPEGCodec.getDefaultJPEGEncodeParam(bufferedImage);

            encodeParam.setQuality(0.75f,true);
            encoder.setJPEGEncodeParam(encodeParam);
            encoder.encode(bufferedImage);

            System.out.println(targetFile.getAbsoluteFile());
            return targetFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


  /*  @Override
    public File resize(Integer w, Integer h, InputStream inputStream) {

        Image image = null;
        try {
            image = ImageIO.read(inputStream);
            int sourceW = image.getWidth(null);
            int sourceH = image.getHeight(null);

            float defaultW = 192f;
            float defaultH = 96f;
            float thumRate = 0.0f;
            if(w == null || h == null){
                if(defaultW / sourceW < defaultH / sourceH){
                    thumRate = defaultW / sourceW;
                }else{
                    thumRate = defaultH / sourceH;
                }

                w = new Float(sourceW * thumRate).intValue();
                h = new Float(sourceH * thumRate).intValue();
            }

            String fileName = GenerateFileaNameUtil.generate("jpg");
            File targetFile = new File(config.getTmpDir() + File.separator + fileName);
            Thumbnails.of(inputStream)
                    .size(w,h)
                    .toFile(targetFile);

            return targetFile;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
*/
    public static void main(String[] args) {
     /*   ThumbnailImageProcess imageProcess = new ThumbnailImageProcess();
        try {
            //imageProcess.resize(500, 300, new FileInputStream(new File("E:\\timg.jpg")));

            String base64String = Base64.encode(FileUtils.readFileToByteArray(new File("E:\\timg.jpg")));
            System.out.println(base64String);
            File targetFile = new File("e:\\test111.jpg");

            byte[] fileByte = Base64.decode(base64String);

            //IOUtils.copyBytes(new ByteArrayInputStream(fileByte),new FileOutputStream(targetFile),1024,true);
            Image image = ImageIO.read(new ByteArrayInputStream(fileByte));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Base64DecodingException e) {
            e.printStackTrace();
        }*/
    }
}
