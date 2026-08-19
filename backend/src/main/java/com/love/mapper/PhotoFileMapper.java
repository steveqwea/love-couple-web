package com.love.mapper;

import com.love.entity.PhotoFile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PhotoFileMapper {

    int insert(PhotoFile photoFile);

    PhotoFile selectByUrl(@Param("url") String url);

    int deleteByUrl(@Param("url") String url);
}
