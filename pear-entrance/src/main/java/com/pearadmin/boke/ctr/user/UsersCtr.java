package com.pearadmin.boke.ctr.user;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.service.SongListService;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.entry.LoginInfo;
import com.pearadmin.boke.entry.SongList;
import com.pearadmin.boke.entry.UserToken;
import com.pearadmin.boke.entry.Users;
import com.pearadmin.boke.utils.*;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.utils.contains.PassToken;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.boke.utils.contains.UserLoginToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class UsersCtr extends BaseCtr {
    
    @Autowired
    private UsersService usersService;
    
    @Autowired
    private SongListService songListService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/userinfo/{userId}")
    public ResultDto<Users> getUserByid(@PathVariable("userId") String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (Exception e) {
            id = 0;
        }
        Users byId = usersService.getByUserId(id);
        return success(byId);
    }
    
    @PostMapping("/userRegis")
    public ResultDto<String> userRegis(Users users, HttpServletRequest request) {
        String userName = users.getUserName();
        String userEmail = users.getUserEmail();
        String password = users.getPassword();
        if (!RegExpUtil.checkName(userName)) {
            return fail(NAMEERR);
        }
        if (!RegExpUtil.checkEmail(userEmail)) {
            return fail(MAILERR);
        }
//        1、单 IP 最大访问频率是每 10 秒一次；
//        2、单 IP 24小时内最大访问次数 1000 次；
        String url = "https://api.nbhao.org/v1/email/verify";
        String post = ApiUtil.doPost(url, "email=" + userEmail);
//        {"message": {"email": "2538808545@qq.com", "result": "ok"}, "code": 200, "result": "true"}
        if ("false".equals(JSONUtil.parseObj(post).get("result"))) {
            return fail(MAILNO);
        }
        if (!RegExpUtil.checkPwd(password)) {
            return fail(PWDERR);
        }
        Users one = usersService.getUserByName(users.getUserName());
        if (one != null) {
            return fail(EXISIT);
        }
        Users byEmail = usersService.getUserByEmail(users.getUserEmail());
        if (byEmail != null) {
            return fail(EXISIT);
        }
//        注册IP
        String ip = IPHelper.getIp(request);
        users.setRegisterIp(ip);
       // 加密密码
        users.setPassword(PasswordUtil.SHAPwd(password));
        boolean save = usersService.save(users);
        return returnDto(save);
    }

    /**
     * 用户登录
     * @param users
     * @return
     */
    @PostMapping("/userLogin")
    public ResultDto<UserToken> toLogin(Users users, HttpServletRequest request) {
        Users userByNEP = usersService.getUserByNEP(users);
        if (userByNEP == null) {
            return fail(LOGINERR);
        }
        if (userByNEP.getUserState() == 0) {
            return fail(NOTUSER);
        }
        String token = TokenUtil.createToken(userByNEP);
        if (redisUtil.hasKey(token)) {
            redisUtil.del(token);
        }
//        TokenUtil.TOKEN = token;
        redisUtil.set(token,token);
        String ip = IPHelper.getIp(request);
        userByNEP.setLoginIp(ip);
        userByNEP.setLoginTime(new Timestamp(System.currentTimeMillis()));
        boolean b = usersService.updateById(userByNEP);
//        session.setAttribute("user",userByNEP);
        TokenUtil.USERID = userByNEP.getUserId();
        LoginInfo info = new LoginInfo();
        BeanUtil.copyProperties(userByNEP,info);
        return success(new UserToken(info,token));
    }

    /**
     * 修改密码
     * @param password
     * @return
     */
    @PostMapping("/setPwd")
    public ResultDto<String> updatePwd(String password) {
        return null;
    }

    /**
     * 重置密码
     * @param email
     * @return
     */
    @PostMapping("/reasetPwd")
    public ResultDto<String> reasetPwd(String email) {
        return null;
    }

    /**
     * 为了把token放到ThreadLocal中
     * @return
     */
    @UserLoginToken
    @PostMapping("/t/to/to")
    public ResultDto<String> setToken() {
        return success();
    }
    
    @PassToken
    @RequestMapping("/t/user_info")
    public ModelAndView toUserInfo() {
        // log.info("toUserInfo ~~~ TokenUtil.USERID = " + TokenUtil.USERID);
        Integer userId = TokenUtil.USERID;
        if (userId == null) {
            return getView("login",null);
        }
        Users byUserId = usersService.getByUserId(userId);
        if (byUserId == null) {
            return getView("login",null);
        } 
        SongList song = songListService.getByUserId(userId);
        if (song == null) {
            song = songListService.getByUserId(0);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("user",byUserId);
        map.put("song",song);
        return getView("user_info",map);
    }
    
    @UserLoginToken
    @PostMapping("/t/seturl")
    public ResultDto<String> setUrls(String url,String column) {
        Integer userId = TokenUtil.USERID;
        if (userId == null) {
            return fail(LOGIN);
        }
        int i = usersService.setUrls(column, url, userId);
        return returnDto(i);
    }
    
    @UserLoginToken
    @PostMapping("/t/setsignature")
    public ResultDto<String> setsignature(String signature) {
        String rep = signature.replaceAll("<p>","").replaceAll("</p>","").replaceAll("<br/>","").replaceAll("&nbsp;","").trim();
        if (rep.length() <= 0) {
            return fail(NULL);
        }
        Integer userId = TokenUtil.USERID;
        if (userId == null) {
            return fail(LOGIN);
        }
        int i = usersService.setsignature(signature,userId);
        return returnDto(i);
    }

    /**
     * 修改用户歌单信息
     * @param song
     * @return
     */
    @UserLoginToken
    @PostMapping("/t/song")
    public ResultDto<String> setOrUpdateSong(SongList song) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        SongList songList = songListService.getByUserId(TokenUtil.USERID);
        boolean index;
        if (songList == null) {
            song.setUserId(TokenUtil.USERID);
            index = songListService.save(song);
        } else {
            songList.setSongNo(song.getSongNo());
            songList.setSongServer(song.getSongServer());
            songList.setSongTheme(song.getSongTheme());
            songList.setAotoPaly(song.getAotoPaly());
            songList.setUserId(TokenUtil.USERID);
            songListService.update();
            index = songListService.updateByUserId(songList) > 0;
        }
        if (index) {
            redisUtil.set(Constants.RedisKey.SONGLIST + TokenUtil.USERID,JSONUtil.toJsonStr(song));
            return success(song);
        } else {
            return fail();
        }
    }

    /**
     * 删除或者
     * @param song
     * @return
     */
    @UserLoginToken
    @PostMapping("/t/song/default")
    public ResultDto<String> setOrUpdateSongDefault(SongList song) {
        if (TokenUtil.USERID == null) {
            return fail(LOGIN);
        }
        SongList songList = songListService.getByUserId(TokenUtil.USERID);
        if (songList == null) {
            songList = songListService.getByUserId(0);
            return success(songList);
        }
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",TokenUtil.USERID);
        boolean remove = songListService.remove(wrapper);
        if (remove) {
            songList = songListService.getByUserId(0);
            return success(songList);
        } else {
            return fail();
        }
    }
    
}
