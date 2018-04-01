package shop.convertio.intf;

import java.io.File;
import java.io.InputStream;

/**
 * @author zhaoht
 * @date 2017/6/23 15:45
 */
public interface IImageProccess {
    File resize(Integer w, Integer h, InputStream inputStream);
}
