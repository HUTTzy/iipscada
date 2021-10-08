import com.alibaba.fastjson.JSONObject;
import iipscada.annotation.DataProvider;
import iipscada.exception.DataProviderException;
import iipscada.factory.DataProviderFactory;
import iipscada.model.DBParam;
import iipscada.model.DBResult;
import iipscada.service.DataProviderService;
import org.junit.Test;

/**
 * @Auther: mao
 * @Date: 18-10-24 11:13
 */
public class DataProviderTest {

    @Test
    public void APIProviderTest() {
        DBParam dbParam =
                new DBParam("http://127.0.0.1/views/apitest.json", "", "", "");
        try {
            DataProvider dataProvider = DataProviderService.getDataProvider("API", dbParam);
            DBResult dbResult = dataProvider.query(dbParam);
            System.out.println(dbResult.getColumns());
            System.out.println(dbResult);
        } catch (DataProviderException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void MysqlProviderTest() {
        DBParam dbParam =
                new DBParam("jdbc:mysql://120.78.70.126:3306/iipscada?characterEncoding=utf8", "root", "pinjer2015", "select * from sys_user");
        System.out.println("dbParam => " + dbParam);
        try {
            DataProvider dataProvider = DataProviderService.getDataProvider("Mysql", dbParam);
            DBResult dbResult = dataProvider.query(dbParam);
            JSONObject jsonObject = dbResult.toJson();
            System.out.println("jsonObj => " + dbResult);
            System.out.println("dbResultColumns => " + dbResult.getColumns());
            System.out.println("dbResult => " + dbResult);
        } catch (DataProviderException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void InfluxDBTest() throws DataProviderException {
        DBParam dbParam =
                new DBParam("http://139.159.160.214:8086?db=iipscada", "MaoMao", "9069309848", "select * from fycom order by time desc limit 10");
        DataProvider dataProvider = DataProviderService.getDataProvider("InfluxDB", dbParam);
        DBResult dbResult = dataProvider.query(dbParam);
        System.out.println(dbResult.getColumns());
        System.out.println(dbResult);
        System.out.println(DataProviderFactory.getProviderNames());
    }

    @Test
    public void RedisTest() throws DataProviderException {
        DBParam dbParam = new DBParam("127.0.0.1:6379", "", "9069309848", "alertlist");

        DataProvider dataProvider = DataProviderService.getDataProvider("Redis", dbParam);
        DBResult dbResult = dataProvider.query(dbParam);
        System.out.println(dbResult.getColumns());
        System.out.println(dbResult);
        System.out.println(DataProviderFactory.getProviderNames());

    }
}
