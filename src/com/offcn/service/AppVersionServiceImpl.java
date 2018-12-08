package com.offcn.service;

import com.offcn.dao.AppVersionMapper;
import com.offcn.pojo.AppVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppVersionServiceImpl implements AppVersionService{
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Override
    public List<AppVersion> getByAppId(Integer appId) {
        List<AppVersion> byAppId = appVersionMapper.getByAppId(appId);
        return byAppId;
    }
    @Override
    public Integer addAppVersion(AppVersion appVersion) {
        Integer integer = appVersionMapper.addAppVersion(appVersion);
        return integer;
    }

    @Override
    public AppVersion getAppVersionByVid(Integer id) {
        AppVersion appVersionByVid = appVersionMapper.getAppVersionByVid(id);
        return appVersionByVid;
    }

    @Override
    public Integer updateAppVersionLocalByVid(AppVersion appVersion) {
        Integer integer = appVersionMapper.updateAppVersionLocalByVid(appVersion);
        return integer;
    }

    @Override
    public Integer delAppVersion(Integer id) {
        Integer integer = appVersionMapper.delAppVersion(id);
        return integer;
    }
}
