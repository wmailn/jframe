package org.wumeng.jframe.service;

import org.wumeng.jframe.model.User;

/**
 * 用户相关Service
 * Author: User9527
 * Date: 2017/11/29
 */
public class UserService {

    public User selectUser(String userName) {
        return User.dao.findFirst("select * from user where user_name = ?", userName);
    }

    public User selectUser(String userName, String passWord) {
        return User.dao.findFirst("select * from user where user_name = ? and pass_word = ? ", userName, passWord);
    }

    public User selectUserByToken(String token) {
        return User.dao.findFirst("select * from user where token = ?", token);
    }

    public boolean updateUserToken(User user, String token) {
        return user.setToken(token).update();
    }

}
