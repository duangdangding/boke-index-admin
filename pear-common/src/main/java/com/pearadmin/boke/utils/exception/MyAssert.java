package com.pearadmin.boke.utils.exception;

import java.util.Collection;
import java.util.List;

import com.pearadmin.boke.utils.contains.BaseCtr;

import cn.hutool.core.lang.Assert;

/**
 * 自定义断言业务
 */
public class MyAssert extends Assert {

	/**
	 * 是否登录 ， 并返回userId
	 * @param loginedUserService
	 * @return
	 */
	/*public static String isLogin(LoginedUserService loginedUserService) {
		LoginedUserInfo user = notNull(loginedUserService.getUser(), CtrResultStr.LoginMsg.LOGIN);
		String userId = notNull(user.getUserId(), CtrResultStr.LoginMsg.LOGIN);
		return userId;
	}*/

    /**
     * 大于0
	 * @param value
     * @param message
     * @return
     */
	/*public static Double gtZero(Double value,String message) {
		if (message == null) {
			message = CtrResultStr.JEZERO;
		}
		notNull(value,message);
		if (value <= 0) {
			throw new com.legendshop.youfu.exception.BizException(message);
		}
		return value;
	}*/

	/**
	 * 是否存在此数据
	 * @param data
	 */
	public static <T> T dataExsit(T data) {
		return notNull(data, BaseCtr.NOTEXSIT);
	}

    /**
     * 已存在
     * @param data
     * @return
     */
	public static void unique(List data) {
	    isNull(data,BaseCtr.EXSIT);
    }

    /**
     * 内容不能为空
     * @param s
     */
    public static void notBlank(String s) {
	    notBlank(s,"不能为空~");
    }

    /**
     * 集合为空
     * @param list
     */
    public static void exsitData(Collection list) {
        Assert.notEmpty(list,"没有查询到数据~");
    }

}
