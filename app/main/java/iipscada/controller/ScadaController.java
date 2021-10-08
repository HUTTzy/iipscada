package iipscada.controller;

import com.jfinal.aop.Clear;
import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import iipscada.config.ScadaConst;
import iipscada.model.IIPScada;
import iipscada.model.ScadaTag;
import iipscada.model.ScadaUser;

import java.util.*;

/**
 * @Auther: mao
 * @Date: 18-10-18 15:04
 */
public class ScadaController extends Controller {
    /**
     * 编辑态
     */
    public void index(@Para("filename") String fileName) {
        setAttr("filename", fileName);
        render("Editor.html");
    }

    /**
     * 删除scada
     */
    public void deletescada(@Para("filename") String filename) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(IIPScada.dao.deleteByFilenameAndUid(filename, user.id()));
    }

    /**
     * 拷贝scada
     */
    public void copyscada(@Para("filename") String filename) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        IIPScada scada = IIPScada.dao.findByFileNameAndUid(filename, user.id()).remove("id");
        if (null != IIPScada.dao.findByFileNameAndUid("_" + scada.get("filename"), user.id())) {
            renderJson(false);
            return;
        }
        scada.set("filename", "_" + scada.get("filename"));
        renderJson(scada.save());
    }

    /**
     * 修改文件名
     */
    public void updatefilename(@Para("filename") String filename, @Para("newfilename") String newfilename) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(IIPScada.dao.updateFileName(user.id(), newfilename, filename));
    }

    /**
     * scada列表界面
     */
    public void scadalist() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        setAttr("scadalist", IIPScada.dao.getjsonFiles(user));
        render("list.html");
    }

    /**
     * scada数据
     */
    public void doQuery(@Para("limit") int limit, @Para("page") int page, @Para("") IIPScada params) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        params.set("uid", user.id());
        renderJson(params.doPaginate(page, limit).buildDataGrid());
    }

    /**
     * scada数据
     */
    public void query() {
        render("listgrid.html");
    }

    /**
     * 获取已保存的组态模板列表
     */
    public void getjsonFiles() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(IIPScada.dao.getjsonFiles(user));
    }

    public void getFileList() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(IIPScada.dao.getFileList(user));
    }

    /**
     * 获取一个模板的详细信息
     */
    @Clear
    public void load(@Para("filename") String fileName) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        IIPScada scada = IIPScada.dao.findByFileNameAndUid(fileName, user.id());
        if (scada != null) {
            renderJson(scada);
        } else {
            renderText("not found");
        }
    }

    /**
     * 检查标签是否合法
     */
    public void checkTags() {

    }

    /**
     * 保存模板(id=0时添加)
     */
    public void save(@Para("") IIPScada model) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        model.set("uid", user.id());
        if (model.getInt("id") == 0) {
            IIPScada.dao.deleteByFilenameAndUid(model.getStr("filename"), user.id());
            if (!model.save(user)) {
                renderText("error");
                return;
            }
        } else {
            model.update();
        }
        renderJson("{\"message\":\"文件名称:" + model.get("filename") + "成功保存,您可以使用'运行'进行测试!\",\"id\":" + model.get("id") + "}");
    }

    /**
     * 判断模板名称是否存在
     */
    public void jsonfileExist(@Para("filename") String fileName) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        boolean isExist = IIPScada.dao.findByFileNameAndUid(fileName, user.id()) != null;
        renderJson("[" + (isExist ? isExist : "") + "]");
    }

    /**
     * 运行态
     */
    @Clear
    public void runview(@Para("filename") String fileName) {
        Long shareId = getSessionAttr(ScadaConst.SHARE_ID);
        IIPScada scada = null;
        if (Objects.isNull(shareId)) {
            //非共享
            ScadaUser user = getSessionAttr(ScadaConst.USER);
            if (Objects.isNull(user))
                //未登录
                redirect("/");
            else {
                shareId = user.id();
                //用户查看了自己的则去掉分享标志
                removeSessionAttr(ScadaConst.SHARE_ID);
            }
        }

        scada = IIPScada.dao.findByFileNameAndUid(fileName, shareId);
        if (scada == null) {
            renderHtml("错误!找不到模板: [" + fileName + "]");
        } else {
            setAttr("scada", scada);
            render("Runview.html");
        }
    }

    /**
     * 获取tag值
     *
     * @param filename
     * @param tags
     */
    @Clear
    public void getvalues(@Para("filename") String filename, @Para("tags") String tags) {
        List<ScadaTag> rows = ScadaTag.getTagValues(tags);
        Record record = new Record();
        record.set("total", rows.size());
        record.set("rows", rows);
        record.set("login", 1).set("time", "").set("pagecount", 1).set("cid", "");
        renderJson(record);
    }

    public void gettagstable() {
        Record record = new Record();
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        List<ScadaTag> scadaTagList = ScadaTag.dao.findAll(100, 1, new ScadaTag(), user.id());
        for (ScadaTag obj : scadaTagList) {
            obj.set("id", obj.get("id") + "#" + obj.getStr("tagname"));
        }
        record.set("total", scadaTagList.size());
        record.set("rows", scadaTagList);
        record.set("login", 1).set("time", "").set("pagecount", 1).set("cid", "");
        renderJson(record);
    }

    /**
     * 获取用户图片
     */
    public void getuserimage() {
        String[] ree = {"10.png", "11.png", "12.png", "13.png", "14.png", "15.png", "16.png", "17.png", "18.png", "19.png", "2.png", "20.png", "21.png", "3.png", "4.png", "5.png", "6.png", "7.png", "8.png", "9.png", "marker-icon-2x.png", "marker-icon.png"};
        renderJson(Arrays.asList(ree));
    }

    /**
     * 直接保存
     *
     * @param model
     */
    public void doUpdate(@Para("") IIPScada model) {
        renderJson(model.update());
    }
}
