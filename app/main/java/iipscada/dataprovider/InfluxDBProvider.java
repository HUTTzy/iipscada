package iipscada.dataprovider;

import iipscada.annotation.DataProvider;
import iipscada.annotation.ProviderInfo;
import iipscada.dataprovider.cache.ConnectionCache;
import iipscada.exception.DataNotFoundException;
import iipscada.exception.DataProviderException;
import iipscada.exception.LinkFailureExceptionData;
import iipscada.exception.UrlIncorrectExceptionData;
import iipscada.model.DBParam;
import iipscada.model.DBResult;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.Query;
import org.influxdb.dto.QueryResult;

import java.util.*;


/**
 * InfluxDB服务插件
 *
 * @Auther: mao
 * @Date: 18-10-23 23:08
 */
@ProviderInfo(value = "InfluxDB", desc = "http://localhost:8086?db=数据库名")
public class InfluxDBProvider implements DataProvider {
    private DBParam dbParam;
    private String database;
    private String url;
    //最大缓存数量
    private final static int MAX_CACHED = 10;
    private static Map<String, InfluxDB> cache = new ConnectionCache<>(MAX_CACHED);


    private InfluxDB getConnection() {
        String uKey = url;
        if (!cache.containsKey(uKey))
            return null;
        return cache.get(uKey);
    }

    @Override
    public DBResult query(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        InfluxDB connection = getConnection();
        if (connection != null) {
            try {
                QueryResult queryRe = connection.query(new Query(dbParam.query(), database));
                QueryResult.Result results = queryRe.getResults().get(0);
                QueryResult.Series series = results.getSeries().get(0);
                List<String> columns = series.getColumns();
                List<List<Object>> values = series.getValues();
                List<Map<String, Object>> datas = new ArrayList<>();
                for (List<Object> objs : values) {
                    Map<String, Object> data = new HashMap<>();
                    for (int i = 0; i < columns.size(); i++) {
                        data.put(columns.get(i), objs.get(i));
                    }
                    datas.add(data);
                }
                return new DBResult(columns, datas);
            }  catch (Exception e) {
                //获取失败,从缓存中移除
                cache.remove(connection);
                throw new DataNotFoundException("InfluxDB数据获取失败,请检查数据源!");
            }
        } else {
            if (init(dbParam))
                return query(dbParam);
            throw new DataNotFoundException("InfluxDB初始化失败");
        }
    }

    //@Override
    public boolean init(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        String totalUrl = dbParam.url();
        InfluxDB influxDB;
        try {
            int querySplit = totalUrl.lastIndexOf("?");
            url = totalUrl.substring(0, querySplit);
            if (-1 == totalUrl.lastIndexOf("db="))
                throw new Exception();
            this.database = totalUrl.substring(totalUrl.lastIndexOf("db=") + 3, totalUrl.length());
            influxDB = InfluxDBFactory.connect(url, dbParam.name(), dbParam.pwd());
            cache.put(url, influxDB);
        } catch (Exception e) {
            throw new UrlIncorrectExceptionData("InfluxDB连接串格式有误,请检查");
        }

        List<String> dbList;
        try {
            dbList = influxDB.describeDatabases();
        } catch (Exception e) {
            throw new LinkFailureExceptionData(e.getMessage());
        }

        if (dbList == null || !dbList.contains(this.database))
            throw new DataNotFoundException("数据库【" + this.database + "】未找到");
        return true;
    }

    @Override
    public boolean isConnected(DBParam dbParam) throws DataProviderException {
        return init(dbParam);
    }

}
