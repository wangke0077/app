package com.offcn.controller;

import com.offcn.service.newService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Controller
public class newcontroller {
    public static ExecutorService executorService=Executors.newFixedThreadPool(10);
    @Autowired
    private newService newService;
    @ResponseBody
    @RequestMapping("/getall")
    public DeferredResult<String>  getAll() throws ExecutionException, InterruptedException {
        DeferredResult<String> deferredResult=new DeferredResult<>(20000L," 请求失败");
        System.out.println(Thread.currentThread()+"        Tomcat主线程开始"+System.currentTimeMillis());
       /*Callable<String> callable= new Callable<String>() {
            public String call() throws Exception {
                System.out.println(Thread.currentThread()+"    异步开始");
                newService.getAll();
                System.out.println("异步结束");
                return "ok";
                Callable<String>
            }
        };*/
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread()+"    异步开始");
                try {
                    newService.getAll(deferredResult);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("异步结束");
            }
        });

        System.out.println("Tomcat主线程结束"+System.currentTimeMillis());
        return deferredResult;
    }
}
