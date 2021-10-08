package iipscada.annotation;

import iipscada.exception.DataProviderException;
import iipscada.model.DBParam;
import iipscada.model.DBResult;

/**
 * 数据接口插件
 *
 * @Auther: mao
 * @Date: 18-10-23 23:06
 */
public interface DataProvider {

    /**
     * 单词请求
     *
     * @return
     * @throws DataProviderException
     */
    DBResult query(DBParam dbParam) throws DataProviderException;

    /**
     * 初始化provider
     **/
    //boolean init(DBParam dbParam) throws DataProviderException;

    /**
     * 测试数据源是否能连接成功
     */
    boolean isConnected(DBParam dbParam) throws DataProviderException;
}
