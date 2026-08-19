package com.love.service;

import com.love.entity.MissYou;
import com.love.mapper.MissYouMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MissYouService {

    @Resource
    private MissYouMapper missYouMapper;

    /** 点击"想你了"：当日计数+1，返回双方当日次数与特殊事件（520/1314 当日首次触发） */
    public Map<String, Object> click(MissYou missYou) {
        if (missYou.getCoupleCode() == null || missYou.getCoupleCode().trim().isEmpty()) {
            throw new RuntimeException("请先完成配对后再使用想你了");
        }
        if (missYou.getUserId() == null) {
            throw new RuntimeException("登录状态失效，请重新登录");
        }
        LocalDate today = LocalDate.now();
        int updated = missYouMapper.increaseCount(missYou.getUserId(), today);
        if (updated == 0) {
            missYou.setMissDate(today);
            missYou.setCount(1);
            try {
                missYouMapper.insert(missYou);
            } catch (Exception e) {
                // 并发下可能已被插入，直接累加
                missYouMapper.increaseCount(missYou.getUserId(), today);
            }
        }
        MissYou exist = missYouMapper.findByUserIdAndDate(missYou.getUserId(), today);
        int myCount = exist == null ? 1 : exist.getCount();

        String special = null;
        if (myCount == 520) {
            special = "520";
        } else if (myCount == 1314) {
            special = "1314";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("myCount", myCount);
        result.put("partnerCount", partnerCount(missYou.getCoupleCode(), missYou.getUserId()));
        result.put("special", special);
        return result;
    }

    /** 查询今日双方次数 */
    public Map<String, Object> stats(String coupleCode, Long userId) {
        Map<String, Object> result = new HashMap<>();
        result.put("myCount", myCount(userId));
        result.put("partnerCount", partnerCount(coupleCode, userId));
        return result;
    }

    private int myCount(Long userId) {
        MissYou m = missYouMapper.findByUserIdAndDate(userId, LocalDate.now());
        return m == null ? 0 : m.getCount();
    }

    private int partnerCount(String coupleCode, Long userId) {
        List<MissYou> list = missYouMapper.listByCoupleCodeAndDate(coupleCode, LocalDate.now());
        return list.stream()
                .filter(m -> !m.getUserId().equals(userId))
                .mapToInt(MissYou::getCount)
                .sum();
    }
}
