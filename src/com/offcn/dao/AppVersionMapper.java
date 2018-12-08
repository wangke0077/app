package com.offcn.dao;

import com.offcn.pojo.AppVersion;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppVersionMapper {

    public List<AppVersion> getByAppId(@Param("appId") Integer appId);

    public Integer addAppVersion(AppVersion appVersion);

    public AppVersion getAppVersionByVid(@Param("id") Integer id);

    public Integer updateAppVersionLocalByVid(AppVersion appVersion);

    public Integer delAppVersion(@Param("id") Integer id);
}
