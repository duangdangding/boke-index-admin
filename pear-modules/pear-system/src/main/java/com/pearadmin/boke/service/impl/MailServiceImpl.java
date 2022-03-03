package com.pearadmin.boke.service.impl;

import cn.hutool.core.util.RandomUtil;

import com.pearadmin.boke.service.MailService;
import com.pearadmin.boke.utils.MailUtil;
import com.pearadmin.boke.utils.MailUtils;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailServiceImpl implements MailService {

    Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private MailUtils mailUtils;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MailUtil mailUtil;

    @Override
    public boolean sendMailFreemark(String to,String object,String templete,String key) {
        boolean b = false;
//        生成6为随机数作为验证码
        String s = RandomUtil.randomNumbers(6);
        boolean b1 = mailUtils.sendTemplateMail(to, object, templete,s);
        if (b1) {
            //        设置存放时间5分钟
            boolean set = redisUtil.set(to + key, s, 5 * 60);
            if (set) {
                b = true;
            }
        }
        return b;
    }

    @Override
    public boolean sendMailQuestion(String to, String content,String ip) {
        to = "2538808545@qq.com";
        boolean b = false;
        try {
            JavaMailSenderImpl mailSender = mailUtil.createMailSender();
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUtil.getUsername());
            message.setSubject("问题留言邮件~");
            message.setText(content);
            message.setTo(to);
            mailSender.send(message);
            boolean set = redisUtil.set(ip + Constants.RedisKey.QUES, 60 * 60);
            if (set) {
                b = true;
            }
        } catch (Exception e) {
            log.info(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return b;
    }
}
