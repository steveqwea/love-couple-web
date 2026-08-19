package com.love.controller;

import com.love.entity.CoupleUser;
import com.love.service.CoupleUserService;
import com.love.service.PhotoFileService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;
import java.util.HashMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
public class CoupleUserController {

    @Resource
    private CoupleUserService coupleUserService;

    @Resource
    private PhotoFileService photoFileService;

    /** 注册 */
    @PostMapping("/register")
    public CoupleUser register(@RequestBody CoupleUser user) {
        return coupleUserService.register(user.getNickname(), user.getPassword());
    }

    /** 登录 */
    @PostMapping("/login")
    public CoupleUser login(@RequestBody CoupleUser user) {
        return coupleUserService.login(user.getNickname(), user.getPassword());
    }

    /** 获取用户信息 */
    @GetMapping("/info/{id}")
    public CoupleUser info(@PathVariable Long id) {
        return coupleUserService.getInfo(id);
    }

    /** 获取伴侣信息 */
    @GetMapping("/partner/{id}")
    public Map<String, Object> partner(@PathVariable Long id) {
        Map<String, Object> result = new HashMap<>();
        CoupleUser partner = coupleUserService.getPartner(id);
        result.put("hasPartner", partner != null);
        if (partner != null) {
            result.put("nickname", partner.getNickname());
            result.put("avatar", partner.getAvatar());
        }
        return result;
    }

    /** 配对 */
    @PostMapping("/pair")
    public CoupleUser pair(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String targetCode = body.get("coupleCode").toString();
        return coupleUserService.pair(id, targetCode);
    }

    /** 修改密码 */
    @PostMapping("/password")
    public CoupleUser password(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String oldPassword = body.get("oldPassword").toString();
        String newPassword = body.get("newPassword").toString();
        return coupleUserService.changePassword(id, oldPassword, newPassword);
    }

    /** 更新头像 */
    @PostMapping("/avatar")
    public CoupleUser avatar(@RequestBody Map<String, Object> body) {
        Long id = Long.valueOf(body.get("id").toString());
        String avatar = body.get("avatar").toString();
        return coupleUserService.updateAvatar(id, avatar);
    }

    /** 上传头像文件（携带 userId 时一步完成上传+存库），文件内容存数据库 */
    @PostMapping("/avatar/upload")
    public Map<String, Object> uploadAvatar(@RequestParam("file") MultipartFile file,
                                            @RequestParam(value = "userId", required = false) Long userId) throws Exception {
        String original = file.getOriginalFilename();
        String ext = original != null && original.contains(".")
                ? original.substring(original.lastIndexOf(".")) : ".jpg";
        String fileName = UUID.randomUUID().toString().replace("-", "") + ext;
        String url = "/uploads/avatar/" + fileName;
        // 文件二进制存数据库
        photoFileService.save(url, file.getContentType(), file.getBytes());
        // 带 userId 时直接保存到数据库，前端只调一次接口，避免二次请求失败
        if (userId != null) {
            coupleUserService.updateAvatar(userId, url);
        }
        Map<String, Object> res = new HashMap<>();
        res.put("url", url);
        return res;
    }
}
