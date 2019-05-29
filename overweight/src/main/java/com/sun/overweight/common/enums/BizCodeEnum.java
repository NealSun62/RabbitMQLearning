package com.sun.overweight.common.enums;

/**
 * 
 * @author wen
 *
 */
public enum BizCodeEnum {

	COMMON_ERROR_200("200","验证码错误！"),

	COMMON_ERROR_201("201","用户名或密码错误！"),

	COMMON_ERROR_202("202","用户未登陆"),

//	COMMON_ERROR_300("300","令牌验证失败！"),

	COMMON_ERROR_302("302","令牌验证通过！"),

	COMMON_ERROR_600("600","关联匝道与当前任务存在冲突"),

	COMMON_ERROR_601("601","当前任务已和匝道绑定，请先解绑"),

	COMMON_ERROR_700("700","存在锁定的匝道，无法继续操作"),

	COMMON_ERROR_800("800","存在已执行或已取消的命令"),

	;//定义结束符
	private String code;
	private String msg;

	private BizCodeEnum(String code, String msg){
		this.code=code;
		this.msg=msg;
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

	/**
	 * 根据code,获取定义的异常内容
	 *
	 * @param code
	 * @return
	 *
	 * @author neal.sun
	 * @date 2018年6月4日
	 */
	public String getMsg(String code) {
		BizCodeEnum resultEnum = null;
		BizCodeEnum[] enumArray = BizCodeEnum.values();
		for (int i = 0; i < enumArray.length; i++) {
			if (enumArray[i].getCode().equals(code)) {
				resultEnum = enumArray[i];
				break;
			}
		}
		return resultEnum.getMsg();
	}
}
