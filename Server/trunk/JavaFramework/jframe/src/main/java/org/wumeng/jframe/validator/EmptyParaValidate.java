package org.wumeng.jframe.validator;

import java.lang.annotation.*;

/**
 * 参数为空的校验
 * Author: User9527
 * Date: 2017/11/30
 */
@Documented
@Target(ElementType.METHOD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)
public @interface EmptyParaValidate {

    String[] value();


}
