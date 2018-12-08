package com.offcn.service;

import com.offcn.dao.AppInfoMapper;
import com.offcn.dao.AppVersionMapper;
import com.offcn.pojo.AppInfo;
import com.offcn.pojo.AppVersion;
import com.offcn.pojo.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AppInfoServiceImpl implements AppInfoService {
    @Autowired
    private AppInfoMapper appInfoMapper;
    @Autowired
    private AppVersionMapper appVersionMapper;
    @Override
    public Page getAppInfo(AppInfo info, String pageSize, String nowPage) {
        Page page=new Page();
        Integer pageSize1=5;
        if (pageSize!=null&&!"".equals(pageSize)){
            pageSize1= Integer.parseInt(pageSize);
        }
        page.setPageSize(pageSize1);
        Integer count = appInfoMapper.getCount(info);
        page.setTotalCount(count);
        Integer totlePage=count%pageSize1 ==0?count/pageSize1:count/pageSize1+1;
        page.setTotalPageCount(totlePage);
        Integer nowpage=1;
        if (nowPage!=null&&!"".equals(nowPage)){
            if (Integer.parseInt(nowPage)<1){
                nowpage=1;
            }else if (Integer.parseInt(nowPage)>totlePage){
                nowpage=totlePage;
            }else {
                nowpage=Integer.parseInt(nowPage);
            }
        }
        page.setCurrentPageNo(nowpage);
        List<AppInfo> appInfo = appInfoMapper.getAppInfo(info, pageSize1, (nowpage - 1) * pageSize1);
        page.setInfoList(appInfo);
        return page;
    }

    @Override
    public AppInfo getById(Integer id,String APKName) {
        return appInfoMapper.getById(id,APKName);
    }

    @Override
    public Integer addInfo(AppInfo appInfo) {
        Integer integer = appInfoMapper.addInfo(appInfo);
        return integer;
    }

    @Override
    public Integer updateLogopath(AppInfo appInfo) {
        Integer integer = appInfoMapper.updateLogopath(appInfo);
        return integer;
    }

    @Override
    public Integer updateAppInfo(AppInfo appInfo) {
        Integer integer = appInfoMapper.updateAppInfo(appInfo);
        return integer;
    }

    @Override
    @Transactional
    public Map<String, Object> delApp(String id) throws Exception {
        boolean flag1=false;
        boolean flag2=false;
        Map<String,Object> map=new HashMap<>();
        if(id==null&&"".equals(id)){
            map.put("delResult","false");
        }else {
            List<AppVersion> byAppId =appVersionMapper.getByAppId(Integer.parseInt(id));
            if (byAppId!=null&&byAppId.size()>0){
                for (AppVersion appVersion:byAppId){

                    String locPath = appVersion.getApkLocPath();
                    File file=new File(locPath);
                    if (!file.exists()) {
                        throw new  Exception();
                    }else {
                        flag1=true;
                    }
                    if (flag1){
                        if(file.delete()){
                            Integer integer = appVersionMapper.delAppVersion(appVersion.getId());
                            if (integer>0){
                                flag2=true;
                            }else {
                                flag2=false;
                            }
                        }
                    }else {
                        throw new  Exception();
                    }
                }
            }

            if (flag2){
                AppInfo byId = appInfoMapper.getById(Integer.parseInt(id), null);
                if (byId!=null){
                    String logoLocPath = byId.getLogoLocPath();
                    File file=new File(logoLocPath);
                    if (file.exists()){
                        boolean delete = file.delete();
                        if (delete){
                            Integer integer = appInfoMapper.deleteAppInfo(Integer.parseInt(id));
                            if (integer>0){
                                map.put("delResult","true");
                            }else {
                                map.put("delResult","false");
                            }
                        }
                    }else {
                        map.put("delResult","notexist");
                    }
                }

            }

        }
        return map;



    }
}
