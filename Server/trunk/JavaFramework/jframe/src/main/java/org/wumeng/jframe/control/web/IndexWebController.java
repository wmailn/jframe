package org.wumeng.jframe.control.web;

import com.jfinal.core.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 主页的页面处理类
 * Author: User9527
 * Date: 2017/11/24
 */
public class IndexWebController extends Controller {

    public void index() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        renderText("This is [JavaFramework] index ! Server current time is： " + df.format(new Date()));
        
    }


}