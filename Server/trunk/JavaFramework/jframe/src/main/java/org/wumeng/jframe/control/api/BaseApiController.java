package org.wumeng.jframe.control.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;
import com.jfinal.kit.HttpKit;
import org.wumeng.jframe.model.User;
import org.wumeng.jframe.response.BaseRespond;
import org.wumeng.jframe.response.DataRespond;
import org.wumeng.jframe.response.ErrorCode;
import org.wumeng.jframe.response.ListRespond;
import org.wumeng.jframe.service.TokenService;
import org.wumeng.jframe.utlis.StringUtils;

import java.util.List;

/**
 * 基础API控制类
 * Author: User9527
 * Date: 2017/11/24
 */
public class BaseApiController extends Controller {

    private final static String REQUEST_METHOD_POST = "POST";
    private final static String REQUEST_METHOD_GET = "GET";

    private JSONObject postParamMap = null;
    private String jsonData = null;

    /**
     * POST请求全部封装为JSON，所以自定义获取参数的方法。
     *
     * @param name 参数名称
     * @return
     */
    public Object getParam(String name) {
        if (REQUEST_METHOD_POST.equals(getRequest().getMethod())) {
            if (null == postParamMap || postParamMap.isEmpty()) {
                try {
                    jsonData = HttpKit.readData(getRequest());
                    postParamMap = JSON.parseObject(jsonData);
                } catch (Exception e) {
                    return null;
                }
            }
            Object result = postParamMap.get(name);
            postParamMap.remove(name);
            return result;
        } else {
            return super.getPara(name);
        }
    }

    /**
     * 响应操作成功
     */
    public void renderSuccess() {
        renderJson(new BaseRespond());
    }

    /**
     * 响应操作成功 - 数据
     *
     * @param obj 响应数据
     */
    public void renderData(Object obj) {
        renderJson(new DataRespond().setData(obj));
    }

    /**
     * 响应操作成功 - 列表
     *
     * @param total 总数
     * @param items 列表
     */
    public void renderList(int total, List<?> items) {
        renderData(new ListRespond().setTotal(total).setItems(items));
    }

    /**
     * 响应操作失败
     *
     * @param message 响应信息
     */
    public void renderFailed(String message) {
        renderJson(new BaseRespond().setCode(ErrorCode.FAILURE).setMsg(message));
    }

    /**
     * 响应操作失败 - 数据库操作失败
     */
    public void renderFailedDb() {
        renderJson(new BaseRespond().setCode(ErrorCode.FAILURE).setMsg("Database operation failed !"));
    }

    /**
     * 响应操作失败
     *
     * @param code    响应错误码
     * @param message 响应信息
     */
    public void renderFailed(int code, String message) {
        renderJson(new BaseRespond().setCode(code).setMsg(message));
    }

    /**
     * 响应参数错误
     */
    public void renderArgumentsError() {
        renderJson(new BaseRespond().setCode(ErrorCode.ARGUMENT_ERROR).setMsg("Arguments Error !"));
    }

    /**
     * 获取JSON格式的参数模型
     *
     * @param type 模型
     * @param <T>
     * @return
     */
    public <T> T getJsonRequestModel(Class<T> type) {
        if (StringUtils.isEmpty(jsonData)) {
            jsonData = HttpKit.readData(getRequest());
        }
        return StringUtils.isEmpty(jsonData) ? null : FastJson.getJson().parse(jsonData, type);
    }

    /**
     * 获取Token
     *
     * @return
     */
    protected String getToken() {
        return getHeader("Token");
    }

    /**
     * 获取当前用户对象
     *
     * @return
     */
    protected User getUser() {
        // 验证Token
        String token = getToken();
        if (StringUtils.isNotEmpty(token)) {
            return TokenService.getInstance().getUserByToken(token);
        } else {
            return null;
        }
    }

}
