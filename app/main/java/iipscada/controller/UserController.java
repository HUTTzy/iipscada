package iipscada.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import iipscada.config.ScadaConst;
import iipscada.model.ScadaUser;

/**
 * 用户信息控制器
 *
 * @Auther: mao
 * @Date: 18-10-18 14:41
 */
public class UserController extends Controller {
    /**
     * 当前用户信息
     */
    public void index() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(user);
    }

    /**
     * 修改用户信息
     **/
    public void update(@Para("") ScadaUser user) {
        user.keep("nick_name", "email", "mobile");
        ScadaUser usr = getSessionAttr(ScadaConst.USER);
        user.set("id", usr.id());
        boolean isOk = user.update();
        if (isOk) {
            usr = ScadaUser.dao.findById(usr.id()).remove("password");
            setSessionAttr(ScadaConst.USER, usr);
        }
        renderJson(isOk);
    }

    /**
     * 修改用户密码
     **/
    public void updatepwd(@Para("password") String newPwd, @Para("oldPassword") String oldPwd) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        user.set("password", oldPwd);
        user = user.findByNamePwd();
        if (user == null || !user.set("password", newPwd).update()) {
            renderJson(false);
        } else {
            renderJson(true);
        }
    }
}
