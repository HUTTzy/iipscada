package iipscada.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import iipscada.config.ScadaConst;
import iipscada.model.ScadaDataScource;
import iipscada.model.ScadaDataSet;
import iipscada.model.ScadaUser;

/**
 * 数据(源,集)控制器
 *
 * @Auther: mao
 * @Date: 18-10-28 22:58
 */
public class DataController extends Controller {

    /**
     * 数据源界面
     */
    public void datascource() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        setAttr("datascources", ScadaDataScource.dao.findAllByUid(user.id()));
        setAttr("dbtypes", ScadaDataScource.dao.getDbTypeList());
        render("datascourcelist.html");
    }


    /**
     * 数据集界面
     */
    public void dataset() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        setAttr("datascources", ScadaDataScource.dao.findAllByUid(user.id()));
        setAttr("datasets", ScadaDataSet.dao.findAllByUid(user.id()));
        render("datasetlist.html");
    }

    /**
     * 获取数据源列表
     */
    public void getdatascourcelist() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(ScadaDataScource.dao.findAllByUid(user.id()));
    }

    /**
     * 获取数据集
     */
    public void getdatasetlist() {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(ScadaDataSet.dao.findAllByUid(user.id()));
    }

    /**
     * 保存/修改 数据集
     */
    public void toggledataset(@Para("") ScadaDataSet dataSet) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(dataSet.toogle(user));
    }

    /**
     * 保存/修改 数据源
     */
    public void toggledatascource(@Para("") ScadaDataScource datascource) {
        ScadaUser user = getSessionAttr(ScadaConst.USER);
        renderJson(datascource.toogle(user));
    }

    /**
     * 测试数据集
     */
    public void datasrourcetest(@Para("") ScadaDataScource datascource) {
        renderText(datascource.test());
    }

    /**
     * 删除数据源
     */
    public void deletedatascource(@Para("") ScadaDataScource datascource) {
        renderJson(datascource.delete());
    }

    /**
     * 删除数据集
     */
    public void deletedataset(@Para("") ScadaDataSet scadaDataSet) {
        renderJson(scadaDataSet.delete());
    }

    /**
     * 查询数据集字段
     */
    public void queryfeild(@Para("") ScadaDataSet scadaDataSet, @Para("fromcache") String fromcache) {
        renderText(scadaDataSet.queryfeild(fromcache != null));
    }

    /**
     * 查询数据集字段byId
     */
    public void queryfeildbyid(@Para("id") Long id) {
        renderText(ScadaDataSet.dao.queryfeildbySetId(id));
    }

}
