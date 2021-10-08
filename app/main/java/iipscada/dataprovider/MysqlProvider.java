package iipscada.dataprovider;

import iipscada.annotation.DataProvider;
import iipscada.annotation.ProviderInfo;
import iipscada.dataprovider.cache.ConnectionCache;
import iipscada.exception.DataNotFoundException;
import iipscada.exception.DataProviderException;
import iipscada.exception.LinkFailureExceptionData;
import iipscada.model.DBParam;
import iipscada.model.DBResult;
import org.influxdb.InfluxDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Mysql数据服务插件
 *
 * @Auther: mao
 * @Date: 18-10-24 9:25
 */
@ProviderInfo(value = "Mysql", desc = "jdbc:mysql://localhost:3306/数据库名")
public class MysqlProvider implements DataProvider {
    private DBParam dbParam;

    //最大缓存数量
    private final static int MAX_CACHED = 10;
    private static Map<String, Connection> cache = new ConnectionCache<>(MAX_CACHED);

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConn() {
        if (cache.containsKey(dbParam.url()))
            return cache.get(dbParam.url());
        return null;
    }

    @Override
    public DBResult query(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> datas = new ArrayList<>();
        Connection connection = getConn();
        if (connection != null) {
            try {
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(dbParam.query());
                ResultSetMetaData rsm = rs.getMetaData();
                int colNum = rsm.getColumnCount();

                for (int i = 1; i <= colNum; i++) {
                    columns.add(rsm.getColumnName(i));
                }

                while (rs.next()) {
                    Map<String, Object> data = new HashMap<>();
                    for (String feild : columns) {
                        data.put(feild, rs.getString(feild));
                    }
                    datas.add(data);
                }
                rs.close();
                stmt.close();
            } catch (SQLException e) {
                cache.remove(dbParam.url());
                throw new DataNotFoundException(e.getMessage());
            }
        } else {
            if (init(dbParam))
                return query(dbParam);
        }
        return new DBResult(columns, datas);
    }

    //@Override
    public boolean init(DBParam dbParam) throws LinkFailureExceptionData {
        this.dbParam = dbParam;
        try {
            Connection conn = DriverManager.getConnection(dbParam.url(), dbParam.name(), dbParam.pwd());
            if (conn != null)
                cache.put(dbParam.url(), conn);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LinkFailureExceptionData(e.getMessage());
        }
        return true;
    }

    @Override
    public boolean isConnected(DBParam dbParam) throws LinkFailureExceptionData {
        return init(dbParam);
    }
}
