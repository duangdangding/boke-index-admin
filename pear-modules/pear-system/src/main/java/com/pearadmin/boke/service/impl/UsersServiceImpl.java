package com.pearadmin.boke.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lsh.mapper.boke.UsersMapper;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.utils.MailUtils;
import com.pearadmin.boke.utils.PasswordUtil;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.vo.query.QueryUserVo;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsersServiceImpl extends ServiceImpl<UsersMapper, Users> implements UsersService {

    @Resource
    private UsersMapper usersMapper;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private MailUtils mailUtils;

    @Override
    public Users getUserByName(String username) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_name",username);
        return usersMapper.selectOne(wrapper);
    }

    @Override
    public Users getUserByEmail(String email) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("user_email",email);
        return usersMapper.selectOne(wrapper);
    }

    @Override
    public Users getUserByNEP(Users users) {
        users.setPassword(PasswordUtil.SHAPwd(users.getPassword()));
        return usersMapper.getUserByNEP(users);
    }

    @Override
    public Users getByUserId(int userId) {
        Users user;
        String key = Constants.RedisKey.USERINFO + userId;
        if (redisUtil.hasKey(key)) {
            user = JSONUtil.toBean(redisUtil.get(key).toString(), Users.class);
        } else {
            user = setUserInf0(userId);
        }
        return user;
    }

    @Override
    public int setFace(Users users) {
        int i = usersMapper.setFace(users);
        if (i > 0) {
            setUserInf0(users.getUserId());
        }
        return i;
    }

    @Override
    public int setUrls(String column, String url, Integer userId) {
        if (!StrUtil.isBlank(column)) {
            String c ;
            switch (column) {
                case "wxUrl":
                    c = "wx_url";
                    break;
                case "qqUrl":
                    c = "qq_url";
                    break;
                case "githubUrl":
                    c = "github_url";
                    break;
                case "csdnUrl":
                    c = "csdn_url";
                    break;
                case "giteeUrl":
                    c = "gitee_url";
                    break;
                case "biliUrl":
                    c = "bili_url";
                    break;
                case "weiboUrl":
                    c = "weibo_url";
                    break;
                default:
                    return 0;
            }
            int i = usersMapper.setUrls(c, url, userId);
            if (i > 0) {
                setUserInf0(userId);
            }
            return i;
        }
        return 0;
    }

    @Override
    public int setsignature(String signature, Integer userId) {
        int i = usersMapper.setsignature(signature, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    private Users setUserInf0(Integer userId) {
        Users users1 = usersMapper.getByUserId(userId).get(0);
        redisUtil.set(Constants.RedisKey.USERINFO + userId,JSONUtil.toJsonStr(users1));
        return users1;
    }

    @Override
    public int unOrbdMail(Integer userId, int bd) {
        int i = usersMapper.unOrbdMail(bd, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int unOrbdPhone(Integer userId, int bd) {
        int i = usersMapper.unOrbdPhone(bd, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int ghMail(String email, Integer userId) {
        int i = usersMapper.ghMail(email, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int ghPhone(String phone, Integer userId) {
        int i = usersMapper.ghPhone(phone, userId);
        if (i > 0) {
            setUserInf0(userId);
        }
        return i;
    }

    @Override
    public int updatePWD(String pwd, Integer userId) {
        return usersMapper.updatePWD(pwd,userId);
    }

    @Override
    public Long getByUserIdCount(Integer userId) {
        QueryWrapper<Users> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId).eq("user_state",1);
        return usersMapper.selectCount(wrapper);
    }

    @Override
    public IPage<Users> getPage(QueryUserVo vo) {
        Page page = new Page(vo.getPage(),vo.getLimit());
        return usersMapper.getPage(page,vo);
    }

    @Override
    public int setDeleteState(String ids,Integer deleteState) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<Users> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("user_id",idList);
            Users users = new Users();
            users.setUserState(deleteState);
            return usersMapper.update(users,queryWrapper);
        }
        return 0;
    }

    @Override
    public int resetPwd(String ids) {
        if (StrUtil.isNotEmpty(ids)) {
            String[] split = ids.split(",");
            QueryWrapper<Users> queryWrapper = new QueryWrapper();
            // string数组转list<Integer>
            List<Integer> idList = Arrays.stream(split)
                    .map(s->Integer.parseInt(s.trim()))
                    .collect(Collectors.toList());
            queryWrapper.in("user_id",idList);
            Users users = new Users();
            String s = PasswordUtil.SHAPwd("123456");
            users.setPassword(s);
            int i = usersMapper.update(users, queryWrapper);
            if (i > 0) {
                for (Integer userId : idList) {
                    Users user = usersMapper.getByUserId(userId).get(0);
                    String email = user.getUserEmail();
                    try {
                        if (StrUtil.isNotEmpty(email)) {
                            String fromEmail = mailUtils.getFromEmail();
                            if (!fromEmail.equals(email)) {
                                log.info(fromEmail + "开始给" + email + "发送邮件~");
                                String context = "您的密码已被重置为：123456";
                                mailUtils.sendSimpleMail(email,"重置密码",context);
                            }
                        }
                    } catch (Exception e) {
                        log.error("发送邮件失败：" + e.getMessage());
                    }
                }
                
            }
            return usersMapper.update(users,queryWrapper);
        }
        return 0;
    }

}
