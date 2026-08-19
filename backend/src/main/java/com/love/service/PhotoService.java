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

    public Photo add(Photo photo) {
        photoMapper.insert(photo);
        return photo;
    }

    public List<Photo> list(String coupleCode) {
        return photoMapper.listByCoupleCode(coupleCode);
    }

    public void delete(Long id) {
        photoMapper.deleteById(id);
    }
}
