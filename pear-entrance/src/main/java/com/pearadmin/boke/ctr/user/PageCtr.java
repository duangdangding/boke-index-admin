package com.pearadmin.boke.ctr.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.PassToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageCtr {

    @RequestMapping("/youlian")
    public String youlian() {
        return "boke/youlian";
    }
    @PassToken
    @RequestMapping("/t/wd")
    public String new_boke2() {
        log.info("new_boke2 /t/wd" + TokenUtil.USERID);
        if (TokenUtil.USERID == null) {
            return "boke/login";
        }
        return "boke/wd";
    }
    @PassToken
    @RequestMapping("/t/md")
    public String new_boke3() {
        if (TokenUtil.USERID == null) {
            return "boke/login";
        }
        return "boke/md";
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
    @RequestMapping("/logout")
    public String logout() {
        TokenUtil.clean();
        /*if (TokenUtil.TOKEN != null) {
            if (redisUtil.hasKey(TokenUtil.TOKEN)) {
                redisUtil.del(TokenUtil.TOKEN);
            }
        }*/
        TokenUtil.USERID = null;
        return "redirect:/";
    }

}
