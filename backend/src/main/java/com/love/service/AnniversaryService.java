package com.love.service;

import com.love.entity.Anniversary;
import com.love.mapper.AnniversaryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AnniversaryService {

    @Resource
    private AnniversaryMapper anniversaryMapper;

    public Anniversary add(Anniversary anniversary) {
        anniversaryMapper.insert(anniversary);
        return anniversary;
    }

    public List<Anniversary> list(String coupleCode) {
        return anniversaryMapper.listByCoupleCode(coupleCode);
    }

    public void delete(Long id) {
        anniversaryMapper.deleteById(id);
    }
}
