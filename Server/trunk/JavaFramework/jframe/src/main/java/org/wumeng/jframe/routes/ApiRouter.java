package org.wumeng.jframe.routes;

import com.jfinal.config.Routes;
import org.wumeng.jframe.control.api.CommonApiController;
import org.wumeng.jframe.control.api.PostsApiController;
import org.wumeng.jframe.control.api.UserApiController;

/**
 * 接口路由
 * Author: User9527
 * Date: 2017/11/24
 */
public class ApiRouter extends Routes {

    @Override
    public void config() {

        // 公共API
        add("/api", CommonApiController.class);
        // 用户API
        add("/api/user", UserApiController.class);
        // 帖子API
        add("/api/posts", PostsApiController.class);

    }

}
