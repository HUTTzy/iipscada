package iipscada.service;

import iipscada.annotation.DataProvider;
import iipscada.exception.DataProviderException;
import iipscada.factory.DataProviderFactory;
import iipscada.model.DBParam;

/**
 * DB数据服务
 *
 * @Auther: mao
 * @Date: 18-10-24 17:32
 */
public class DataProviderService {

    private DataProviderService() {
        new AssertionError("请不要创建这个类");
    }

    /**
     * 获取DataProvider
     *
     * @param providerName
     * @param dbParam
     * @return
     * @throws DataProviderException
     */
    public static DataProvider getDataProvider(String providerName, DBParam dbParam) throws DataProviderException {
        DataProvider dataProvider = DataProviderFactory.createDataProvider(providerName);
        //dataProvider.init(dbParam);
        return dataProvider;
    }
}
