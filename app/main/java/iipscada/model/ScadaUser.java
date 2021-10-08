package iipscada.model;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Record;
import iipscada.paginate.PaginateFilter;
import iipscada.config.ScadaConst;
import iipscada.paginate.PaginateModel;
import iipscada.util.MD5Util;
import iipscada.util.MaoDBUtil;

import java.util.List;

/**
 * @Auther: mao
 * @Date: 18-10-18 10:52
 */
public class ScadaUser extends PaginateModel<ScadaUser> {
    public static ScadaUser dao = new ScadaUser();

    private static String[] feilds = {
            "id", "login_name", "nick_name", "email", "password", "mobile", "rid", "stat"
    };

    /**
     * 权限
     **/
    public String auth() {
        return get("auths", "");
    }

    /**
     * 路由权限
     **/
    public String routerAuth() {
        return get("auth", "");
    }


    /**
     * 是不是平台管理员
     **/
    public boolean isSuperAdmin() {
        return id() == ScadaConst.SUPER_ADMIN;
    }

    /**
     * 获取用户数量
     **/
    public int getUserCount() {
        return findAll().size();
    }

    /**
     * 尝试登录
     */
    public ScadaUser findByNamePwd() {
        ScadaUser user = findFirst("select * from v_sys_user2 where login_name=? and password=?", loginName(), MD5Util.encode(password()));
        ScadaMenu.dao.addAuthToUser(user);
        return user;
    }

    /**
     * 获取菜单
     */
    public String menu() {
        return get("menuids", "");
    }

    /**
     * 所有用户
     */
    private List<ScadaUser> findAll() {
        return find("select * from v_sys_user");
    }

    /**
     * 获取登录名
     **/
    public String loginName() {
        return getStr("login_name");
    }

    /**
     * 获取角色
     **/
    public long loginRid() {
        return getLong("rid");
    }

    /**
     * 获取最大scada数
     */
    public long maxscada() {
        return getInt("maxscada");
    }

    /**
     * 获取id
     */
    public long id() {
        return getLong("id");
    }

    /**
     * 获取密码
     */
    public String password() {
        return getStr("password");
    }

    /**
     * 获取所有用户
     *
     * @return
     */
    public List<ScadaUser> getUsers(int limit, int page, ScadaUser params) {
        return MaoDBUtil.paginateLike(limit, page, "v_sys_user", params);
    }

    /**
     * 删除多个user
     */
    public boolean deleteByIds(String ids) {
        return MaoDBUtil.deleteByIds("sys_user", ids);
    }

    /**
     * 获取状态
     *
     * @return
     */
    public int stat() {
        return getInt("stat");
    }

    /**
     * 删除用户
     */
    public boolean deleteById(long id) {
        return Db.deleteById("sys_user", id);
    }

    /**
     * 保存用户
     */
    @Override
    public boolean update() {
        Record userRecord = toRecord();
        String pwd = password();
        if (pwd != null) {
            userRecord.set("password", MD5Util.encode(pwd));
        }
        userRecord.keep(feilds);
        return Db.update("sys_user", userRecord);
    }

    @Override
    public boolean save() {
        Record userRecord = toRecord();
        userRecord.remove("id");
        String pwd = MD5Util.getInitPwd();
        userRecord.set("initpwd", pwd);
        userRecord.set("password", MD5Util.encode(pwd));
        return Db.save("sys_user", userRecord);
    }

    /**
     * ScadaUser分页过滤器/如果返回null则使用默认过滤器
     **/
    @Override
    public PaginateFilter paginateFilter() {
        return (attr) -> {
            switch (attr) {
                case "rid":
                    return "${val}='${filterVal}'";
            }
            return "${val}like'%${filterVal}%'";
        };
    }
}
