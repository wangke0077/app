package com.offcn.controller;

import com.offcn.pojo.DevUser;
import com.offcn.service.DevUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class DevUserController {

    @Autowired
    private DevUserService devUserService;

    @RequestMapping("/dologin")
    public String dologin(String devCode, String devPassword, HttpSession session){
        DevUser userByName = devUserService.getUserByName(devCode, devPassword);
        if (userByName!=null){
            session.setAttribute("userSession",userByName);
            return "redirect:/checkLogin";
        }else {
            return "redirect:/devlogin";
        }
    }
    @RequestMapping("/checkLogin")
    public String checkLogin(HttpSession session){
        if (session.getAttribute("userSession")!=null){
            return "developer/main";
        }else {
            return "rediect:/devlogin";
        }
    }
}
