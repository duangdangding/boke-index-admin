package com.pearadmin.boke.utils;

import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.pearadmin.boke.service.UsersService;
import com.pearadmin.boke.entry.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class UserUtil {

    @Autowired
    private static UsersService usersService;
    
    @Autowired
    private static RedisUtil redisUtil;

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = null;
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            request = attributes.getRequest();
        }
        return request;
    }

    public static Users getToken() {
        HttpServletRequest request = getRequest();
        String token = request.getHeader("token");
        if (StrUtil.isBlank(token)) {
            return null;
        }
        String name;
        String id;
        try {
            id = JWT.decode(token).getAudience().get(0);
            name = JWT.decode(token).getAudience().get(1);
        } catch (JWTDecodeException e) {
            return null;
        }
        // 验证 token
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(id)).build();
        try {
            jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
//            throw new DefinitionException(401, "请重新登录！");
            return null;
        }
        
        Users userByName = usersService.getUserByName(name);
        if (userByName == null) {
            return null;
        }

//                Redis判断token
        if (redisUtil.hasKey(token)) {
            String redisToken = (String) redisUtil.get(token);
            if (!redisToken.equals(token)) {
//                throw new DefinitionException(401, "请重新登录！");
                return null;
            }
        } else {
//            throw new DefinitionException(401, "请重新登录！");
            return null;
        }
        Users users = new Users(Integer.valueOf(id), name);
        return users;
    }
    
    
    public static Integer getUserId() {
        Users token = getToken();
        if (token == null) {
            return null;
        }
        return token.getUserId();
    }

}
