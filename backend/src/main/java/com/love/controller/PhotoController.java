package com.love.controller;

import com.love.entity.Photo;
import com.love.service.PhotoFileService;
import com.love.service.PhotoService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @Resource
    private PhotoFileService photoFileService;

    @GetMapping("/list")
    public List<Photo> list(@RequestParam String coupleCode) {
        return photoService.list(coupleCode);
    }

    /** 上传照片（文件内容存数据库，保证云端部署不丢失） */
    @PostMapping("/upload")
    public Photo upload(@RequestParam("coupleCode") String coupleCode,
                        @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                        @RequestParam("file") MultipartFile file) throws Exception {
        // 生成唯一文件名，保留后缀
        String original = file.getOriginalFilename();
        String ext = original != null && original.contains(".")
                ? original.substring(original.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        String url = "/uploads/" + fileName;

        // 文件二进制存数据库
        photoFileService.save(url, file.getContentType(), file.getBytes());

        // 存照片记录
        Photo photo = new Photo();
        photo.setCoupleCode(coupleCode);
        photo.setPhotoUrl(url);
        photo.setRemark(remark);
        return photoService.add(photo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        photoService.delete(id);
    }

    /** 编辑照片：修改备注，可选替换图片（传了 file 就替换） */
    @PostMapping("/{id}/update")
    public Photo update(@PathVariable Long id,
                        @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                        @RequestParam(value = "file", required = false) MultipartFile file) throws Exception {
        byte[] data = null;
        String contentType = null;
        if (file != null && !file.isEmpty()) {
            data = file.getBytes();
            contentType = file.getContentType();
        }
        return photoService.update(id, remark, contentType, data);
    }
}
