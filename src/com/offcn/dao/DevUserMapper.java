package com.offcn.dao;

import com.offcn.pojo.DevUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DevUserMapper {
    //更具用户名进行查询
    public DevUser getUserByDevCode(@Param("devCode") String devCode);
}
