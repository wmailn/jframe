package org.wumeng.jframe.control.api;

import com.jfinal.aop.Before;
import com.jfinal.kit.HashKit;
import org.wumeng.jframe.interceptor.TokenInterceptor;
import org.wumeng.jframe.model.AuthCode;
import org.wumeng.jframe.model.User;
import org.wumeng.jframe.service.TokenService;
import org.wumeng.jframe.service.UserService;
import org.wumeng.jframe.validator.EmptyParaValidate;

/**
 * 用户API控制类
 * Author: User9527
 * Date: 2017/11/24
 */
public class UserApiController extends BaseApiController {

    private static UserService mUserService = new UserService();

    public void index() {
        render("index.html");
    }

    /**
     * 获取验证码
     */
    @EmptyParaValidate("phone")
    public void auth_code() {
        AuthCode authCode = getJsonRequestModel(AuthCode.class);
        // 生成6位数随机验证码
        int code = (int) ((Math.random() * 9 + 1) * 100000);
        // 有效期限当前时候后5分钟
        long validTime = System.currentTimeMillis() + 5 * 60 * 1000;
        // 创建模型
        authCode.setCode(code).setValidTime(validTime / 1000);
        // 插入数据库
        if (authCode.save()) {
            renderData(authCode);
        } else {
            renderFailedDb();
        }
    }

    /**
     * 注册
     */
    @EmptyParaValidate({"auth_code_id", "auth_code", "user_name", "phone", "pass_word"})
    public void register() {

        AuthCode code = getJsonRequestModel(AuthCode.class);
        User user = getJsonRequestModel(User.class);

        // 数据库中的验证码
        AuthCode dCode = AuthCode.dao.findById(code.getId());

        if (null == dCode) {
            renderFailed("The verification code does not exist !");
        } else if (dCode.getId() != code.getId() || !dCode.getCode().equals(code.getCode())) {
            renderFailed("The verification code error !");
        } else if (!dCode.getPhone().equals(code.getPhone())) {
            renderFailed("The verification wrong number of the phone number !");
        } else if (dCode.getValidTime() < (System.currentTimeMillis() / 1000)) {
            renderFailed("The verification code has expired !");
        } else {
            // 数据库中的用户
            User dUser = mUserService.selectUser(user.getUserName());
            if (null != dUser && 0 != dUser.getId()) {
                if (dUser.getUserName().equals(user.getUserName())) {
                    renderFailed("User name already exists !");
                }
                // TODO 手机 重复
            } else {
                // 用户增加注册时间
                user.setRegtime(System.currentTimeMillis() / 1000);
                // 对密码进行MD5加密
                user.setPassWord(HashKit.md5(user.getPassWord()));
                // 插入数据库
                if (user.save()) {
                    // 响应用户并生成Token
                    responseUserAndCreateToken(user);
                } else {
                    renderFailedDb();
                }
            }
        }
    }

    /**
     * 登录
     */
    @EmptyParaValidate({"user_name", "pass_word"})
    public void login() {
        User user = getJsonRequestModel(User.class);
        // 数据库中的用户
        User dbUser = mUserService.selectUser(user.getUserName(), HashKit.md5(user.getPassWord()));
        if (null != dbUser && 0 != dbUser.getId()) {
            // 响应用户并生成Token
            responseUserAndCreateToken(dbUser);
        } else {
            renderFailed("User name or password error!");
        }
    }

    /**
     * 响应用户并生成Token
     *
     * @param user
     */
    private void responseUserAndCreateToken(User user) {
        // 用户存在则生成Token插入数据库并返回
        String token = TokenService.getInstance().createToken(user);
        // 更新数据库中的Token
        if (mUserService.updateUserToken(user, token)) {
            // 把令牌添加到响应头
            getResponse().addHeader("Authorization", token);
            // 清空密码
            user.setPassWord("");
            renderData(user);
        } else {
            renderFailed("Update Token error!");
        }
    }

    /**
     * 登出
     */
    @Before(TokenInterceptor.class)
    public void logout() {
        // 删除Token
        if (TokenService.getInstance().removeToken(getToken())) {
            renderSuccess();
        } else {
            renderFailed("Logout failed !");
        }
    }
}
