package org.wumeng.jframe.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import org.wumeng.jframe.response.BaseRespond;
import org.wumeng.jframe.response.ErrorCode;
import org.wumeng.jframe.service.TokenService;
import org.wumeng.jframe.utlis.StringUtils;

/**
 * Token拦截器
 * Author: User9527
 * Date: 2017/12/08
 */
public class TokenInterceptor implements Interceptor {

    @Override
    public void intercept(Invocation inv) {
        // 验证Token
        String token = inv.getController().getHeader("Token");
        if (StringUtils.isEmpty(token) || !TokenService.getInstance().validate(token)) {
            inv.getController().renderJson(new BaseRespond().setCode(ErrorCode.TOKEN_INVALID).setMsg("Token invalid !"));
            return;
        }

        inv.invoke();
    }
}
