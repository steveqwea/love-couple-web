package com.love.service;

import com.love.entity.PhotoFile;
import com.love.mapper.PhotoFileMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PhotoFileService {

    @Resource
    private PhotoFileMapper photoFileMapper;

    /** 保存文件二进制到数据库 */
    public void save(String url, String contentType, byte[] data) {
        PhotoFile file = new PhotoFile();
        file.setUrl(url);
        file.setContentType(contentType);
        file.setData(data);
        photoFileMapper.insert(file);
    }

    public PhotoFile findByUrl(String url) {
        return photoFileMapper.selectByUrl(url);
    }

    public void deleteByUrl(String url) {
        if (url != null) {
            photoFileMapper.deleteByUrl(url);
        }
    }
}
