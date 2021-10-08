package iipscada.model;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;
import iipscada.config.ScadaInit;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Auther: mao
 * @Date: 18-11-17 16:34
 */
public class ScadaBtn extends Model<ScadaBtn> {
    public static final ScadaBtn dao = new ScadaBtn();

    /**
     * 获取角色权限
     */
    public List<ScadaBtn> findByRid(long rid) {
        return find("select * from sys_role_btn where rid=?", rid);
    }

    /**
     * 删除角色权限
     */
    public boolean deleteRBtn(String ids, long rid) {
        Db.delete("delete from sys_role_btn where rid=? and bid in (" + ids + ")", rid);
        return true;
    }

    /**
     * 添加角色权限
     */
    public boolean addRBtn(Set<String> bidSet, long rid) {
        for (String id : bidSet) {
            Db.update("insert into sys_role_btn (`rid`,`bid`) values(?,?)", rid, id);
        }
        return true;
    }

    /**
     * 获取菜单权限
     */
    public List<ScadaBtn> findMenuAuth(long menuid) {
        return find("select * from sys_btn where menuid=?", menuid);
    }

    /**
     * 获取所有权限btn
     */
    public List<ScadaBtn> findAllBtn() {
        return find("select * from sys_btn");
    }

    /**
     * 设置权限
     */
    public boolean authSet(JSONObject jsonObject, long rid) {
        Set<String> bidSet = jsonObject.keySet();
        StringBuilder stringBuilder = new StringBuilder();
        Set<String> addBidSet = new HashSet<>();
        for (String bid : bidSet) {
            stringBuilder.append(bid).append(",");
            if (jsonObject.getInteger(bid) == 1)
                addBidSet.add(bid);
        }

        if (stringBuilder.length() > 0)
            stringBuilder.setLength(stringBuilder.length() - 1);
        boolean re = deleteRBtn(stringBuilder.toString(), rid) && addRBtn(addBidSet, rid);
        ScadaInit.init();

        return re;
    }

    public boolean setAuth(long menuid, JSONArray jsonArray) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            if (!jsonObject.getInteger("id").equals(0))
                stringBuilder.append(jsonObject.getInteger("id")).append(",");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 1);
        }
        deleteMenuAuth(menuid, stringBuilder.toString());
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            addMenuAuth(menuid, jsonObject);
        }
        ScadaInit.init();
        return true;
    }

    private boolean addMenuAuth(long menuid, JSONObject jsonObject) {
        Record record = new Record().set("auth", jsonObject.get("auth"))
                .set("name", jsonObject.get("name")).set("id", jsonObject.getInteger("id"));
        if (record.getInt("id") == 0) {
            Db.save("sys_btn", record.remove("id").set("menuid", menuid));
        } else {
            Db.update("sys_btn", record);
        }
        return true;
    }

    private boolean deleteMenuAuth(long menuid, String keeps) {
        if (keeps.equals("")) {
            keeps = "0";
        }
        Db.delete("delete from sys_btn where menuid=? and id not in (" + keeps + ")", menuid);
        return true;
    }
}
