package iipscada.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import iipscada.config.ScadaConst;
import iipscada.model.ScadaDataScource;
import iipscada.model.ScadaDataSet;
import iipscada.model.ScadaTag;
import iipscada.model.ScadaUser;
import iipscada.paginate.PaginateModel;
import iipscada.util.MaoDBUtil;

import java.util.List;

/**
 * 标签控制器
 *
 * @Auther: mao
 * @Date: 18-10-29 19:54
 */
public class TagController extends Controller {
    /**
     * tag编辑界面
     */
    public void tagview() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        setAttr("datascources", ScadaDataScource.dao.findAllByUid(user.id()));
        setAttr("datasets", ScadaDataSet.dao.findAllByUid(user.id()));
        render("taglist.html");
    }

    /**
     * 获取tag列表
     */
    public void taglist(@Para("limit") int limit, @Para("page") int page, @Para("") ScadaTag scadaTag) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        scadaTag.set("uid", user.id());
        renderJson(scadaTag.doPaginate(page, limit).setTableName("v_me_tags").buildDataGrid());
    }

    /**
     * 删除tag
     */
    public void delete(@Para("ids") String ids) {
        renderJson(MaoDBUtil.deleteByIds("me_tags", ids));
    }

    /**
     * 添加/修改标签
     */
    public void toggleTag(@Para("") ScadaTag scadaTag) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        scadaTag.set("uid", user.id());
        if (scadaTag.get("tagname") == null) {
            return;
        }
        if (scadaTag.id() == null || scadaTag.id() == 0)
            renderJson(scadaTag.save());
        else
            renderJson(scadaTag.update());
    }
}
