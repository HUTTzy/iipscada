package iipscada.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import iipscada.config.ScadaConst;
import iipscada.interceptor.AuthInterceptor;
import iipscada.interceptor.LoginInterceptor;
import iipscada.model.*;

import java.util.List;

/**
 * @Auther: mao
 * @Date: 18-10-18 11:06
 */
public class IndexController extends Controller {
    public void index() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        List<ScadaMenu> scadaMenuList = ScadaMenu.dao.findByIds(user.menu());
        setAttr("menuList", scadaMenuList);
        render("index.html");
    }

    public void console() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        setAttr("datasourecount", ScadaDataScource.dao.findAllByUid(user.id()).size());
        setAttr("datasetcount", ScadaDataSet.dao.findAllByUid(user.id()).size());
        setAttr("tagcount", ScadaTag.dao.findAllByUid(user.id()).size());
        render("console.html");
    }

    /**
     * 登录
     **/
    @Clear({LoginInterceptor.class, AuthInterceptor.class})
    public void login() {
        render("login.html");
    }

    @Clear({LoginInterceptor.class, AuthInterceptor.class})
    public void tryLogin(@Para("") ScadaUser user) {
        if (user.loginName() == null || user.password() == null) {
            login();
        } else {
            user = user.findByNamePwd();
            if (user == null) {
                setAttr("msg", "账号或密码错误!");
                login();
            } else if (user.stat() == 0) {
                setAttr("msg", "该账号已经被锁定，请联系管理员!");
                login();
            } else {
                user.remove("password");
                setSessionAttr(ScadaConst.USER, user);
                redirect("/");
            }
        }

    }

    /**
     * 到编辑器
     **/
    public void edit() {
        renderText("编辑器");
    }

    @Clear()
    public void exit() {
        removeSessionAttr(ScadaConst.USER);
        redirect("/");
    }
}
