package iipscada.model;

import com.jfinal.plugin.activerecord.Model;
import iipscada.annotation.DataProvider;
import iipscada.exception.DataProviderException;
import iipscada.factory.DataProviderFactory;

import java.util.List;
import java.util.Map;

/**
 * @Auther: mao
 * @Date: 18-10-28 23:30
 */
public class ScadaDataScource extends Model<ScadaDataScource> {
    public static ScadaDataScource dao = new ScadaDataScource();

    /**
     * 测试数据源是否能连接
     */
    public String test() {
        DBParam dbParam = new DBParam(url(), username(), password());
        DataProvider dataProvider = DataProviderFactory.createDataProvider(dbtype());
        try {
            return (dataProvider.isConnected(dbParam)) ? "连接成功!" : "连接失败!";
        } catch (DataProviderException e) {
            //e.printStackTrace();
            return e.getMsg();
        }
    }

    public String url() {
        return getStr("url");
    }

    public String username() {
        return getStr("username");
    }

    public String password() {
        return getStr("password");
    }

    public String dbtype() {
        return getStr("dbtype");
    }

    /**
     * 保存/修改 数据源
     */
    public boolean toogle(ScadaUser user) {
        set("uid", user.id());
        if (!_getAttrs().containsKey("id") || getLong("id") == 0)
            return save();
        return update();
    }

    /**
     * 支持的数据库列表
     */
    public Map<String, String> getDbTypeList() {
        return DataProviderFactory.getProviderNames();
    }

    /**
     * 获取数据源列表
     *
     * @param uid
     * @return
     */
    public List<ScadaDataScource> findAllByUid(long uid) {
        return this.find("select * from me_datasource where uid=?", uid);
    }
}
