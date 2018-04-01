package shop.convertio.service;

import org.springframework.stereotype.Service;
import shop.convertio.mapper.FileInfoMapper;
import shop.convertio.model.FileInfo;

import javax.annotation.Resource;

@Service
public class FileInfoService {

    @Resource
    private FileInfoMapper fileInfoMapper;


    public int create(FileInfo fileInfo){
        return fileInfoMapper.insert(fileInfo);
    }

    public FileInfo selectInfoByKey(String key){

        return fileInfoMapper.selectByKey(key);
    }



}
