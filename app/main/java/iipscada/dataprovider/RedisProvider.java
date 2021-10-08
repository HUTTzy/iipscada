package iipscada.dataprovider;

import iipscada.annotation.DataProvider;
import iipscada.annotation.ProviderInfo;
import iipscada.config.ScadaConst;
import iipscada.exception.DataNotFoundException;
import iipscada.exception.DataProviderException;
import iipscada.exception.LinkFailureExceptionData;
import iipscada.model.DBParam;
import iipscada.model.DBResult;
import iipscada.model.ScadaUser;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.*;

/**
 * @Auther: mao
 * @Date: 18-10-24 22:03
 */
@ProviderInfo(value = "Redis", desc = "例如:127.0.0.1:6379")
public class RedisProvider implements DataProvider {
    private Jedis jedis;
    private String url;
    private int port;
    private DBParam dbParam;
    private String password;

    @Override
    public DBResult query(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        List<String> columns = new ArrayList<>();
        List<Map<String, Object>> datas = new ArrayList<>();

        if (jedis != null) {
            String queryStr = dbParam.query();
            int limiter = queryStr.indexOf("#");
            String key;
            int limit = 0;
            if (limiter != -1) {
                key = queryStr.substring(0, limiter);
                try {
                    limit = Integer.parseInt(queryStr.substring(limiter + 1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                key = queryStr;
            }
            try {
                if (!jedis.exists(key))
                    throw new DataNotFoundException("Redis键:【" + key + "】不存在!");

                switch (jedis.type(key)) {
                    case "list":
                        List<String> datasListTmp = jedis.lrange(key, 0, limit - 1);
                        columns.add(key);
                        for (String data : datasListTmp) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(key, data);
                            datas.add(map);
                        }
                        break;
                    case "zset":
                    case "set":
                        Set<String> datasSetTmp = jedis.smembers(key);
                        columns.add(key);
                        for (String data : datasSetTmp) {
                            Map<String, Object> map = new HashMap<>();
                            map.put(key, data);
                            datas.add(map);
                        }
                        break;
                    case "hash":
                        Iterator<String> iterator = jedis.hkeys(key).iterator();
                        Map<String, Object> map = new HashMap<>();
                        while (iterator.hasNext()) {
                            String keySet = iterator.next();
                            columns.add(keySet);
                            map.put(keySet, jedis.hmget(key, keySet).get(0));
                        }
                        datas.add(map);
                        break;
                    case "string":
                        columns.add(key);
                        Map<String, Object> mapString = new HashMap<>();
                        mapString.put(key, jedis.get(key));
                        datas.add(mapString);
                        break;
                }
            } catch (JedisConnectionException e) {
                throw new DataNotFoundException("Redis连接失败，请检查Redis服务器");
            }


        }
        return new DBResult(columns, datas);
    }

    //@Override
    public boolean init(DBParam dbParam) throws LinkFailureExceptionData {
        this.dbParam = dbParam;
        int splitor = dbParam.url().lastIndexOf(":");
        String paramUrl = dbParam.url();
        if (splitor == -1) {
            url = paramUrl;
            port = 6379;
        } else {
            url = paramUrl.substring(0, splitor);
            port = Integer.parseInt(paramUrl.substring(splitor + 1));
        }


        password = dbParam.pwd();
        jedis = new Jedis(url, port);
        try {
            if (null != password && !"".equals(password))
                jedis.auth(password);

            jedis.exists(ScadaConst.USER);
        } catch (Exception e) {
            throw new LinkFailureExceptionData(e.getMessage());
        }

        return jedis != null;
    }

    @Override
    public boolean isConnected(DBParam dbParam) {
        return jedis.isConnected();
    }
}
