package org.wumeng.jframe.model;

import com.alibaba.fastjson.annotation.JSONField;
import org.wumeng.jframe.model.base.BaseAuthCode;

/**
 * 验证码模型
 * 驼峰风格和下划线风格转换，无需转换的字段可以无需增加，直接根据数据库字段生成
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class AuthCode extends BaseAuthCode<AuthCode> {
    public static final AuthCode dao = new AuthCode().dao();

    /**
     * ID
     */
    @JSONField(name = "auth_code_id")
    private int id;

    /**
     * 验证码
     */
    @JSONField(name = "auth_code")
    private int code;

    /**
     * 有效期
     */
    @JSONField(name = "valid_time")
    private long validTime;
}
