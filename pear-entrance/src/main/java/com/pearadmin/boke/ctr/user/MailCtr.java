package com.pearadmin.boke.ctr.user;

import javax.servlet.http.HttpServletRequest;

import com.pearadmin.boke.service.MailService;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.utils.PasswordUtil;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.RegExpUtil;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.vo.ResultDtoManager;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailCtr extends BaseCtr {
    
    @Autowired
    private MailService mailService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private UsersService usersService;

    /**
     * 发送验证码
     * @return
     */
    @UserLoginToken
    @PostMapping("/t/mail/bd")
    public ResultDto<String> sendMail(int index, String to) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        Users byUserId = usersService.getByUserId(TokenUtil.USERID);
        if (byUserId == null) {
            return fail(RELOGIN);
        }
        String key = "";
        String object = "";
        if (index == 2) {
            if (byUserId.getEmailBind() == 2) {
                return ResultDtoManager.fail(-1,BINDMAIL);
            }
            key = Constants.RedisKey.BD;
            to = byUserId.getUserEmail();
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
    @UserLoginToken
    @PostMapping("/t/mail/sbd")
    public ResultDto<String> bangdingMail(String valitionCode,int index,String to) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        Users byUserId = usersService.getByUserId(TokenUtil.USERID);
        if (byUserId == null) {
            return fail(RELOGIN);
        }
        String key = "";
        if (index == 2) {
            if (byUserId.getEmailBind() == 2) {
                return fail(BINDMAIL);
            }
            key = Constants.RedisKey.BD;
            to = byUserId.getUserEmail();
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
            int i = usersService.unOrbdMail(TokenUtil.USERID, index);
            return returnDto(i);
        } else if (index == 3) {
            int i = usersService.ghMail(to, TokenUtil.USERID);
            return returnDto(i);
        } else {
            return fail(ERRYZM);
        }
    }

    /**
     * 更换密码
     * @param pwd
     * @param valitionCode
     * @return
     */
    @UserLoginToken
    @PostMapping("/t/set/pwd")
    public ResultDto<String> setPwd(String pwd,String valitionCode) {
        if (TokenUtil.USERID == null) {
            return fail(RELOGIN);
        }

        Users byUserId = usersService.getByUserId(TokenUtil.USERID);
        if (byUserId == null) {
            return fail(RELOGIN);
        }
        String to = byUserId.getUserEmail();
        String key = Constants.RedisKey.MM;
        if (!redisUtil.hasKey(to + key)) {
            return fail(SXYZM);
        }
        if (!redisUtil.get(to + key).equals(valitionCode)) {
            return fail(ERRYZM);
        }
        if (!RegExpUtil.checkPwd(pwd)) {
            return fail(PWDERR);
        }
        String s = PasswordUtil.SHAPwd(pwd);
        int i = usersService.updatePWD(s, TokenUtil.USERID);
        return returnDto(i);
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
