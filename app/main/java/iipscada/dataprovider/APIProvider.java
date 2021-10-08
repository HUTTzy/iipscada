package iipscada.dataprovider;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import iipscada.annotation.DataProvider;
import iipscada.annotation.ProviderInfo;
import iipscada.exception.DataNotFoundException;
import iipscada.exception.DataProviderException;
import iipscada.exception.LinkFailureExceptionData;
import iipscada.exception.UrlIncorrectExceptionData;
import iipscada.model.DBParam;
import iipscada.model.DBResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: mao
 * @Date: 18-10-25 21:21
 */
@ProviderInfo(value = "API", desc = "数据地址,具体格式参照文档")
public class APIProvider implements DataProvider {
    private DBParam dbParam;

    @Override
    public DBResult query(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        try {
            URL url = new URL(dbParam.url());
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                throw new UrlIncorrectExceptionData("请求接口参数有误!");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder sb = new StringBuilder("");
            String current;
            while ((current = in.readLine()) != null) {
                sb.append(current);
            }
            JSONObject object = JSONObject.parseObject(sb.toString());
            List<String> columns = new ArrayList<String>(object.keySet());
            List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
            for (String column : columns) {
                Map<String, Object> tmpMap = new HashMap<String, Object>();
                tmpMap.put(column, object.get(column));
                datas.add(tmpMap);
            }

            return new DBResult(columns, datas);
        } catch (JSONException e) {
            throw new DataNotFoundException("数据格式错误!");
        } catch (MalformedURLException e) {
            throw new UrlIncorrectExceptionData("请求接口参数有误!");
        } catch (IOException e) {
            throw new DataNotFoundException("数据请求失败,请检查接口是否正常!");
        }
    }

    //@Override
    public boolean init(DBParam dbParam) throws DataProviderException {
        this.dbParam = dbParam;
        return dbParam != null;
    }

    @Override
    public boolean isConnected(DBParam dbParam) throws DataProviderException {
        boolean isConnected = true;
        try {
            URL url = new URL(dbParam.url());
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = null;
            if (urlConnection instanceof HttpURLConnection) {
                connection = (HttpURLConnection) urlConnection;
            } else {
                isConnected = false;
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (Exception e) {
            isConnected = false;
            throw new LinkFailureExceptionData(e.getMessage());
        } finally {
            return isConnected;
        }
    }
}
