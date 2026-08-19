package com.love.mapper;

import com.love.entity.Message;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    int insert(Message message);

    List<Message> listByCoupleCode(@Param("coupleCode") String coupleCode);

    int deleteById(@Param("id") Long id);
}
