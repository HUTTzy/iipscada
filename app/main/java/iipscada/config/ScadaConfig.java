package iipscada.config;

import com.jfinal.config.*;
import com.jfinal.core.JFinal;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.plugin.ehcache.EhCachePlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import iipscada.controller.*;
import iipscada.interceptor.AuthInterceptor;
import iipscada.interceptor.CommonInterceptor;
import iipscada.interceptor.LoginInterceptor;
import iipscada.model.*;
import iipscada.util.SecondJobUtil;

/**
 * @Auther: mao
 * @Date: 18-10-18 09:34
 */
public class ScadaConfig extends JFinalConfig {

    public void configConstant(Constants constants) {
        PropKit.use("iipscada.properties");
        constants.setDevMode(PropKit.getBoolean("devMode"));
        constants.setViewType(ViewType.JFINAL_TEMPLATE);
        constants.setEncoding("UTF-8");
    }

    public void configRoute(Routes routes) {
        routes.add("/", IndexController.class, "/WEB-INF/view/");
        routes.add("/user", UserController.class, "/WEB-INF/view/");
        routes.add("/admin", AdminController.class, "/WEB-INF/view/");
        routes.add("/server", ScadaController.class, "/WEB-INF/view/scadaedit/");
        routes.add("/data", DataController.class, "/WEB-INF/view/scadadata/");
        routes.add("/tag", TagController.class, "/WEB-INF/view/scadadata/");
        routes.add("/btn", BtnController.class);
        routes.add("/config", ConfigController.class);
        routes.add("/util", UtilController.class, "/WEB-INF/view/utilshow");
        routes.add("/share", ShareController.class);
        routes.add("/api", APIController.class, "/WEB-INF/view/scadaedit/");
    }

    public void configEngine(Engine engine) {
        //engine.addSharedObject("baseurl", JFinal.me().getContextPath());
    }

    public void configPlugin(Plugins plugins) {
        DruidPlugin dp = new DruidPlugin(PropKit.get("jdbc.url"), PropKit.get("jdbc.username"), PropKit.get("jdbc.password"));
        plugins.add(dp);

        ActiveRecordPlugin arp = new ActiveRecordPlugin(dp);
        arp.addMapping("v_sys_user2", ScadaUser.class);
        arp.addMapping("me_scada", IIPScada.class);
        arp.addMapping("me_tags", ScadaTag.class);
        arp.addMapping("sys_role", ScadaRole.class);
        arp.addMapping("me_datasource", ScadaDataScource.class);
        arp.addMapping("me_dataset", ScadaDataSet.class);
        arp.addMapping("sys_menu", ScadaMenu.class);
        arp.addMapping("sys_role_btn", ScadaBtn.class);
        arp.addMapping("sys_config", ScadaConfigM.class);
        arp.addMapping("me_share", ScadaSharer.class);
        plugins.add(arp);

        plugins.add(new EhCachePlugin());
    }

    public void configInterceptor(Interceptors interceptors) {
        //登录拦截器
        interceptors.add(new LoginInterceptor());
        interceptors.add(new AuthInterceptor());
        interceptors.add(new CommonInterceptor());
    }

    public void configHandler(Handlers handlers) {
        //handlers.add(new ContextPathHandler("baseUrl"));
        handlers.add(new ContextPathHandler("ctxPath"));
    }

    @Override
    public void afterJFinalStart() {
        ScadaInit.init();
        //SecondJobUtil.start();
    }

    public static void main(String args[]) {
        JFinal.start("src/main/webapp", 8082, "/", 5);
    }
}
