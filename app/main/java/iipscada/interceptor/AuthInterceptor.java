package iipscada.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import iipscada.config.AUTH;
import iipscada.config.ScadaConst;
import iipscada.controller.IndexController;
import iipscada.model.ScadaUser;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 权限拦截器
 *
 * @Auther: mao
 * @Date: 18-10-18 15:09
 */
public class AuthInterceptor implements Interceptor {

    public void intercept(Invocation invocation) {
        Controller cn = invocation.getController();
        ScadaUser scadaUser = cn.getSessionAttr(ScadaConst.USER);
        String controllerPath = invocation.getControllerKey();
        String action = invocation.getActionKey();

        //对不起平台管理员就是为所欲为
        if (scadaUser.isSuperAdmin()) {
            invocation.invoke();
            return;
        }

        //index中的不需要拦截
        if (cn instanceof IndexController) {
            invocation.invoke();
            return;
        }

        //角色权限
        Set<String> roleAuth = ScadaConst.ROLE_AUTH.get(scadaUser.loginRid());
        if (roleAuth != null && roleAuth.stream().anyMatch((auth) -> auth.equals(action) || action.matches(auth))) {
            invocation.invoke();
            return;
        }


//        //控制器
//        String[] auths = scadaUser.routerAuth().split(";");
//        for (String auth : auths) {
//            if (Pattern.matches(auth, controllerPath)) {
//                //路由方法
//                String[] methods = scadaUser.auth().split(";");
//                for (String mAuth : methods) {
//                    if (Pattern.matches(auth + mAuth, action)) {
//                        invocation.invoke();
//                        return;
//                    }
//                }
//            }
//        }


        cn.renderText("您没有权限执行-" + action);
    }
}
