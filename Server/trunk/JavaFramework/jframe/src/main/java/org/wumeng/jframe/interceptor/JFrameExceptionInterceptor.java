package org.wumeng.jframe.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.apache.log4j.Logger;
import org.wumeng.jframe.control.api.BaseApiController;
import org.wumeng.jframe.response.BaseRespond;
import org.wumeng.jframe.response.ErrorCode;
import org.wumeng.jframe.validator.EmptyParaValidate;

/**
 * 项目的主拦截器
 * Author: User9527
 * Date: 2017/11/29
 */
public class JFrameExceptionInterceptor implements Interceptor {

    protected final Logger mLogger = Logger.getLogger(JFrameExceptionInterceptor.class);

    @Override
    public void intercept(Invocation inv) {
        try {
            EmptyParaValidate emptyParaValidate = inv.getMethod().getAnnotation(EmptyParaValidate.class);
            if (emptyParaValidate == null) {
                inv.invoke();
                return;
            }

            String[] paraKeys = emptyParaValidate.value();
            if (null == paraKeys || paraKeys.length == 0) {
                inv.invoke();
                return;
            }

            for (String param : paraKeys) {
                Object value = ((BaseApiController) inv.getController()).getParam(param);
                if (value == null || value.toString().length() == 0) {
                    inv.getController().renderJson(new BaseRespond().setCode(ErrorCode.ARGUMENT_ERROR).setMsg("[" + param + "] Can not be empty !"));
                    return;
                }
            }
            inv.invoke();

        } catch (Exception e) {
            mLogger.error(e.getMessage(), e);
            inv.getController().renderJson(new BaseRespond().setCode(ErrorCode.ERROR).setMsg(e.toString()));
        }
    }

}
