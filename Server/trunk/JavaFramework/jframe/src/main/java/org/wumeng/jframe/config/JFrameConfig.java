package org.wumeng.jframe.config;

import com.jfinal.config.*;
import com.jfinal.json.FastJsonFactory;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.template.Engine;
import org.wumeng.jframe.interceptor.JFrameExceptionInterceptor;
import org.wumeng.jframe.model._MappingKit;
import org.wumeng.jframe.routes.ApiRouter;
import org.wumeng.jframe.routes.WebRouter;

/**
 * 项目主配置
 * Author: User9527
 * Date: 2017/11/28
 */
public class JFrameConfig extends JFinalConfig {

    @Override
    public void configConstant(Constants me) {
        // 加载少量必要配置，随后可用PropKit.get(...)获取值
        PropKit.use("jframe_config.txt");
        me.setDevMode(PropKit.getBoolean("devMode", false));
        // 指定使用FastJson
        me.setJsonFactory(new FastJsonFactory());
    }

    @Override
    public void configRoute(Routes me) {
        // 接口路由
        me.add(new ApiRouter());
        // 页面路由
        me.add(new WebRouter());
    }

    @Override
    public void configEngine(Engine me) {

    }

    @Override
    public void configPlugin(Plugins me) {
        // 配置C3p0数据库连接池插件
        DruidPlugin druidPlugin = createDruidPlugin();
        me.add(druidPlugin);

        // 配置ActiveRecord插件
        ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
        // 所有映射在 MappingKit 中自动化搞定
        _MappingKit.mapping(arp);
        // 打印SQL语句
        arp.setShowSql(true);
        me.add(arp);
    }

    @Override
    public void configInterceptor(Interceptors me) {
        me.add(new JFrameExceptionInterceptor());
    }

    @Override
    public void configHandler(Handlers me) {

    }

    /**
     * 数据库连接
     *
     * @return
     */
    public static DruidPlugin createDruidPlugin() {
        return new DruidPlugin(PropKit.get("jdbcUrl"), PropKit.get("user"), PropKit.get("password").trim());
    }
}
