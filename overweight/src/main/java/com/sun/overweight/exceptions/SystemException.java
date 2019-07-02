package com.sun.overweight.exceptions;

/**    
 * 自定义系统异常。主要是抛出系统级的错误。
 *
 * @author louis.lu
 * @date 2018年6月7日
 */
@SuppressWarnings("serial")
public class SystemException extends RuntimeException{

	private int code;
	private String msg;

	public SystemException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public SystemException(int code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
