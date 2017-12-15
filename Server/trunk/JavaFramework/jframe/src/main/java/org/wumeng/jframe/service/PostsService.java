package org.wumeng.jframe.service;

import com.jfinal.plugin.activerecord.Page;
import org.wumeng.jframe.model.Posts;

/**
 * 用户相关Service
 * Author: User9527
 * Date: 2017/11/29
 */
public class PostsService {

    public Page<Posts> paginate(int pageNumber, int pageSize) {
        return Posts.dao.paginate(pageNumber, pageSize, "select *", "from posts order by id asc");
    }

}
