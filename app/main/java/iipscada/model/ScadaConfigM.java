package iipscada.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import iipscada.config.ScadaInit;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: mao
 * @Date: 18-11-21 09:07
 */
public class ScadaConfigM extends Model<ScadaConfigM> {
    public static final ScadaConfigM dao = new ScadaConfigM();

    /**
     * 所有配置信息
     *
     * @return
     */
    public List<ScadaConfigM> findAll() {
        return find("select * from sys_config");
    }

    /**
     * 更新一条
     *
     * @param config
     * @return
     */
    public boolean update(JSONObject config) {
        Set<Map.Entry<String, Object>> entrySet = config.entrySet();
        Record record = new Record();
        for (Map.Entry<String, Object> entry : entrySet) {
            record.set(entry.getKey(), entry.getValue());
        }
        return Db.update("sys_config", record);
    }

    /**
     * 保存配置列表
     *
     * @param jsonArray
     * @return
     */
    public boolean listUpdate(JSONArray jsonArray) {
        boolean re = true;
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject config = jsonArray.getJSONObject(i);
            re = update(config) && re;
        }
        ScadaInit.initSysConfig();
        return re;
    }
}
