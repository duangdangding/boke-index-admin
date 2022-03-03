package com.pearadmin.boke.utils.exception;

/**
 * 错误描述
 */
public interface BaseErrorInfoInterface {

	/** 错误码*/
	String getResultCode();

	/** 错误描述*/
	String getResultMsg();
}
