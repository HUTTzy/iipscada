package iipscada.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import iipscada.model.ScadaBtn;

import java.util.List;

/**
 * 权限按钮
 *
 * @Auther: mao
 * @Date: 18-11-17 16:32
 */
public class BtnController extends Controller {

    /**
     * 获取角色权限/以及所有权限
     */
    public void rolebBtnList(@Para("rid") Long rid) {
        List<ScadaBtn> rBtnList = ScadaBtn.dao.findByRid(rid);
        List<ScadaBtn> btnList = ScadaBtn.dao.findAllBtn();
        setAttr("rBtnList", rBtnList);
        setAttr("btnList", btnList);
        renderJson();
    }

    /**
     * 权限设置
     */
    public void authset(@Para("msg") String data, @Para("rid") long rid) {
        JSONObject jsonObject = JSONObject.parseObject(data);
        renderJson(ScadaBtn.dao.authSet(jsonObject, rid));
    }

    /**
     * 菜单权限
     */
    public void menuAuth(@Para("menuid") long rid) {
        renderJson(ScadaBtn.dao.findMenuAuth(rid));
    }

    public void setAuth(@Para("menuid") long menu, @Para("auth") String auth) {
        JSONArray jsonArray = JSONArray.parseArray(auth);
        renderJson(ScadaBtn.dao.setAuth(menu, jsonArray));
    }
}
