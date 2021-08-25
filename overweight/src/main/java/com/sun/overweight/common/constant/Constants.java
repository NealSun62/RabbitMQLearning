package com.sun.overweight.common.constant;

import java.math.BigDecimal;

/**
 * 常量信息
 *
 * @author chenyk25600
 * @date
 */
public interface Constants {
    /**
     * 默认集合大小
     */
    Integer DEFAULT_SIZE = 16;

    /**
     * 默认
     */
    String DEFAULT_ROOT_PID = "root";

    /**
     * 默认版本
     */
    String DEFALT_SOFT_VERSION = "1";

    /**
     * 默认空值
     */
    String EMPTY = " ";
    String NULL = "null";
    BigDecimal ZERO_BIGDECIMAL = new BigDecimal(0);

    /**
     * 操作
     */
    Integer OPERATE = 0;
    /**
     * 新增
     */
    Integer INSERT = 1;
    /**
     * 修改
     */
    Integer UPDATE = 2;
    /**
     * 删除
     */
    Integer DELETE = 3;
    /**
     * 升级
     */
    Integer UPGRADE = 4;
    /**
     * 启用
     */
    Integer START = 5;
    /**
     * 停用
     */
    Integer STOP = 6;
    /**
     * 保存
     */
    Integer SAVE = 7;
    /**
     * 设置
     */
    Integer SET = 8;
    /**
     * 上传
     */
    Integer UPLOAD = 9;
    /**
     * 下载
     */
    Integer DOWNLOAD = 10;

    /**
     * 默认时间
     */
    Integer DEFAULT_DATE = 19700101;
    Integer DEFAULT_END_DATE = 99999999;

    /**
     * 分隔符
     */
    String DELIMITER = "|";
    /**
     * 顿号
     */
    String DAWN = "、";
    /**
     * 逗号
     */
    String COMMA = ",";
    /**
     * 分号
     */
    String SEMICCOLON = ";";

    /**
     * datasource
     */
    String MYSQL = "mysql";
    String ORACLE = "oracle";

    /**
     * 否
     */
    String NO = "0";
    /**
     * 是
     */
    String YES = "1";

    /**
     * 数据字典默认客户号
     */
    String DEFAULT_CUSTOMNUM = "99999";

    /**
     * 数据来源,1:资讯基本信息;2:资讯行情;3:业务数据;
     */
    String INFOBASE = "1";
    String INFOMKT = "2";
    String INFODATA = "3";

    /**
     * 最大行业级别
     */
    Integer MAX_INDUSTRY_LEVEL = 4;
    /**
     * 一级行业级别
     */
    String FISRT_INDT_LVL_CODE = "1";

    /**
     * 上市公司 list_flag字段
     */
    String LISTED_COMPANY_FLAG = "02";

    /**
     * 财报期次：一季度报
     */
    String FIRST_FIN_REPT_PERIOD = "0331";

    /**
     * 财报期次：中报
     */
    String SECOND_FIN_REPT_PERIOD = "0630";

    /**
     * 财报期次：三季度报
     */
    String THIRD_FIN_REPT_PERIOD = "0930";

    /**
     * 财报期次：年报
     */
    String FORTH_FIN_REPT_PERIOD = "1231";

    /**
     * 债券数据源类型：2-手工
     */
    String BOND_SOURCE_HANDLER = "2";

    /**
     * 默认分页数目
     */
    Integer DEFALUT_PAGE_SIZE = 1000;

    /**
     * 债券类型代码：次级债
     */
    String DEBT_REPY_TYPE_SUB = "02";

    /**
     * 债券类型代码：二级资本债
     */
    String DEBT_REPY_TYPE_SEC_CAPL = "04";

    /**
     * 机构类别代码：工商企业
     */
    String INS_CLAS_INDU_AND_COML_ENT = "3";

    /**
     * 机构类别代码：商业银行
     */
    String INS_CLAS_COML_BANK = "40";

    /**
     * 省份层级代码
     */
    String REGI_PROVINCE_HIRC_CODE = "1";
    /**
     * 市级层级代码
     */
    String REGI_CITY_HIRC_CODE = "2";
    /**
     * 地区层级代码
     */
    String REGI_HIRC_CODE = "3";

    /**
     * 资讯库：聚源数据库
     */
    String PUBINFO_DB_FORM_JY = "1";

    /**
     * 资讯库：万德数据库
     */
    String PUBINFO_DB_FORM_WIND = "2";
    /**
     * 中债估值：收益率曲线类型代码-到期
     */
    String YLD_CURV_TYPE_EXPIRE = "01";
    /**
     * 亿元
     */
    Integer DIVIDE_TO_MILLION = 100000000;
    /**
     * 百元
     */
    Integer DIVIDE_TO_HUNDRED = 100;

    /**
     * 成功
     */
    String SUCCESS_CODE = "0";
    /**
     * 失败
     */
    String ERROR_CODE = "0";

    /**
     * admin
     */
    String ADMIN = "admin";

    /**
     * 组织根
     */
    String ORG_ROOT="0_000000";
    public static final String AUTHORIZATION = "Authorization";
    /**接口响应状态*/
    /** 成功 */
    public static final String STATUSCODE_SUCCEED = "32";
    /** 超时 */
    public static final String STATUSCODE_SESSIONTIMEOUT = "64";
    /** 失败 */
    public static final String STATUSCODE_FAILED = "128";
    /** 无操作 */
    public static final String STATUSCODE_NOTFOUND = "256";

    /** 删除标识*/
    /** 未删除 */
    public static final String DELETED_NO = "0";
    /** 已删除 */
    public static final String DELETED_YES = "1";

    /**锁定标识**/
    /**未锁定**/
    public static final String LOCKED_NO = "0";
    /**管理员锁定**/
    public static final String LOCKED_MANAGER = "1";
    /**密码输入错误次数过多**/
    public static final String LOCKED_ERROMAX = "2";

    /**新建用户时初始的密码和盐**/
    /**密码**/
    public static final String INITIAL_PASSWORD = "111111";
    public static final String INITIAL_SALT = "56f94a2284293111";

    /** 分页相关 */
    /** 默认页数 */
    public static final int DEFAULT_PAGE_INDEX = 0;
    /** 默认每页显示条数 */
    public static final int DEFAULT_PAGE_SIZE = 20;
    /** 默认每页显示条数 */
    public static final int DEFAULT_EXPORT_SIZE = 1000;

    /**返回消息*/
    public static final String MESSAGE_SUCCEED = "成功";
    public static final String MESSAGE_PARAMETER_ILLEGALITY = "参数不合法";
    public static final String MESSAGE_FAILED = "失败";
    public static final String TOKEN_NAME = "token";

    public static final String OPERATION_TYPE = "opt";

    /** redis中存的用户数据的前缀 使用字符串+userId拼接作为KEY值  */
    public static final String AUTHDATARESPONSEDTO = "AUTHDATARESPONSEDTO";
    public static final String ROLE = "ROLE";
    public static final String AREA = "AREA";
    public static final String ORGUNIT = "ORGUNIT";
    public static final String AUTH = "AUTH";
    public static final String RQIANALYSISREDISDTO = "RQIANALYSISREDISDTO";
    public static final String TYPE = "TYPE";
    public static final String SIZE = "SIZE";
    public static final String NUM = "NUM";
    /** redis 中userId的缓存数据的有效时间 30分钟*/
    public static final long SAVE_REDIS_DATA_TIMEOUT = 30*60;

    /** redis 中userId的缓存数据的有效时间 30分钟*/
    public static final int SAVE_DATA_DAYS_PCI_RQI = 1;
}
