package iipscada.dataprovider.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 数据库连接缓存
 *
 * @Auther: mao
 * @Date: 18-11-28 00:15
 */

public class ConnectionCache<K, V> extends LinkedHashMap<K, V> {
    //默认最大缓存数
    private int MAX_CACHE = 15;

    public ConnectionCache() {
        this(15);
    }

    public ConnectionCache(int maxChache) {
        super(15, 0.75f, true);
        if (maxChache <= 0)
            throw new RuntimeException("最大缓存数量必须大于0!");
        MAX_CACHE = maxChache;
    }

    //删除早期的缓存
    @Override
    public synchronized boolean removeEldestEntry(Map.Entry<K, V> entry) {
        return size() > MAX_CACHE;
    }

    @Override
    public synchronized V get(Object key) {
        return super.get(key);
    }

    @Override
    public synchronized int size() {
        return super.size();
    }

    @Override
    public synchronized V put(K key, V value) {
        return super.put(key, value);
    }

    @Override
    public synchronized boolean containsKey(Object key) {
        return super.containsKey(key);
    }
}
