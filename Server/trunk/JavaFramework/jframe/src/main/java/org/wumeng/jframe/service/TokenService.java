package org.wumeng.jframe.service;

import com.jfinal.kit.HashKit;
import org.wumeng.jframe.model.User;
import org.wumeng.jframe.utlis.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Token相关Service
 * Author: User9527
 * Date: 2017/12/08
 */
public class TokenService {

    private static UserService mUserService = new UserService();

    private Map<String, User> mTokenMaps;

    private static class TokenServiceHolder {
        private static final TokenService INSTANCE = new TokenService();
    }

    public static final TokenService getInstance() {
        return TokenServiceHolder.INSTANCE;
    }

    private TokenService() {
        mTokenMaps = new ConcurrentHashMap<>();
    }

    /**
     * 获取用户
     *
     * @param token
     * @return
     */
    public User getUserByToken(String token) {
        User user = null;
        if (StringUtils.isNotEmpty(token)) {
            user = mTokenMaps.get(token);
            if (null == user || 0 == user.getId()) {
                user = mUserService.selectUserByToken(token);
                // 缓存
                if (null != user && 0 != user.getId()) {
                    mTokenMaps.put(token, user);
                }
            }
        }
        return user;
    }

    /**
     * 删除Token
     *
     * @param token
     */
    public boolean removeToken(String token) {
        boolean isSuccessful = false;
        User user = getUserByToken(token);
        if (null != user && 0 != user.getId()) {
            // 更新数据库中的Token
            isSuccessful = mUserService.updateUserToken(user, "");
            // 从缓存中移除
            mTokenMaps.remove(token);
        }
        return isSuccessful;
    }

    /**
     * 生成Token
     *
     * @param user
     * @return
     */
    public String createToken(User user) {
        String token = HashKit.md5(user.getId() + "|" + UUID.randomUUID().toString());
        mTokenMaps.put(token, user);
        return token;
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public boolean validate(String token) {
        return StringUtils.isNotEmpty(token) && null != getUserByToken(token) && 0 != getUserByToken(token).getId();
    }

}
