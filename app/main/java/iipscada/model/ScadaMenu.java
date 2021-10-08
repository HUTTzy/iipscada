package iipscada.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;

import java.sql.CallableStatement;
import java.util.ArrayList;
import java.util.List;

/**
 * 菜单模型
 *
 * @Auther: mao
 * @Date: 18-11-15 10:52
 */
public class ScadaMenu extends Model<ScadaMenu> {
    public static ScadaMenu dao = new ScadaMenu();

    public boolean toggle() {
        Integer id = get("id", 0);
        System.out.println(this);
        if (id == 0) {
            return save();
        } else
            return update();
    }

    /**
     * 赋予路由权限
     **/
    public void addAuthToUser(ScadaUser user) {
        if (user == null)
            return;
        StringBuffer authBuffer = new StringBuffer();
        List<ScadaMenu> scadaMenuList = findByIds(user.menu());
        for (ScadaMenu menu : scadaMenuList) {
            authBuffer.append(menu.auth()).append(";");
        }
        if (scadaMenuList.size() != 0)
            authBuffer.setLength(authBuffer.length() - 1);
        user.put("auth", authBuffer.toString());
    }

    /**
     * 获取角色菜单
     **/
    public List<ScadaMenu> findByRid(long rid) {
        ScadaRole role = ScadaRole.dao.findById(rid);
        return findByIds(role.menuId());
    }

    /**
     * 获取所有菜单
     **/
    public List<ScadaMenu> findByIds(String ids) {
        if (ids.trim().equals(""))
            return new ArrayList<>();
        return find("select * from sys_menu where id in (" + ids + ") order by csort");
    }

    /**
     * 获取所有菜单
     **/
    public List<ScadaMenu> findAll() {
        return find("select * from sys_menu order by csort");
    }

    /**
     * 获取所有菜单
     **/
    public String auth() {
        return get("auth", "");
    }

    /**
     * 添加菜单
     **/
    @Override
    public boolean save() {
        remove("id");
        boolean re = super.save();
        System.out.println(this);
        if (re)
            executeProcedure("scada_AddBtnOnMenuAdded", getInt("id"));
        return re;
    }

    /**
     * 修改菜单
     **/
    @Override
    public boolean update() {
        return super.update();
    }

    /**
     * 删除菜单
     */
    @Override
    public boolean delete() {
        boolean re = super.delete();
        if (re)
            executeProcedure("scada_DeleteBtnOnMenuDeleted", get("id"));
        return re;
    }

    /**
     * 执行菜单
     */
    private void executeProcedure(String procedureName, int value) {
        Db.execute((cnn) -> {
            CallableStatement proc = cnn.prepareCall("{call " + procedureName + "(?)}");
            proc.setInt(1, value);
            proc.execute();
            if (cnn != null)
                cnn.close();
            if (proc != null)
                proc.close();
            return null;
        });
    }
}
