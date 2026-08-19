package com.love.mapper;

import com.love.entity.CheckIn;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface CheckInMapper {
    int insert(CheckIn checkIn);

    CheckIn findByUserIdAndDate(@Param("userId") Long userId, @Param("checkDate") LocalDate checkDate);

    List<CheckIn> listByCoupleCodeAndDate(@Param("coupleCode") String coupleCode, @Param("checkDate") LocalDate checkDate);

    List<CheckIn> listByCoupleCode(@Param("coupleCode") String coupleCode);
}
