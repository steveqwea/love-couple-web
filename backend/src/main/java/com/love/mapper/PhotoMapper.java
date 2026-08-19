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

    int updateRemark(@Param("id") Long id, @Param("remark") String remark);

    int updateUrlAndRemark(@Param("id") Long id, @Param("photoUrl") String photoUrl, @Param("remark") String remark);
}
