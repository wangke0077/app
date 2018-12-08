package com.offcn.service;

import com.offcn.pojo.AppInfo;
import com.offcn.pojo.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface AppInfoService {
    public Page getAppInfo(AppInfo info, String pageSize, String nowPage);

    public AppInfo getById(Integer id,String APKName) ;
    public Integer addInfo(AppInfo appInfo);
    public Integer updateLogopath(AppInfo appInfo);
    public Integer updateAppInfo(AppInfo appInfo);

    public Map<String,Object> delApp(String id) throws Exception;

}
