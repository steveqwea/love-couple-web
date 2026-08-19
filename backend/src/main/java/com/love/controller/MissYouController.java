package com.love.controller;

import com.love.entity.MissYou;
import com.love.service.MissYouService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping("/api/missyou")
public class MissYouController {

    @Resource
    private MissYouService missYouService;

    /** 点击"想你了"（当日计数+1） */
    @PostMapping("/click")
    public Map<String, Object> click(@RequestBody MissYou missYou) {
        return missYouService.click(missYou);
    }

    /** 查询今日双方想你的次数 */
    @GetMapping("/stats")
    public Map<String, Object> stats(@RequestParam String coupleCode, @RequestParam Long userId) {
        return missYouService.stats(coupleCode, userId);
    }
}
