/*
 *
 * LegendShop 多用户商城系统
 *
 *  版权所有,并保留所有权利。
 *
 */
package com.pearadmin.boke.vo;

import java.io.Serializable;

/**
 * 接口返回值
 */
// @ApiModel(value="统一返回接口集")
public class ResultDto<T> implements Serializable{

	private static final long serialVersionUID = -1945491395884152235L;

	/** 返回状态码， 1是正常 */
	// @ApiModelProperty("状态码, 1: 代表成功, 其他值代表失败, -999: 代表未知错误!")
	private Integer status;

	/** 返回说明 */
	// @ApiModelProperty("返回消息说明, OK: 代表成功, fail: 代表失败")
	private String msg;

	/** 版本号 例如 V1.0 */
	// @ApiModelProperty("返回版本号, 默认 1.0")
	private String verId;

	/** 返回结果 */
	// @ApiModelProperty("返回结果集, 具体结果看\"schema属性说明\"")
	private T result;

	public ResultDto(){
	}

	public ResultDto(T result){
		this.result = result;
	}

	public T getResult() {
		return result;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getVerId() {
		return verId;
	}

	public void setVerId(String verId) {
		this.verId = verId;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return 1 == status;
	}

	@Override
	public String toString() {
		return "ResultDto [status=" + status + ", msg=" + msg + ", verId="
				+ verId + ", result=" + result + "]";
	}

}
