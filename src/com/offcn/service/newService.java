package com.offcn.service;

import com.alibaba.fastjson.JSONObject;
import com.offcn.pojo.AppInfo;
import com.offcn.pojo.AppVersion;
import com.offcn.pojo.DevUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class newService {

    @Autowired
    private DevUserService devUserService;
    @Autowired
    private AppVersionService appVersionService;
    public static ExecutorService executorService=Executors.newFixedThreadPool(10);
    public void getAll(DeferredResult<String> result) throws ExecutionException, InterruptedException {

        Map<String,Object> map=new HashMap<>();
        System.out.println(System.currentTimeMillis());
        Callable<DevUser> user1=new Callable<DevUser>() {
            @Override
            public DevUser call() throws Exception {

                DevUser test001 = devUserService.getUserByName("test001", "123456");
                return  test001;
            }
        };
        Callable<AppVersion> version1=new Callable<AppVersion>() {
            @Override
            public AppVersion call() throws Exception {
                AppVersion appVersionByVid = appVersionService.getAppVersionByVid(36);
                return appVersionByVid;
            }
        };
        FutureTask<DevUser> futureTask=new FutureTask<DevUser>(user1);
        FutureTask<AppVersion> futureTask1=new FutureTask<AppVersion>(version1);
        executorService.submit(futureTask);
        executorService.submit(futureTask1);
        map.put("user",futureTask.get());
        map.put("version",futureTask1.get());
        System.out.println(System.currentTimeMillis());
        result.setResult("ok");
    }
}
