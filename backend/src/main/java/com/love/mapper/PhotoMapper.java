package com.love.mapper;

import com.love.entity.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PhotoMapper {
    int insert(Photo photo);

    List<Photo> listByCoupleCode(@Param("coupleCode") String coupleCode);

    Photo selectById(@Param("id") Long id);

    int deleteById(@Param("id") Long id);
}
