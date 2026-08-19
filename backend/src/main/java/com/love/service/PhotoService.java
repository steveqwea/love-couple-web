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

    /** 修改照片备注，file 不为空时同时替换图片 */
    public Photo update(Long id, String remark, String contentType, byte[] data) {
        Photo photo = photoMapper.selectById(id);
        if (photo == null) {
            throw new RuntimeException("照片不存在");
        }
        if (data != null && data.length > 0) {
            // 生成新文件名存入数据库，删除旧文件
            String oldUrl = photo.getPhotoUrl();
            String ext = oldUrl != null && oldUrl.contains(".")
                    ? oldUrl.substring(oldUrl.lastIndexOf(".")) : ".jpg";
            String newUrl = "/uploads/" + java.util.UUID.randomUUID().toString().replace("-", "") + ext;
            photoFileService.save(newUrl, contentType, data);
            photoMapper.updateUrlAndRemark(id, newUrl, remark);
            photoFileService.deleteByUrl(oldUrl);
            photo.setPhotoUrl(newUrl);
        } else {
            photoMapper.updateRemark(id, remark);
        }
        photo.setRemark(remark);
        return photo;
    }
}
