package com.offcn.service;

import com.offcn.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
public interface AppVersionService {
    public List<AppVersion> getByAppId( Integer appId);
    public Integer addAppVersion(AppVersion appVersion);
    public AppVersion getAppVersionByVid(@Param("id") Integer id);
    public Integer updateAppVersionLocalByVid(AppVersion appVersion);
    public Integer delAppVersion(Integer id);
}
