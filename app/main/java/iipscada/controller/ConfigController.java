package iipscada.controller;

import com.alibaba.druid.pool.vendor.SybaseExceptionSorter;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import iipscada.model.ScadaConfigM;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 配置控制器
 *
 * @Auther: mao
 * @Date: 18-11-21 09:09
 */
public class ConfigController extends Controller {

    // 请求配置消息
    public void doQuery() {
        renderJson(ScadaConfigM.dao.findAll());
    }

    //更新配置
    public void doUpdate(@Para("config") String config) {
        renderJson(ScadaConfigM.dao.listUpdate(JSONArray.parseArray(config)));
    }
}
