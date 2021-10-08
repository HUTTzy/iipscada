package iipscada.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.kit.PropKit;
import iipscada.config.ScadaConst;
import iipscada.model.IIPScada;
import iipscada.model.ScadaSharer;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;


/**
 * 头皮发黑
 *
 * @author :mao
 * @version :1.0
 * @date :18-12-29
 * @description :组态对外接口
 */

@Clear
public class APIController extends Controller {

    public static final String API_URL;

    static {
        API_URL = PropKit.get("api.url");
    }

    /**
     * 浏览分享的组态
     */
    public void view(@Para("scadaid") String scadaId, @Para("pwd") String pwd) {
        try {
            String key = getPara(0, "");

            //参数容错判断
            if (StringUtils.isEmpty(key))
                throw new RuntimeException("参数错误");
            ScadaSharer sharer = ScadaSharer.dao.findByScadaShareKey(key);
            Objects.requireNonNull(sharer, "参数错误");

            IIPScada target = IIPScada.dao.findById(sharer.getLong("scadaid"));
            Objects.requireNonNull(target, "参数错误");

            long sharerId = target.getLong("uid");

            if (StringUtils.isEmpty(sharer.getStr("pwd"))) {
                //无密码模式
                doShare(target, "", sharerId);
            } else {
                //有密码
                String scadaPwd = sharer.getStr("pwd");
                if (StringUtils.isNotEmpty(pwd)) {
                    //提交模式
                    if (!Objects.equals(scadaId, target.getInt("id") + ""))
                        throw new RuntimeException("参数错误");
                    if (pwd.equals(scadaPwd))
                        //密码正确
                        scadaPwd = "";
                    else
                        //密码不正确
                        setAttr("msg", "密码错误");
                }
                doShare(target, scadaPwd, sharerId);
            }
        } catch (Exception e) {
            renderText(e.getMessage());
        }

    }

    /**
     * 浏览
     *
     * @param target   目标组态
     * @param pwd      密码
     * @param sharerId 分享人
     */
    private void doShare(IIPScada target, String pwd, long sharerId) {
        if (target == null)
            throw new RuntimeException("参数错误");
        setAttr("scada", target);
        if (StringUtils.isEmpty(pwd)) {
            //无需密码
            //填入分享人信息
            setSessionAttr(ScadaConst.SHARE_ID, sharerId);
            render("Runview.html");
        } else
            //需要密码
            render("sharePwd.html");
    }
}
