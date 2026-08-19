package com.love.controller;

import com.love.entity.Photo;
import com.love.service.PhotoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Resource
    private PhotoService photoService;

    @Value("${app.upload-dir:uploads}")
    private String uploadDir;

    @GetMapping("/list")
    public List<Photo> list(@RequestParam String coupleCode) {
        return photoService.list(coupleCode);
    }

    /** 上传照片 */
    @PostMapping("/upload")
    public Photo upload(@RequestParam("coupleCode") String coupleCode,
                        @RequestParam(value = "remark", required = false, defaultValue = "") String remark,
                        @RequestParam("file") MultipartFile file) throws Exception {
        // 创建上传目录
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        // 生成唯一文件名，保留后缀
        String original = file.getOriginalFilename();
        String ext = original != null && original.contains(".")
                ? original.substring(original.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        File target = new File(dir, fileName);
        file.transferTo(target.getAbsoluteFile());

        // 存数据库
        Photo photo = new Photo();
        photo.setCoupleCode(coupleCode);
        photo.setPhotoUrl("/uploads/" + fileName);
        photo.setRemark(remark);
        return photoService.add(photo);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        photoService.delete(id);
    }
}
