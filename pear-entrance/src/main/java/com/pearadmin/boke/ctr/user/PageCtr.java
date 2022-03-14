package com.pearadmin.boke.ctr.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageCtr {

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

    // sec:authorize="hasPermission('/admin/boke/md','sys:boke:md')"
    // <ul class=\"ed_type\"><li><a sec:authorize="hasPermission('/admin/boke/wd','sys:boke:wd')" onclick=\"return toeditor();\" href=\"/t/wd\">富文本编辑器</a></li><li><a sec:authorize="hasPermission('/admin/boke/md','sys:boke:md')" onclick=\"return toeditor();\" href=\"/t/md\">markdown编辑器</a></li></ul>
    //<ul class="ed_type"><li><a sec:authorize="hasPt/to/toermission('/admin/boke/wd','sys:boke:wd')" onclick="return toeditor();" href="/t/wd">富文本编辑器</a></li><li><a sec:authorize="hasPermission('/admin/boke/md','sys:boke:md')" onclick="return toeditor();" href="/t/md">markdown编辑器</a></li></ul>
    @RequestMapping({"/boke/md"})
    // <ul class="ed_type"><li><a sec:authorize="hasPermission('/admin/boke/wd','sys:boke:wd')" href="/t/wd">富文本编辑器</a></li>
    // <li><a sec:authorize="hasPermission('/admin/boke/md','sys:boke:md')" href="/t/md">markdown编辑器</a></li></ul>
    @PreAuthorize("hasPermission('/admin/boke/md','sys:boke:md')")
    public String toMDPage() {
        return "boke/md";
    }
    @RequestMapping({"/boke/wd"})
    // sec:authorize="hasPermission('/admin/boke/wd','sys:boke:wd')"
    @PreAuthorize("hasPermission('/admin/boke/wd','sys:boke:wd')")
    // <ul class="ed_type"><li><a href="/boke/wd">富文本编辑器</a></li><li><a href="/boke/md">markdown编辑器</a></li></ul>
    public String toWDPage() {
        return "boke/wd";
    }

}
