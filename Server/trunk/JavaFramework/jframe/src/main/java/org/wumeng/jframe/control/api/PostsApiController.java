package org.wumeng.jframe.control.api;

import com.jfinal.aop.Before;
import com.jfinal.plugin.activerecord.Page;
import org.wumeng.jframe.interceptor.TokenInterceptor;
import org.wumeng.jframe.model.AuthCode;
import org.wumeng.jframe.model.Posts;
import org.wumeng.jframe.model.User;
import org.wumeng.jframe.service.PostsService;
import org.wumeng.jframe.service.TokenService;
import org.wumeng.jframe.service.UserService;
import org.wumeng.jframe.validator.EmptyParaValidate;

/**
 * 帖子API控制类
 * Author: User9527
 * Date: 2017/11/24
 */
@Before(TokenInterceptor.class)
public class PostsApiController extends BaseApiController {

    private static PostsService mPostsService = new PostsService();

    public void index() {
        render("index.html");
    }

    /**
     * 发布帖子
     */
    @EmptyParaValidate({"title", "content"})
    public void publish() {
        // 获取帖子模型
        Posts posts = getJsonRequestModel(Posts.class);
        posts.setPublisher(getUser().getId());
        posts.setPublishTime(System.currentTimeMillis() / 1000);
        // 保存至数据库
        if (posts.save()) {
            renderData(posts);
        } else {
            renderFailedDb();
        }
    }

    /**
     * 帖子列表
     */
    public void list() {
        Page<Posts> posts = mPostsService.paginate(getParaToInt("page_num", 1), getParaToInt("page_size", 100));
        // 保存至数据库
        if (null != posts && null != posts.getList() && 0 < posts.getList().size()) {
            renderList(posts.getTotalRow(), posts.getList());
        } else {
            renderFailedDb();
        }
    }

}
