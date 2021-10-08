package iipscada.paginate;

import com.jfinal.plugin.activerecord.Record;

/**
 * 额外的过滤条件
 *
 * @Auther: mao
 * @Date: 18-11-28 09:21
 */
public class PaginateCondition {
    private Record record = new Record();

    public PaginateCondition addCondition(String attr, Object val) {
        if (null == val)
            throw new RuntimeException("过滤值不能为null");
        record.set(attr, val);
        return this;
    }

    public String[] getAttr() {
        return record.getColumnNames();
    }

    public Object getVal(String attr) {
        return record.get(attr);
    }
}
