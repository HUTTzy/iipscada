package iipscada.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import iipscada.config.ScadaConst;
import iipscada.model.ScadaUser;

/**
 * 登录拦截器
 *
 * @Auther: mao
 * @Date: 18-10-18 11:10
 */
public class LoginInterceptor implements Interceptor {
    public void intercept(Invocation invocation) {
        Controller actionController = invocation.getController();
        actionController.setAttr("baseUrl", actionController.getViewPath());
        ScadaUser user = actionController.getSessionAttr(ScadaConst.USER);
        if (user == null) {
            //未登录
            actionController.redirect("/login");
        } else {
            actionController.setAttr("user", user);
            invocation.invoke();
        }

    }
}
