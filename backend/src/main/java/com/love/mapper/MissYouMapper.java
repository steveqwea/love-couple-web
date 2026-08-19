package com.love.mapper;

import com.love.entity.MissYou;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface MissYouMapper {
    int insert(MissYou missYou);

    int increaseCount(@Param("userId") Long userId, @Param("missDate") LocalDate missDate);

    MissYou findByUserIdAndDate(@Param("userId") Long userId, @Param("missDate") LocalDate missDate);

    List<MissYou> listByCoupleCodeAndDate(@Param("coupleCode") String coupleCode, @Param("missDate") LocalDate missDate);
}
