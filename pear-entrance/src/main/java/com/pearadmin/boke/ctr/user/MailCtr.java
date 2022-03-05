package com.pearadmin.boke.ctr.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.service.MailService;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.RegExpUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.service.ISysUserService;

@RestController
public class MailCtr extends BaseCtr {
    
    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysUserService usersService;

    /**
     * 发送绑定邮箱验证码
     * @return
     */
    @PostMapping("/t/mail/bd")
    public ResultDto<String> sendMail(int index, String to) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        /*SysUser byUserId = usersService.getById(sysUser.getUserId());
        if (byUserId == null) {
            return fail(RELOGIN);
        }*/
        String key = "";
        String object = "";
        if (index == 2) {
            if (sysUser.getEmailBind() == 2) {
                return ResultDtoManager.fail(-1,BINDMAIL);
            }
            key = Constants.RedisKey.BD;
            to = sysUser.getEmail();
            object = "绑定邮箱的验证码";
        } /*else if (index == 2) {
            if (byUserId.getEmailBind() == 2) {
                return ResultDtoManager.fail(-1,BINDMAIL);
            }
            key = Constants.BD;
        }*/ else if (index == 3) {
            if (!RegExpUtil.checkEmail(to)) {
                return fail(MAILERR);
            }
            key = Constants.RedisKey.GH;
            object = "更换邮箱的验证码";
        } else if (index == 4) {
            key = Constants.RedisKey.MM;
            object = "更换密码的验证码";
        } else {
            return fail(ERRPARAM);
        }
        
        if (redisUtil.hasKey(to + key)) {
            return fail(PFYZM);
        }
        boolean b = mailService.sendMailFreemark(to, object, Constants.Template.MAILTMPE,key);
        return returnDto(b);
    }

    /**
     * 绑定和解绑
     * @param valitionCode
     * @param index
     * @return
     */
    @PostMapping("/t/mail/sbd")
    public ResultDto<String> bangdingMail(String valitionCode,int index,String to) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        String key = "";
        if (index == 2) {
            if (sysUser.getEmailBind() == 2) {
                return fail(BINDMAIL);
            }
            key = Constants.RedisKey.BD;
            to = sysUser.getEmail();
        } /*else if (index == 2) {
            if (byUserId.getEmailBind() == 2) {
                return ResultDtoManager.fail(-1,BINDMAIL);
            }
            key = Constants.BD;
        }*/else if (index == 3) {
            if (!RegExpUtil.checkEmail(to)) {
                return fail(MAILERR);
            }
            key = Constants.RedisKey.BD;
        } else {
            return fail(ERRPARAM);
        }
        
        if (!redisUtil.hasKey(to + key)) {
            return fail(SXYZM);
        }
        if (!redisUtil.get(to + key).equals(valitionCode)) {
            return fail(ERRYZM);
        }
        if (index == 2) {
            int i = usersService.unOrbdMail(userId, index);
            return returnDto(i);
        } else if (index == 3) {
            int i = usersService.ghMail(to,userId);
            return returnDto(i);
        } else {
            return fail(ERRYZM);
        }
    }
    
    @RequestMapping("/mail/qus")
    public ResultDto<String> sendMailQuestion(String content, String to, HttpServletRequest request) {
        String ip = IPHelper.getIp(request);
        if (redisUtil.hasKey(ip + Constants.RedisKey.QUES)) {
            return fail(QUESMAIL);
        }
        boolean b = mailService.sendMailQuestion(to, content,ip);
        return returnDto(b);
    }
}
