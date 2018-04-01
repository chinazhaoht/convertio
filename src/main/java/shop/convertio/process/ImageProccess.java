package shop.convertio.process;

import com.jhlabs.image.ContrastFilter;
import com.jhlabs.image.HSBAdjustFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import shop.convertio.utils.GenerateFileaNameUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @author zhaoht
 * @date 2017/7/19 18:12
 */
@Component
@Scope("prototype")
public class ImageProccess {

    private BufferedImage srcFile;
    private BufferedImage dstFile;

    private float contrast;// 对比度
    private float lightness;// 亮度
    private float saturate ;//饱和度

    public ImageProccess setSrcFile(File src){
        try {
            srcFile = ImageIO.read(src);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }
    public ImageProccess setcontrast(float contrast){
        this.contrast = contrast;
        return this;
    }

    public ImageProccess setLightNess(float lightNess){
        this.lightness = lightNess;
        return this;
    }

    public ImageProccess setSaturate(float saturate){
        this.saturate = saturate;
        return this;
    }

    public File build(){
        ContrastFilter contrastFilter = new ContrastFilter();
        contrastFilter.setContrast(this.contrast);
        contrastFilter.setBrightness(this.lightness);

        dstFile = contrastFilter.filter(srcFile,dstFile);
        HSBAdjustFilter hsbAdjustFilter = new HSBAdjustFilter();
        hsbAdjustFilter.setSFactor(saturate);
        hsbAdjustFilter.filter(dstFile,srcFile);
        File file = new File(GenerateFileaNameUtil.generate("jpg"));

        try {
            ImageIO.write(srcFile,"jpeg",file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;
    }



}
