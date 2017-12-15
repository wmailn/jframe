package org.wumeng.jframe.response;


/**
 * 基础请求模型
 * <p>
 * 2017-01-18
 *
 * @author WuMeng
 * @version 1.0
 */
public class DataRespond extends BaseRespond {

    /**
     * 数据
     */
    private Object data;

    public Object getData() {
        return data;
    }

    public DataRespond setData(Object data) {
        this.data = data;
        return this;
    }
}
