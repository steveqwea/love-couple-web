package com.love.controller;

import com.love.entity.Anniversary;
import com.love.service.AnniversaryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/anniversary")
public class AnniversaryController {

    @Resource
    private AnniversaryService anniversaryService;

    @GetMapping("/list")
    public List<Anniversary> list(@RequestParam String coupleCode) {
        return anniversaryService.list(coupleCode);
    }

    @PostMapping("/add")
    public Anniversary add(@RequestBody Anniversary anniversary) {
        return anniversaryService.add(anniversary);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        anniversaryService.delete(id);
    }
}
