package iipscada.controller;

import com.jfinal.core.Controller;
import com.jfinal.core.paragetter.Para;
import com.jfinal.plugin.activerecord.Record;
import iipscada.model.*;

import java.util.List;

/**
 * @Auther: mao
 * @Date: 18-10-18 15:05
 */
public class AdminController extends Controller {

    public void menutoggle(@Para("") ScadaMenu scadaMenu) {
        renderJson(scadaMenu.toggle());
    }

    public void menudelete(@Para("") ScadaMenu scadaMenu) {
        renderJson(scadaMenu.delete());
    }

    /**
     * 所有菜单
     */
    public void menulist() {
        renderJson(ScadaMenu.dao.findAll());
    }

    /**
     * 获取所有用户信息
     */
    public void userlist() {
        render("user/user/list.html");
    }

    /**
     * 获取所有用户
     */
    public void getAllUsers(@Para("limit") int limit, @Para("page") int page, @Para("") ScadaUser params) {
        //PaginateModel例子

        //如果继承了PaginateModel
        //PaginateModel.PaginateBuilder paginateBuilder = params.doPaginate(page, limit);
        //如果未继承PaginateModel
        //PaginateModel.PaginateBuilder paginateBuilder = PaginateModel.doPaginate(int page, int limit, String tableName, Model params, PaginateFilter pf);

        //设置过滤器。如果不设置,则使用PaginateModel实例的过滤器,如果不是PaginateModel则使用默认过滤器
        //paginateBuilder.setPaginateFilter(paginateFilter)

        //获取结果
        //paginateBuilder.buildDataGrid() 适合layui datagrid格式的record
        //paginateBuilder.buildList       直接获取获取列表

        //如果ScadaUser没有继承了PaginateModel,使用静态方法查询
//        PaginateModel.PaginateBuilder pb = PaginateModel.doPaginate(page, limit, params);
//        pb.setTableName("v_sys_user2").setPaginateFilter(e -> "${val}like'%${filterVal}%'");//设置表(必填)、过滤器(选填)
//        renderJson(pb.buildDataGrid());

        //关于过滤器配置请查看PaginateFilter.java

        //如果ScadaUser继承了PaginateModel,简单粗暴
        renderJson(params.doPaginate(page, limit).buildDataGrid());
    }


    /**
     * 删除用户
     */
    public void deleteuser(@Para("ids") String ids) {
        renderJson(ScadaUser.dao.deleteByIds(ids));
    }

    /**
     * 锁定用户
     */
    public void saveuser(@Para("") ScadaUser user) {
        renderJson(user.update());
    }

    /**
     * 修改/添加用户
     */
    public void toggleuser(@Para("") ScadaUser user) {
        if (user.id() == 0) {
            //添加
            renderJson(user.save());
        } else {
            renderJson(user.update());
        }
    }

    /**
     * 获取所有角色界面
     */
    public void rolelistview() {
        render("user/admin/role.html");
    }

    /**
     * 所有角色列表
     */
    public void rolelist() {
        renderJson(ScadaRole.dao.findAll());
    }

    public void rolelisttable() {
        List<ScadaRole> roleList = ScadaRole.dao.findAll();
        Record records = new Record();
        records.set("code", 0).set("msg", "").set("count", roleList.size()).set("data", roleList);
        renderJson(records);
    }

    public void delrole(@Para("ids") String ids) {
        renderJson(ScadaRole.dao.deleteByIds(ids));
    }

    /**
     * 修改/添加角色
     */
    public void togglerole(@Para("") ScadaRole role) {
        if (role.id() == 0)
            //添加
            renderJson(role.save());
        else
            renderJson(role.update());
    }

    /***
     * 权限列表
     */
    public void aulist() {

    }
}
