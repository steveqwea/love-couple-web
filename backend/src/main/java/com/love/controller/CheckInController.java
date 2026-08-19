package com.love.controller;

import com.love.entity.CheckIn;
import com.love.service.CheckInService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/checkin")
public class CheckInController {

    @Resource
    private CheckInService checkInService;

    /** 提交打卡（每人每天一次，提交后不可修改） */
    @PostMapping("/submit")
    public CheckIn submit(@RequestBody CheckIn checkIn) {
        return checkInService.submit(checkIn);
    }

    /** 查询今天双方的打卡记录 */
    @GetMapping("/today")
    public List<CheckIn> today(@RequestParam String coupleCode) {
        return checkInService.today(coupleCode);
    }

    /** 打卡历史 */
    @GetMapping("/list")
    public List<CheckIn> list(@RequestParam String coupleCode) {
        return checkInService.list(coupleCode);
    }
}
