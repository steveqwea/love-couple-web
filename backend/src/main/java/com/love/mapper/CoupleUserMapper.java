package com.love.mapper;

import com.love.entity.CoupleUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CoupleUserMapper {
    int insert(CoupleUser user);

    CoupleUser findById(@Param("id") Long id);

    CoupleUser findByUsername(@Param("username") String username);

    CoupleUser findByNickname(@Param("nickname") String nickname);

    List<CoupleUser> findByCoupleCode(@Param("coupleCode") String coupleCode);

    int updateCoupleCode(@Param("id") Long id, @Param("coupleCode") String coupleCode);

    int updatePartner(@Param("id") Long id, @Param("partnerId") Long partnerId, @Param("coupleCode") String coupleCode);

    int updateAvatar(@Param("id") Long id, @Param("avatar") String avatar);

    int updatePassword(@Param("id") Long id, @Param("password") String password);
}
