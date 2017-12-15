package org.wumeng.jframe.utlis;

/**
 * 字符串处理类
 * Author: User9527
 * Date: 2017/11/24
 */
public final class StringUtils {

    /**
     * 判断字符串是否为空 Determines if a string is empty (<code>null</code> or
     * zero-length).
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        return ((string == null) || (string.length() == 0));
    }

    /**
     * 判断字符串是否不为空 Determines if a string is not empty.
     *
     * @param string
     * @return
     */
    public static boolean isNotEmpty(String string) {
        return string != null && string != "null" && string.length() > 0;
    }

    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        return str.matches("\\d*");
    }
}
