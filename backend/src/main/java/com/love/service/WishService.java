package com.love.service;

import com.love.entity.Wish;
import com.love.mapper.WishMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WishService {

    @Resource
    private WishMapper wishMapper;

    public Wish add(Wish wish) {
        wishMapper.insert(wish);
        return wish;
    }

    public List<Wish> list(String coupleCode) {
        return wishMapper.listByCoupleCode(coupleCode);
    }

    public void finish(Long id) {
        wishMapper.finish(id);
    }

    public void delete(Long id) {
        wishMapper.deleteById(id);
    }
}
