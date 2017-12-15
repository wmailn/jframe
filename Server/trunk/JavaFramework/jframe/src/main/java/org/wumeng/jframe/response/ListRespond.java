package org.wumeng.jframe.response;

import java.util.List;

/**
 * 封装的列表返回的基础模型
 * <p>
 * 2017-02-18
 *
 * @author WuMeng
 * @version 1.0
 */
public class ListRespond {

    /**
     * 总数
     */
    private int total;
    /**
     * 列表
     */
    private List<?> items;

    public int getTotal() {
        return total;
    }

    public ListRespond setTotal(int total) {
        this.total = total;
        return this;
    }

    public List<?> getItems() {
        return items;
    }

    public ListRespond setItems(List<?> items) {
        this.items = items;
        return this;
    }
}
