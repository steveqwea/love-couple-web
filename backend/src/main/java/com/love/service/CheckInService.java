package com.love.service;

import com.love.entity.CheckIn;
import com.love.mapper.CheckInMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

@Service
public class CheckInService {

    @Resource
    private CheckInMapper checkInMapper;

    /** 提交打卡：每人每天只能打一次卡，提交后不可修改（双方可各自打卡） */
    public CheckIn submit(CheckIn checkIn) {
        if (checkIn.getCoupleCode() == null || checkIn.getCoupleCode().trim().isEmpty()) {
            throw new RuntimeException("请先完成配对后再打卡");
        }
        if (checkIn.getUserId() == null) {
            throw new RuntimeException("登录状态失效，请重新登录");
        }
        if (checkIn.getMood() == null || checkIn.getMood().trim().isEmpty()) {
            throw new RuntimeException("请选择今天的心情");
        }
        if (checkIn.getLoveDegree() == null || checkIn.getLoveDegree() < 1 || checkIn.getLoveDegree() > 5) {
            throw new RuntimeException("请选择恩爱程度（1-5星）");
        }
        LocalDate today = LocalDate.now();
        if (checkInMapper.findByUserIdAndDate(checkIn.getUserId(), today) != null) {
            throw new RuntimeException("你今天已经打过卡啦，打卡后无法修改哦");
        }
        checkIn.setCheckDate(today);
        checkInMapper.insert(checkIn);
        return checkInMapper.findByUserIdAndDate(checkIn.getUserId(), today);
    }

    /** 查询今天双方的打卡记录（最多两条：我 + TA） */
    public List<CheckIn> today(String coupleCode) {
        return checkInMapper.listByCoupleCodeAndDate(coupleCode, LocalDate.now());
    }

    /** 打卡历史 */
    public List<CheckIn> list(String coupleCode) {
        return checkInMapper.listByCoupleCode(coupleCode);
    }
}
