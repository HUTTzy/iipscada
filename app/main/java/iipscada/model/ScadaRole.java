package iipscada.model;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.ehcache.CacheKit;
import iipscada.util.MaoDBUtil;

import java.util.List;

/**
 * 角色模型
 *
 * @Auther: mao
 * @Date: 18-10-28 11:51
 */
public class ScadaRole extends Model<ScadaRole> {
    public static ScadaRole dao = new ScadaRole();

    @Override
    public boolean save() {
        boolean re = super.save();
        reLoadCache();
        return re;
    }

    @Override
    public boolean update() {
        boolean re = super.update();
        reLoadCache();
        return re;
    }

    public long id() {
        return getLong("id");
    }

    /**
     * 获取所有角色
     */
    public List<ScadaRole> findAll() {
        List<ScadaRole> scadaRoleList = CacheKit.get("scada", "rolelist", () ->
                find("select * from sys_role")
        );
        return scadaRoleList;
    }

    public boolean deleteByIds(String ids) {
        boolean result = MaoDBUtil.deleteByIds("sys_role", ids);
        reLoadCache();
        return result;
    }

    /**
     * 获取权限菜单id
     **/
    public String menuId() {
        return get("menuid", "");
    }

    private void reLoadCache() {
        CacheKit.put("scada", "rolelist", find("select * from sys_role"));
    }
}
