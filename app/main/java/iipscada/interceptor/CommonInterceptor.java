package iipscada.interceptor;


import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;
import iipscada.config.ScadaConst;

/**
 * 通用拦截器
 *
 * @Auther: mao
 * @Date: 18-12-2 00:25
 */
public class CommonInterceptor implements Interceptor {
    @Override
    public void intercept(Invocation inv) {
        Controller cn = inv.getController();
        cn.setAttr("sys_config", ScadaConst.SYS_CONFIG);
        inv.invoke();
    }
}
