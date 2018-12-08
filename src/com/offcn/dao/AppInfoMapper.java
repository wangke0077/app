package com.offcn.dao;

import com.offcn.pojo.AppInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AppInfoMapper {
    public List<AppInfo> getAppInfo(@Param("info") AppInfo info,@Param("pageSize") Integer pageSize,@Param("nowPage") Integer nowPage);
    public Integer getCount(@Param("info") AppInfo info);
    /*
    * string softwareName
    * int status
    * int flatformid
    * int devid
    * int categoryLevel1
    * int categoryLevel2
    * int categoryLevel3
    *
    * */
    public AppInfo getById(@Param("id") Integer id,@Param("APKName") String APKName);

   public Integer addInfo(AppInfo appInfo);
   public Integer updateLogopath(AppInfo appInfo);
   public Integer updateAppInfo(AppInfo appInfo);
   public Integer deleteAppInfo(@Param("id") Integer id);
}
