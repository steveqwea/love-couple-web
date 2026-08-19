package com.love.mapper;

import com.love.entity.Wish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface WishMapper {
    int insert(Wish wish);

    List<Wish> listByCoupleCode(@Param("coupleCode") String coupleCode);

    int finish(@Param("id") Long id);

    int deleteById(@Param("id") Long id);
}
