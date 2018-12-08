package com.offcn.service;

import com.offcn.pojo.DevUser;

public interface DevUserService {
    public DevUser getUserByName(String name,String password);
}
