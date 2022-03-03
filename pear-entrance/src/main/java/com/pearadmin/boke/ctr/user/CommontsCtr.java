package com.pearadmin.boke.ctr.user;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pearadmin.boke.entry.Bokes;
import com.pearadmin.boke.entry.Comments;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.service.BokesService;
import com.pearadmin.boke.service.CommentsService;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.utils.DateUtil;
import com.pearadmin.boke.utils.MailUtils;
import com.pearadmin.boke.utils.MyStringUtil;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.TokenUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.vo.ResultDto;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class CommontsCtr extends BaseCtr {

    @Autowired
    private CommentsService commentsService;

    @Autowired
    private BokesService bokesService;

    @Autowired
    private UsersService usersService;

    @Autowired
    private MailUtils mailUtils;

    @RequestMapping("/boke/comm/{bokeId}")
    public List<Comments> getByBokeId(@PathVariable("bokeId") Integer bokeId) {
        return commentsService.getCommentsByBokeId(bokeId);
    }

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 评论
     * @param comments
     * @return
     */
    @PostMapping("/comment/to")
    public ResultDto toComment(Comments comments, HttpServletRequest request) {
        String html = comments.getCommTont();
        String rep = html.replaceAll("<p>","").replaceAll("</p>","").replaceAll("<br/>","").replaceAll("&nbsp;","").trim();
        if (rep.length() <= 0) {
            return fail(CONTNULL);
        }
        // 判断有没有违规字符
        rep = MyStringUtil.replaceEmptyStr(rep);
        Set<Object> objects = redisUtil.sGet(Constants.RedisKey.VIOLATION);
        if (MyStringUtil.isValinWord(objects,rep)) {
            return fail(VIOLATIONSTR);
        }
        Bokes boke = bokesService.getById(comments.getBokeId());
        if (boke == null) {
            return fail(NOBOKE);
        }
        Integer userId = TokenUtil.USERID != null ? TokenUtil.USERID : comments.getUserId();
        if (userId != null) {
            Long count1 = usersService.getByUserIdCount(userId);
            if (count1 == 0) {
                userId = null;
            }
        }
        String ip = IPHelper.getIp(request);
        if (userId == null) {
            String key = Constants.RedisKey.PL + ip;
            if (redisUtil.hasKey(key)) {
                int count = (int) redisUtil.get(key);
                if (count >= 10) {
                    return fail(PLXZ);
                }
                redisUtil.incr(key,1);
            } else {
                redisUtil.set(key,1, DateUtil.getSeconds());
            }
        } else {
            comments.setUserId(userId);
        }
        comments.setUserIp(ip);
        comments.setCommTime(Constants.DateFormat.DATETIME.format(new Date()));
        int save = commentsService.saveComment(comments);
        if (save > 0) {
            bokesService.setCommentNum(boke.getBokeId());
            // 发送邮件
            Users byUserId = usersService.getByUserId(boke.getUserId());
            // 如果此文章没有用户
            if (byUserId == null) {
                return fail(BOKEILL);
            }
            try {
                String userEmail = byUserId.getUserEmail();
                if (StrUtil.isNotEmpty(userEmail)) {
                    // JavaMailSenderImpl mailSender = mailUtil.createMailSender();
                    // String fromMail = mailSender.getUsername();
                    String fromEmail = mailUtils.getFromEmail();
                    if (!fromEmail.equals(userEmail)) {
                        log.info(fromEmail + "开始给" + userEmail + "发送邮件~");
                        String context = "有人对您的<a href='http://suweibk.xyz:11521/boke/"+ boke.getBokeId() +"' target='_blank'>" + boke.getTitle() + "</a>进行了评论~<br>请不要回复此邮件！！！";
                        mailUtils.sendHtmlMail(userEmail,"提示邮件",context);
                        /*SimpleMailMessage message = new SimpleMailMessage();
                        message.setFrom(mailUtil.getUsername());
                        message.setSubject("问题留言邮件~");
                        message.setText("有人对您的<a href='http://suweibk.xyz:11521/boke/"+ boke.getBokeId() +"' target='_blank'>" + boke.getTitle() + "</a>进行了评论~<br>请不要回复此邮件！！！");
                        message.setTo(userEmail);
                        mailSender.send(message);*/
                    }
                }
            } catch (Exception e) {
                log.error("发送邮件失败：" + e.getMessage());
            }
            return success(comments);
        } else {
            return fail(FAILD);
        }
    }

}
