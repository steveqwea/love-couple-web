package com.love.service;

import com.love.entity.CoupleUser;
import com.love.mapper.CoupleUserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

@Service
public class CoupleUserService {

    @Resource
    private CoupleUserMapper coupleUserMapper;

    /** 注册：自动生成配对码，username 默认等于 nickname */
    public CoupleUser register(String nickname, String password) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new RuntimeException("昵称不能为空");
        }
        if (password == null || password.length() < 3) {
            throw new RuntimeException("密码至少3位");
        }
        if (coupleUserMapper.findByNickname(nickname) != null) {
            throw new RuntimeException("该昵称已被占用");
        }
        CoupleUser user = new CoupleUser();
        user.setNickname(nickname);
        user.setUsername(nickname);
        user.setPassword(password);
        user.setAvatar("");
        user.setCoupleCode(generateCode());
        coupleUserMapper.insert(user);
        return user;
    }

    /** 登录：用昵称或用户名 + 密码 */
    public CoupleUser login(String nickname, String password) {
        CoupleUser user = coupleUserMapper.findByNickname(nickname);
        if (user == null) {
            user = coupleUserMapper.findByUsername(nickname);
        }
        if (user == null || !user.getPassword().equals(password)) {
            throw new RuntimeException("用户名或密码错误");
        }
        return user;
    }

    /** 获取用户信息 */
    public CoupleUser getInfo(Long id) {
        CoupleUser user = coupleUserMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        return user;
    }

    /** 获取伴侣信息 */
    public CoupleUser getPartner(Long id) {
        CoupleUser user = coupleUserMapper.findById(id);
        if (user == null || user.getPartnerId() == null) {
            return null;
        }
        return coupleUserMapper.findById(user.getPartnerId());
    }

    /** 配对：输入对方的配对码，两人绑定在一起（每人只能绑定一个伴侣） */
    public CoupleUser pair(Long id, String targetCode) {
        CoupleUser me = coupleUserMapper.findById(id);
        if (me == null) {
            throw new RuntimeException("用户不存在");
        }
        List<CoupleUser> partners = coupleUserMapper.findByCoupleCode(targetCode);
        CoupleUser partner = partners.stream()
                .filter(p -> !p.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (partner == null) {
            throw new RuntimeException("未找到该配对码对应的用户");
        }
        // 我已配对：若对方就是我的伴侣则幂等返回，否则拒绝
        if (me.getPartnerId() != null) {
            if (me.getPartnerId().equals(partner.getId())) {
                return me;
            }
            throw new RuntimeException("你已与" + partnerName(me.getPartnerId()) + "配对，无法再绑定他人");
        }
        // 对方已配对：若对方绑定的是我则补绑定，否则拒绝
        if (partner.getPartnerId() != null && !partner.getPartnerId().equals(id)) {
            throw new RuntimeException("对方已与" + partnerName(partner.getPartnerId()) + "配对");
        }
        // 双向绑定 + 统一配对码
        coupleUserMapper.updatePartner(id, partner.getId(), targetCode);
        coupleUserMapper.updatePartner(partner.getId(), id, targetCode);
        return coupleUserMapper.findById(id);
    }

    /** 修改密码：校验原密码 */
    public CoupleUser changePassword(Long id, String oldPassword, String newPassword) {
        CoupleUser user = coupleUserMapper.findById(id);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        if (oldPassword == null || !user.getPassword().equals(oldPassword)) {
            throw new RuntimeException("原密码错误");
        }
        if (newPassword == null || newPassword.length() < 3) {
            throw new RuntimeException("新密码至少3位");
        }
        coupleUserMapper.updatePassword(id, newPassword);
        return coupleUserMapper.findById(id);
    }

    /** 伴侣昵称（用于错误提示） */
    private String partnerName(Long partnerId) {
        CoupleUser partner = coupleUserMapper.findById(partnerId);
        return partner != null ? partner.getNickname() : "他人";
    }

    /** 更新头像 */
    public CoupleUser updateAvatar(Long id, String avatar) {
        coupleUserMapper.updateAvatar(id, avatar);
        return coupleUserMapper.findById(id);
    }

    /** 生成6位配对码 */
    private String generateCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 6).toUpperCase();
    }
}
