package iipscada.model;

import com.jfinal.plugin.activerecord.Record;
import iipscada.annotation.DataProvider;
import iipscada.paginate.PaginateCondition;
import iipscada.paginate.PaginateFilter;
import iipscada.exception.DataProviderException;
import iipscada.paginate.PaginateModel;
import iipscada.service.DataProviderService;
import iipscada.util.MaoDBUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 标签
 *
 * @Auther: mao
 * @Date: 18-10-21 10:25
 */
public class ScadaTag extends PaginateModel<ScadaTag> {
    public static final ScadaTag dao = new ScadaTag();

    @Override
    public PaginateFilter paginateFilter() {
        return attr -> {
            switch (attr) {
                case "datasourceid":
                case "datasetid":
                    return "${val}='${filterVal}'";
            }
            return "${val}like'%${filterVal}%'";
        };
    }

    public Long id() {
        return getLong("id");
    }

    public String getDatasetId() {
        return get("datasetid").toString();
    }

    /**
     * 获取用户创建的tag
     */
    public List<ScadaTag> findAll(int limit, int page, ScadaTag params, long id) {
        params.set("uid", id);
        return doPaginate(page, limit, params).setTableName("v_me_tags").buildList();
    }

    /**
     * 获取数量数量
     */
    public int getCount(long id) {
        return findAllByUid(id).size();
    }

    /**
     * 获取标签
     *
     * @return
     */
    public ScadaTag getTagByTagId(String tagId) {
        return findFirst("select * from me_tags where id=?", tagId);
    }

    /**
     * 获取所有标签
     *
     * @return
     */
    public List<ScadaUser> getTags(int limit, int page, ScadaUser params) {
        return MaoDBUtil.paginateLike(limit, page, "v_sys_user", params);
    }

    /**
     * 获取标签的数值
     */
    public static List<ScadaTag> getTagValues(String tagIds) {
        String[] tags = tagIds.split(",");
        //结果集
        List<ScadaTag> resultTagList = new ArrayList<>();
        StringBuffer ids = new StringBuffer();
        //数据集 -> 字段
        Map<String, List<ScadaTag>> fieldsMap;

        //1.查找到涉及的tag
        for (String tagIdTmp : tags) {
            if (!tagIdTmp.contains("#"))
                continue;
            String[] tmp = tagIdTmp.split("#");
            ids.append(tmp[0].split("[.]")[1]).append(",");
        }
        if (ids.length() == 0)
            return resultTagList;
        ids.setLength(ids.length() - 1);
        List<ScadaTag> scadaTagList = MaoDBUtil.findByIds("v_me_tags", ids.toString(), ScadaTag.class);

        //2.按照数据集分类 构建 数据集 -> ScadaTag
        fieldsMap = scadaTagList.stream().collect(Collectors.groupingBy(ScadaTag::getDatasetId));

//        for (Record re : scadaTagList) {
//            String dataSetId = re.getStr("datasetid");
//            if (!fieldsMap.containsKey(dataSetId)) {
//                List<Record> fields = new ArrayList<Record>();
//                fields.add(re);
//                fieldsMap.put(dataSetId, fields);
//            } else {
//                List<Record> tmp = fieldsMap.get(dataSetId);
//                tmp.add(re);
//                fieldsMap.put(dataSetId, tmp);
//            }
//        }


        //3.开始查询
        for (String dataSetId : fieldsMap.keySet()) {
            List<ScadaTag> fields = fieldsMap.get(dataSetId);
            //查找
            if (fields.size() > 0) {
                ScadaTag param = fields.get(0);
                DBParam dbParam = new DBParam(param.get("url"), param.get("username"), param.get("password"), param.get("query"));
                DataProvider dataProvider;
                try {
                    dataProvider = DataProviderService.getDataProvider(param.get("dbtype"), dbParam);
                    DBResult dbResult = dataProvider.query(dbParam);
                    //结果字段
                    List<String> resultField = dbResult.getColumns();
                    Map<String, Object> dataRow = dbResult.getDatas().get(0);

                    for (ScadaTag field : fields) {
                        String plug = field.getStr("plug");
                        if (resultField.contains(plug)) {
                            field.set("value", dataRow.get(plug));
                            field.set("id", plug + "." + field.get("id") + "#" + field.getStr("tagname"));
                            resultTagList.add(field);
                        }
                    }
                } catch (DataProviderException e) {
                    e.printStackTrace();
                }
            }
        }

        return resultTagList;
    }

    public List<ScadaTag> findAllByUid(long id) {
        return find("select * from me_tags where uid=?", id);
    }
}
