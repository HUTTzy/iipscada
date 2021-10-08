package iipscada.model;

import com.jfinal.plugin.activerecord.Model;
import iipscada.annotation.DataProvider;
import iipscada.exception.DataProviderException;
import iipscada.factory.DataProviderFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 数据集模型
 *
 * @Auther: mao
 * @Date: 18-10-29 10:24
 */
public class ScadaDataSet extends Model<ScadaDataSet> {
    public static ScadaDataSet dao = new ScadaDataSet();

    public long datasourceid() {
        return getLong("datasourceid");
    }

    public String query() {
        return getStr("query");
    }

    public String caechfield() {
        return getStr("caechfield");
    }

    public Long id() {
        return getLong("id");
    }


    /**
     * 获取数据源列表
     *
     * @param uid
     * @return
     */
    public List<ScadaDataSet> findAllByUid(long uid) {
        return find("select * from v_me_dataset where uid=?", uid);
    }

    /**
     * 添加/修改 数据集
     */
    public boolean toogle(ScadaUser user) {
        set("uid", user.id());
        if (!_getAttrs().containsKey("id") || getLong("id") == 0)
            return save();
        return update();
    }

    /**
     * 查询字段/setId
     */
    public String queryfeildbySetId(long setId) {
        return findById(setId).queryfeild(false);
    }

    /**
     * 查询字段/编辑
     */
    public String queryfeild(boolean isFromcache) {
        //1.找到数据源
        String result = "";
        if (isFromcache && id() != null && id() != 0) {
            //缓存中找
            result = findById(id()).caechfield();
            if (!"".equals(result))
                return result;
        }
        ScadaDataScource scadaDataScource = ScadaDataScource.dao.findById(datasourceid());
        DataProvider dataProvider = DataProviderFactory.createDataProvider(scadaDataScource.dbtype());
        DBParam dbParam = new DBParam(scadaDataScource.url(), scadaDataScource.username(), scadaDataScource.password(), query());
        try {
            //dataProvider.init(dbParam);
            DBResult dbResult = dataProvider.query(dbParam);
            result += StringUtils.join(dbResult.getColumns().toArray(), ",");
            //加入缓存
            if (id() != null)
                keep("id").set("caechfield", result).update();

        } catch (DataProviderException e) {
            e.printStackTrace();
            result = "error:" + e.getMsg();
        } finally {
            return result;
        }
    }
}
