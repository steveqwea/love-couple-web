package com.love.mapper;

import com.love.entity.Anniversary;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnniversaryMapper {
    int insert(Anniversary anniversary);

    List<Anniversary> listByCoupleCode(@Param("coupleCode") String coupleCode);

    int deleteById(@Param("id") Long id);
}
