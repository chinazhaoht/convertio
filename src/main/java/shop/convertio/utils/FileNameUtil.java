package shop.convertio.utils;

/**
 * @author zhaoht
 * @date 2017/6/26 14:54
 */
public class FileNameUtil {

    public static String getFileExtName(String fileName){
        String[] imageNameArray = fileName.split("\\.");
        String extName = imageNameArray[imageNameArray.length - 1];

        return extName;
    }

    public static String getFileRealName(String fileName){
        String[] imageNameArray = fileName.split("\\.");
        String realName = imageNameArray[0];

        return realName;
    }

    public static String getBase64FileType(String header){
        String[] array = header.split(";");
        String[] array2 = array[0].split("\\/");
        String extName = array2[array2.length -1];
        return extName;
    }


    public static void main(String[] args) {
        String header = "data:audio/webm;base64";
        System.out.println(getBase64FileType(header));
    }
}
