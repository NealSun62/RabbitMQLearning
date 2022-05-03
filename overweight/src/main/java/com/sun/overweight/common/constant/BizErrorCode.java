package com.sun.overweight.common.constant;

/**
 * 错误码
 * 公用错误码9910001-9920000(不包含)
 * 自定义错误码9920001~
 *
 * @author: chenyk25600
 * @date 2020/5/28
 **/
public interface BizErrorCode {
    /**
     * 未知异常
     *
     * @return
     */
    Integer UN_KNONW_ERROR = 9910001;

    /**
     * 外部服务异常
     *
     * @return
     */
    Integer EXTERNAL_SERVICE_EXP = 9910002;

    /**
     * {0}已存在
     *
     * @return
     */
    Integer DATA_EXIST = 9911001;

    /**
     * {0}不存在
     *
     * @return
     */
    Integer DATA_NOT_EXIST = 9911002;

    /**
     * 数据错误
     *
     * @return
     */
    Integer DATA_ERR = 9911003;

    /**
     * {0}错误
     *
     * @return
     */
    Integer PARA_ERR = 9913001;

    /**
     * {0}不能为空
     *
     * @return
     */
    Integer PARA_NIL = 9913002;

    /**
     * 工作流异常
     *
     * @return
     */
    Integer WORKFLOW_ERROR = 9914001;

    /**
     * 获取文件信息失败
     */
    Integer FILE_INFO_ERROR = 9915001;

    /**
     * 文件上传失败
     */
    Integer FILE_UPLOAD_ERROR = 9915002;

    /**
     * 文件下载失败
     */
    Integer FILE_DOWNLOAD_ERROR = 9915003;
}
