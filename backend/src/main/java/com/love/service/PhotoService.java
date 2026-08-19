package com.love.service;

import com.love.entity.Photo;
import com.love.mapper.PhotoMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PhotoService {

    @Resource
    private PhotoMapper photoMapper;

    @Resource
    private PhotoFileService photoFileService;

    public Photo add(Photo photo) {
        photoMapper.insert(photo);
        return photo;
    }

    public List<Photo> list(String coupleCode) {
        return photoMapper.listByCoupleCode(coupleCode);
    }

    public void delete(Long id) {
        Photo photo = photoMapper.selectById(id);
        photoMapper.deleteById(id);
        // 同步删除数据库中的文件内容
        if (photo != null) {
            photoFileService.deleteByUrl(photo.getPhotoUrl());
        }
    }
}
