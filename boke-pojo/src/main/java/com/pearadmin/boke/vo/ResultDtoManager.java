package com.pearadmin.boke.vo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 构造ResultDto，返回客户端
 */
public class ResultDtoManager {
	
    private static String SUCCESS_MSG = "OK";
	
	private static int SUCCESS_STATUS = 1;
	
	private static int UNKNOWN_ERROR = -999;

	private static String VERSION_ID = "2.2";
	
	/** The log. */
	private static final Logger log = LoggerFactory.getLogger(ResultDtoManager.class);

	/**
	 * 
	 * 返回成功
	 * 
	 * **/
	public static <T> ResultDto<T> success(T result){
		ResultDto<T> dto =  new ResultDto<T>(result);

		// 去掉app.properties文件，不读取配置，改成常量配置
		/*dto.setVerId(EnvironmentConfig.getInstance().getPropertyValue(AppConfigPropertiesEnum.VerId));*/
		dto.setVerId(VERSION_ID);
		dto.setMsg(SUCCESS_MSG);
		dto.setStatus(SUCCESS_STATUS);
		log.info("--> response = {}", dto);
		return dto;
	}
	
	/**
	 * 返回成功
	 * 
	 */
	public static <T> ResultDto<T> success(){
		return ResultDtoManager.success(null);
	}

	
	/**
	 * 
	 * 返回失败
	 * 
	 * **/
	public static <T> ResultDto<T> fail(T result, Integer status, String msg){
		ResultDto<T> dto =  new ResultDto<T>(result);
		dto.setVerId(VERSION_ID);
		if(null == status){
			status = UNKNOWN_ERROR;
		}
		if(msg == null || msg.length() == 0){
			msg = "unknown error";
		}
		dto.setMsg(msg);
		dto.setStatus(status);
		log.info("--> response = {}", dto);
		return dto;
	}
	
	/**
	 * 
	 * 返回未知错误
	 * 
	 * **/
	public static <T> ResultDto<T> fail(){
		return ResultDtoManager.fail(null, null, null);
	}
	
	/**
	 * 
	 * 返回未知错误
	 * 
	 * **/
	public static <T> ResultDto<T> fail(Integer status, String msg){
		return ResultDtoManager.fail(null, status, msg);
	}
	
	/**
	 * 返回未知错误
	 */
	public static <T> ResultDto<T> fail(String msg){
		return ResultDtoManager.fail(null, null, msg);
	}
	
}
