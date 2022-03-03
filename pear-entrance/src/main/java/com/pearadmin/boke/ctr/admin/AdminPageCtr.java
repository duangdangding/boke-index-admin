package com.pearadmin.boke.ctr.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AdminPageCtr {

    @RequestMapping("/admin/index")
    public String adminTopage1() {
        return "index";
    }
    @RequestMapping("/lushao/{page}")
    public String adminTopage(@PathVariable("page") String page) {
        return "boke/admin/" + page;
    }
    @RequestMapping("/lushao/birthday/{page}")
    public String adminBirthdayTopage(@PathVariable("page") String page) {
        return "boke/admin/birthday/" + page;
    }
}