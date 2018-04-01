package shop.convertio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhaoht
 * @date 2017/6/23 14:09
 */
@Component
public class StorgeConfig {

    @Value("${storge.type}")
    private int type;

    @Value("${storge.maxSize}")
    private int maxSize;

    @Value("${storge.fileType}")
    private String[] fileType;


    @Value("${storge.srcFileDir}")
    private String srcFileDir;

    @Value("${storge.srcFileDir")
    public String tmpDir;

    @Value("${storge.targetFileDir}")
    private String targetFileDir;

    @Value("${storge.endPoint}")
    private String endPoint;

    public String getSrcFileDir() {
        return srcFileDir;
    }

    public void setSrcFileDir(String srcFileDir) {
        this.srcFileDir = srcFileDir;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public String[] getFileType() {
        return fileType;
    }

    public void setFileType(String[] fileType) {
        this.fileType = fileType;
    }


    public String getTargetFileDir() {
        return targetFileDir;
    }

    public void setTargetFileDir(String targetFileDir) {
        this.targetFileDir = targetFileDir;
    }

    public String getTmpDir() {
        return tmpDir;
    }

    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }
}
