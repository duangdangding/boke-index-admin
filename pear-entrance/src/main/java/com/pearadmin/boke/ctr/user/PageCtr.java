package com.pearadmin.boke.ctr.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageCtr {

    @RequestMapping("/youlian")
    public String youlian() {
        return "boke/youlian";
    }
    @RequestMapping("/login")
    public String login() {
        return "boke/login";
    }
    @RequestMapping("/frantPage")
    public String frantPage() {
        return "boke/frantPage";
    }
    @RequestMapping("/register")
    public String register() {
        return "boke/register";
    }
    @RequestMapping("/user_info")
    public String user_info() {
        return "redirect:/t/user_info";
    }
    
    @RequestMapping("/u/x/c")
    public String u_xiangce() {
        // return "u_xiangce";
        return "qny";
    }

}
