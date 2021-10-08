package iipscada.paginate;

import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Page;
import com.jfinal.plugin.activerecord.Record;
import com.jfinal.plugin.activerecord.Table;
import iipscada.util.CUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>分页模型</p>
 *
 * @version 1.0
 * @auther: mao
 * @Date: 18-11-24 19:57
 * @see PaginateFilter
 */
public abstract class PaginateModel<T extends PaginateModel> extends Model<T> {

    //过滤器
    private PaginateFilter paginateFilter;
    //额外条件
    private PaginateCondition paginateCondition;

    public PaginateFilter paginateFilter() {
        return null;
    }

    public PaginateCondition paginateCondition() {
        return null;
    }

    //保留字段
    public String[] gridAttr() {
        return null;
    }

    public PaginateModel addCondition(String attr, Object val) {
        if (paginateCondition == null)
            paginateCondition = new PaginateCondition();
        paginateCondition.addCondition(attr, val);
        return this;
    }

    public PaginateBuilder doPaginate(int page, int limit) {
        Record a = new Record();
        return doPaginate(page, limit, _getTable().getName(), this, null);
    }

    public PaginateBuilder doPaginate(int page, int limit, PaginateFilter pf) {
        return doPaginate(page, limit, _getTable().getName(), this, pf);
    }

    public T findById(long id) {
        Table t = _getTable();
        Objects.requireNonNull(t, "未找到合适的表!");

        String tName = t.getName();
        if (StringUtils.isEmpty(tName))
            throw new NullPointerException("未找到合适的表!");
        return findFirst("select * from " + tName + " where id=?" + id);
    }

    /**
     * 静态查询
     **/
    public static <M extends Model> PaginateBuilder doPaginate(int page, int limit, String tableName, M params, PaginateFilter pf) {
        return new PaginateBuilder(params, tableName, page, limit).setPaginateFilter(pf);
    }

    public static <M extends Model> PaginateBuilder doPaginate(int page, int limit, M params) {
        return new PaginateBuilder(params, page, limit);
    }

    /**
     * 分页构建器
     */
    public static class PaginateBuilder<T extends Model> {
        private final int page;
        private final int limit;
        private final T params;


        private Page pageList;
        private List dataList;
        private int size;
        private PaginateFilter pf = null;
        private String tableName;

        private PaginateBuilder(T params, int page, int limit) {
            this.params = params;
            this.limit = limit;
            this.page = page;
        }

        private PaginateBuilder(T params, String tableName, int page, int limit) {
            this.params = params;
            this.tableName = tableName;
            this.limit = limit;
            this.page = page;
        }

        /**
         * 查询
         */
        private void build() {
            if (null == tableName || "".equals(tableName))
                throw new RuntimeException("创建分页查询-表名为空");
            if (null == pf) {
                if (params instanceof PaginateModel) {
                    PaginateModel tmpPf = ((PaginateModel) params);
                    pf = tmpPf.getPaginateFilter();
                    if (null == pf) {
                        pf = tmpPf.paginateFilter();
                        tmpPf.paginateFilter = pf;
                    }
                }
                if (null == pf)
                    pf = attr -> "${val}like'%${filterVal}%'";
            }
            StringBuilder stringBuilder = createQuery(tableName, params, pf);
            //过滤列
            String attrs = "select ";
            if (params instanceof PaginateModel) {
                PaginateModel tmpPf = ((PaginateModel) params);
                String[] gridAttr = tmpPf.gridAttr();
                if (gridAttr != null) {
                    attrs += CUtil.join(",", gridAttr);
                } else
                    attrs += "*";
            }
            pageList = params.paginate(page, limit, attrs, stringBuilder.toString());
            size = pageList.getTotalRow();
            dataList = pageList.getList();
        }

        /**
         * 获取数据List
         *
         * @return
         */
        public List buildList() {
            build();
            return dataList;
        }

        /**
         * 获取符合layui datagrid格式
         *
         * @return record
         */
        public Record buildDataGrid() {
            build();
            Record record = new Record();
            record.set("count", size)
                    .set("msg", "")
                    .set("data", dataList)
                    .set("code", 0);
            return record;
        }

        /**
         * 创建查询
         *
         * @param tableName
         * @param params
         * @param <E>
         * @return
         */
        private static synchronized <E extends Model> StringBuilder createQuery(String tableName, E params, PaginateFilter pf) {
            StringBuilder stringBuilder = new StringBuilder("from ").append(tableName).append(" where ");
            String[] cols = params._getAttrNames();
            if (cols.length > 0) {
                for (String col : cols) {
                    Object colValue = params.get(col);
                    if (colValue == null || "".equals(col) || "".equals(colValue)) continue;
                    String tCol = "`" + col + "`";
                    String filter = pf.doFilter(col);
                    filter = filter.replace("${val}", tCol).replace("${filterVal}", colValue.toString());
                    stringBuilder.append(filter).append("  and ");
                }
                //额外的条件
                if (params instanceof PaginateModel) {
                    PaginateModel pParams = (PaginateModel) params;
                    PaginateCondition condition = pParams.paginateCondition;
                    if (condition == null) {
                        pParams.paginateCondition = pParams.paginateCondition();
                        condition = pParams.paginateCondition;
                    }
                    if (condition != null) {
                        String[] exCols = condition.getAttr();
                        for (String col : exCols) {
                            Object colValue = condition.getVal(col);
                            if (colValue == null || "".equals(col) || "".equals(colValue)) continue;
                            String tCol = "`" + col + "`";
                            String filter = pf.doFilter(col);
                            filter = filter.replace("${val}", tCol).replace("${filterVal}", colValue.toString());
                            stringBuilder.append(filter).append("  and ");
                        }
                    }
                }

            }
            stringBuilder.setLength(stringBuilder.length() - 6);
            return stringBuilder;
        }

        /**
         * 设置过滤器
         *
         * @param pf
         * @return
         */
        public PaginateBuilder setPaginateFilter(PaginateFilter pf) {
            this.pf = pf;
            return this;
        }

        /**
         * 设置表名
         *
         * @param tableName
         */
        public PaginateBuilder setTableName(String tableName) {
            this.tableName = tableName;
            return this;
        }
    }

    private PaginateFilter getPaginateFilter() {
        return paginateFilter;
    }
}
