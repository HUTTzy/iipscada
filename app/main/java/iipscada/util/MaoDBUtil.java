package iipscada.util;

import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

import java.util.List;

/**
 * DB操作工具
 *
 * @Auther: mao
 * @Date: 18-10-27 23:32
 */
public class MaoDBUtil {
    /**
     * 创建sql where
     *
     * @param tableName
     * @param params
     * @param <E>
     * @return
     */
    public static <E extends Model> StringBuffer createSlq(String tableName, E params) {
        StringBuffer stringBuffer = new StringBuffer("from ").append(tableName).append(" where ");
        String[] cols = params._getAttrNames();
        if (cols.length > 0) {
            for (String col : cols) {
                Object colValue = params.get(col);
                if (colValue == null || "".equals(col) || "".equals(colValue)) continue;
                stringBuffer.append("`").append(col).append("`");
                stringBuffer.append(" like '%").append(colValue).append("%'  and ");
            }
        }
        return stringBuffer;
    }

    /**
     * 分页查询
     *
     * @param limit     最多条数
     * @param page      第几页
     * @param tableName 查询的表名
     * @param params    过滤模型
     * @return 查询结果
     */
    public static <E extends Model> List<E> paginateLike(int limit, int page, String tableName, E params) {
        StringBuffer stringBuffer = createSlq(tableName, params);
        stringBuffer.setLength(stringBuffer.length() - 6);
        return params.paginate(page, limit, "select *", stringBuffer.toString()).getList();
    }

    /**
     * 分页查询 用户版本
     *
     * @param limit     最多条数
     * @param page      第几页
     * @param tableName 查询的表名
     * @param params    过滤模型
     * @param uid       用户id
     * @return 查询结果
     */
    public static <E extends Model> List<E> paginateLikeByUid(int limit, int page, String tableName, E params, long uid) {
        StringBuffer stringBuffer = createSlq(tableName, params);
        stringBuffer.append("uid=").append(uid);
        return params.paginate(page, limit, "select *", stringBuffer.toString()).getList();
    }

    /**
     * 删除多个记录
     *
     * @param tableName
     * @param ids
     * @return
     */
    public static boolean deleteByIds(String tableName, String ids) {
        StringBuffer stringBuffer = new StringBuffer("delete from ").append(tableName);
        stringBuffer.append(" where id in (").append(ids).append(")");
        return Db.delete(stringBuffer.toString()) > 0;
    }

    /**
     * 查找多个记录
     *
     * @param tableName
     * @param ids
     * @return
     */
    public static List<Record> findByIds(String tableName, String ids) {
        StringBuffer stringBuffer = new StringBuffer("select * from ").append(tableName);
        stringBuffer.append(" where id in (").append(ids).append(")");
        return Db.find(stringBuffer.toString());
    }

    /**
     * 查找多个记录
     *
     * @param tableName
     * @param ids
     * @return
     */
    public static <E extends Model> List<E> findByIds(String tableName, String ids, Class<E> type) {
        StringBuffer stringBuffer = new StringBuffer("select * from ").append(tableName);
        stringBuffer.append(" where id in (").append(ids).append(")");
        E tool = (E) ReflectionUtil.newInstance(type);
        return tool.find(stringBuffer.toString());
    }


}
