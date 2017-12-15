package org.wumeng.jframe.routes;

import com.jfinal.config.Routes;
import org.wumeng.jframe.control.web.IndexWebController;

/**
 * 页面路由
 * Author: User9527
 * Date: 2017/11/24
 */
public class WebRouter extends Routes {

    @Override
    public void config() {
        add("/", IndexWebController.class);
    }

}
