package com.offcn.service;

import com.offcn.dao.DevUserMapper;
import com.offcn.pojo.DevUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DevUserServiceImpl implements DevUserService{
    @Autowired
    private DevUserMapper devUserMapper;
    @Override
    public DevUser getUserByName(String name, String password) {
        DevUser userByName=null;
        if (name != null&&!"".equals(name)) {
             userByName = devUserMapper.getUserByDevCode(name);
             if (userByName!=null){
                 if (password!=null&&password.equals(userByName.getDevPassword())){
                     return userByName;
                 }
             }

        }
        return null;
    }
}
