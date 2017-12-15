package org.wumeng.jframe.response;


/**
 * 基础请求模型
 * <p>
 * 2017-01-18
 *
 * @author WuMeng
 * @version 1.0
 */
public class BaseRespond {

    /**
     * 请求结果码
     */
    private int code = ErrorCode.SUCCESS;
    /**
     * 提示消息
     */
    private String msg = "Successful";

    public int getCode() {
        return code;
    }

    public BaseRespond setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseRespond setMsg(String msg) {
        this.msg = msg;
        return this;
    }

}
