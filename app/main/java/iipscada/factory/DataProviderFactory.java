package iipscada.factory;

import iipscada.annotation.DataProvider;
import iipscada.annotation.ProviderInfo;
import iipscada.util.ClassUtil;
import iipscada.util.ReflectionUtil;

import java.util.*;

/**
 * DataPrivider工厂
 *
 * @Auther: mao
 * @Date: 18-10-24 10:22
 */
public class DataProviderFactory {

    private static Map<String, Class<?>> dataProviders = DataProviderFactory.getDataProviders();
    private static Map<String, String> providerInfoMap;
    //private static List<String> providerNames;

    /**
     * 获取所有provider
     */
    private static Map<String, Class<?>> getDataProviders() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("iipscada.dataprovider");
        Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
        //Map<String, DataProvider> dataProviderMap = new HashMap<String, DataProvider>();
        //providerNames = new ArrayList<String>();
        providerInfoMap = new HashMap<String, String>();
        for (Class<?> cls : classSet) {
            if (cls.isAnnotationPresent(ProviderInfo.class) && DataProvider.class.isAssignableFrom(cls)) {
                ProviderInfo providerInfo = cls.getAnnotation(ProviderInfo.class);
                String providerName = providerInfo.value();
                String providerDesc = providerInfo.desc();
                classMap.put(providerName, cls);
                providerInfoMap.put(providerName, providerDesc);
                //providerNames.add(providerName);
                //dataProviderMap.put(providerInfo.value(), (DataProvider) ReflectionUtil.newInstance(cls));
            }
        }
        return classMap;
    }

    /**
     * 获取所有dataproviderInfo
     */
    public static Map<String, String> getProviderNames() {
        return providerInfoMap;
    }


    /**
     * 创建数据提供器
     */
    public static DataProvider createDataProvider(String providerName) {
        Class<?> cls = dataProviders.get(providerName);
        return (DataProvider) ReflectionUtil.newInstance(cls);
    }
}
