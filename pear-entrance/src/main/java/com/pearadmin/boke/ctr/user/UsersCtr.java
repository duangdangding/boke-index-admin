package com.pearadmin.boke.ctr.user;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pearadmin.boke.entry.SongList;
import com.pearadmin.boke.service.SongListService;
import com.pearadmin.boke.utils.ApiUtil;
import com.pearadmin.boke.utils.RedisUtil;
import com.pearadmin.boke.utils.RegExpUtil;
import com.pearadmin.boke.utils.contains.BaseCtr;
import com.pearadmin.boke.utils.contains.Constants;
import com.pearadmin.boke.utils.ip.IPHelper;
import com.pearadmin.boke.vo.ResultDto;
import com.pearadmin.common.tools.SecurityUtil;
import com.pearadmin.system.domain.SysUser;
import com.pearadmin.system.service.ISysUserService;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class UsersCtr extends BaseCtr {
    
    @Autowired
    private ISysUserService sysUserService;
    
    @Autowired
    private SongListService songListService;
    
    @Autowired
    private RedisUtil redisUtil;
    
    @GetMapping("/userinfo/{userId}")
    public ResultDto<SysUser> getUserByid(@PathVariable("userId") Long userId) {
        SysUser byId = sysUserService.getById(userId);
        return success(byId);
    }
    
    @PostMapping("/userRegis")
    public ResultDto<String> userRegis(SysUser users, HttpServletRequest request) {
        String userName = users.getUsername();
        String userEmail = users.getEmail();
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
        SysUser userByUsername = sysUserService.getUserByUsername(userName);
        if (userByUsername != null) {
            return fail(EXISIT);
        }
        SysUser userByEmail = sysUserService.getUserByEmail(userEmail);
        if (userByEmail != null) {
            return fail(EXISIT);
        }
        users.setLogin("1");
        users.setEnable("1");
        users.setStatus("1");
        users.setPassword(new BCryptPasswordEncoder().encode(users.getPassword()));
        
//        注册IP
        String ip = IPHelper.getIp(request);
        users.setRegisterIp(ip);
        Boolean save = sysUserService.save(users);
        // 给普通用户权限
        sysUserService.saveUserRole(users.getUserId(), Arrays.asList("1501789529231589376".split(",")));
        return returnDto(save);
    }


    @RequestMapping("/t/user_info")
    public ModelAndView toUserInfo() {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return getView("login",null);
        }
        Long userId = sysUser.getUserId();
        SysUser byUserId = sysUserService.getById(sysUser.getUserId());
        if (byUserId == null) {
            return getView("login",null);
        } 
        SongList song = songListService.getByUserId(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("user",byUserId);
        map.put("song",song);
        return getView("boke/user_info",map);
    }
    
    @PostMapping("/t/seturl")
    public ResultDto<String> setUrls(String url,String column) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        int i = sysUserService.setUrls(column, url, sysUser.getUserId());
        return returnDto(i);
    }
    
    @PostMapping("/t/setsignature")
    public ResultDto<String> setsignature(String signature) {
        String rep = signature.replaceAll("<p>","").replaceAll("</p>","").replaceAll("<br/>","").replaceAll("&nbsp;","").trim();
        if (rep.length() <= 0) {
            return fail(NULL);
        }
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        int i = sysUserService.setsignature(signature,sysUser.getUserId());
        return returnDto(i);
    }

    /**
     * 修改用户歌单信息
     * @param song
     * @return
     */
    @PostMapping("/t/song")
    public ResultDto<String> setOrUpdateSong(SongList song) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        SongList songList = songListService.getByUserId(userId);
        boolean index;
        song.setUserId(userId);
        if (songList == null) {
            index = songListService.save(song);
        } else {
            songList.setSongNo(song.getSongNo());
            songList.setSongServer(song.getSongServer());
            songList.setSongTheme(song.getSongTheme());
            songList.setAotoPaly(song.getAotoPaly());
            songListService.update();
            index = songListService.updateByUserId(songList) > 0;
        }
        if (index) {
            redisUtil.set(Constants.RedisKey.SONGLIST + userId,JSONUtil.toJsonStr(song));
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
    @PostMapping("/t/song/default")
    public ResultDto<String> setOrUpdateSongDefault(SongList song) {
        SysUser sysUser = SecurityUtil.currentUser();
        if (sysUser == null) {
            return fail(LOGIN);
        }
        Long userId = sysUser.getUserId();
        SongList songList = songListService.getByUserId(userId);
        if (songList.getUserId() == 0) {
            return success(songList);
        }
        QueryWrapper<SongList> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        boolean remove = songListService.remove(wrapper);
        if (remove) {
            redisUtil.del(Constants.RedisKey.SONGLIST + userId);
            songList = songListService.getByUserId(0L);
            return success(songList);
        } else {
            return fail();
        }
    }
    
}
