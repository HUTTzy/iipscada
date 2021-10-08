package iipscada.model;


import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 查询结果 (只读)
 *
 * @Auther: mao
 * @Date: 18-10-24 09:50
 */
public class DBResult {

    /**
     * 存放数据
     **/
    private final List<Map<String, Object>> datas;
    /**
     * 存放列
     **/
    private final List<String> columns;

    public List<Map<String, Object>> getDatas() {
        return datas;
    }

    public List<String> getColumns() {
        return columns;
    }

    /**
     * 创建结果
     **/
    public DBResult(List<String> columns, List<Map<String, Object>> datas) {
        this.columns = columns;
        this.datas = datas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DBResult))
            return false;

        DBResult dbResult = (DBResult) o;
        return Objects.equals(datas, dbResult.datas) &&
                Objects.equals(columns, dbResult.columns);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    @Override
    public String toString() {
        if (null == datas || null == columns) {
            throw new RuntimeException("未找到数据!");
        }
        if (0 == datas.size()) {
            return "{\"column\":[],\"values\":[]}";
        }

        StringBuffer stringBuffer = new StringBuffer("{\"column\":[");
        //columns
        for (String column : columns) {
            stringBuffer.append("\"").append(column).append("\",");
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        stringBuffer.append("],\"values\":[");

        //datas
        for (Map<String, Object> data : datas) {
            stringBuffer.append("{");
            for (String column : columns) {
                stringBuffer.append("\"").append(column).append("\":");
                stringBuffer.append("\"").append(data.get(column)).append("\",");
            }
            stringBuffer.setLength(stringBuffer.length() - 1);
            stringBuffer.append("},");
        }
        stringBuffer.setLength(stringBuffer.length() - 1);
        stringBuffer.append("]");

        //end
        stringBuffer.append("}");

        return stringBuffer.toString();
    }

    /***
     * 转json格式
     */
    public JSONObject toJson() {
        if (null == datas || null == columns) {
            throw new RuntimeException("未找到数据!");
        }
        return JSONObject.parseObject(this.toString());
    }

}
