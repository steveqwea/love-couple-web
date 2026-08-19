package com.love.controller;

import com.love.entity.Wish;
import com.love.service.WishService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/wish")
public class WishController {

    @Resource
    private WishService wishService;

    @GetMapping("/list")
    public List<Wish> list(@RequestParam String coupleCode) {
        return wishService.list(coupleCode);
    }

    @PostMapping("/add")
    public Wish add(@RequestBody Wish wish) {
        return wishService.add(wish);
    }

    @PutMapping("/finish/{id}")
    public void finish(@PathVariable Long id) {
        wishService.finish(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        wishService.delete(id);
    }
}
