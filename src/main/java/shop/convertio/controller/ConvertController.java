package shop.convertio.controller;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import shop.convertio.config.StorgeConfig;
import shop.convertio.http.RespResult;
import shop.convertio.model.FileInfo;
import shop.convertio.model.FileType;
import shop.convertio.process.VideoProccess;
import shop.convertio.service.FileInfoService;
import shop.convertio.utils.FileNameUtil;
import shop.convertio.utils.GenerateFileaNameUtil;
import shop.convertio.utils.KeyUtil;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@RequestMapping("/video")
@RestController
public class ConvertController {

    final static Logger logger = LoggerFactory.getLogger(ConvertController.class);


    @Autowired
    public StorgeConfig config;

    @Autowired
    public VideoProccess videoProccess;


    @Autowired
    private FileInfoService fileInfoService;

    /**
     * 将上传的文件转换成MP4格式
     * 支持的格式：mov,avi,flv
     * @param file
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public Object convertMp4(MultipartFile file){


        //源文件信息
        String srcFileName = file.getOriginalFilename();
        String srcExtName = FileNameUtil.getFileExtName(srcFileName);
        String srcFileRealName = FileNameUtil.getFileRealName(srcFileName);
        String srcTmpFileName = GenerateFileaNameUtil.generate(srcExtName);
        String srcFilePath = this.config.getSrcFileDir() + srcTmpFileName;


        //目标文件信息
        String targetFileName = GenerateFileaNameUtil.generate(FileType.MP4);

        String targetFilePath = this.config.getTargetFileDir() + targetFileName;

        FileInfo fileInfo = new FileInfo();
        fileInfo.setUploadTime(System.currentTimeMillis());
        fileInfo.setSrcFileName(file.getOriginalFilename());
        fileInfo.setSrcRealName(srcFileRealName);
        fileInfo.setSrcExtName(srcExtName);
        fileInfo.setTargetPath(targetFilePath);

        String key = KeyUtil.getKey();
        fileInfo.setKey(key);

        fileInfoService.create(fileInfo);


        logger.info("key = {}",key);
        File srcFile = new File(srcFilePath);

        File targetFile = new File(targetFilePath);


        try {
            file.transferTo(srcFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
        targetFile = videoProccess.toMp4(srcFile,targetFilePath);

        Map<String,Object> data = new HashMap<>(2);
        data.put("key",key);
        if(targetFile != null && targetFile.length() != 0){
            return RespResult.build().setData(data);
        }
        return RespResult.build().setSuccess(false);

    }

    @RequestMapping(method = RequestMethod.GET)
    public void download(String key, HttpServletRequest request, HttpServletResponse response){

        FileInfo info = fileInfoService.selectInfoByKey(key);

        String targetPath = info.getTargetPath();

        File targetFile = new File(targetPath);

        InputStream inputStream = null;
        ServletOutputStream servletOutputStream = null;
        try {
            inputStream = new FileInputStream(targetFile);

            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("content-disposition", "attachment;filename=" + info.getSrcRealName() + "." + FileType.MP4);

            servletOutputStream = response.getOutputStream();
            IOUtils.copy(inputStream,servletOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            IOUtils.closeQuietly(inputStream);
        }
    }
}
