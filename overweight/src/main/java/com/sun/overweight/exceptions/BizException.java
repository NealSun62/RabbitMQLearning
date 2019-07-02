package com.sun.overweight.exceptions;

/**
 * 自定义异常，主要用于业务异常。一层层的网上抛，一直到controller进行catch
 * 
 * @author louis.lu
 * @date 2018年6月4日
 */
@SuppressWarnings("serial")
public class BizException extends Exception {
	private String code;
	private String msg;

	public BizException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BizException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
