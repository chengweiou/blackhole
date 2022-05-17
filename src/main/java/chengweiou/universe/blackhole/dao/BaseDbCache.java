package chengweiou.universe.blackhole.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import chengweiou.universe.blackhole.util.LogUtil;

// todo 如果是不规则的 update，delete（不是基本，没用basedio），然后再findById。如何清除缓存？
public class BaseDbCache {
    private static final Cache<String, Object> cache = Caffeine.newBuilder().maximumSize(10_000)
        .expireAfterWrite(3, TimeUnit.MINUTES) // 无论如何，最多 3min 就会过期
        .expireAfterAccess(1, TimeUnit.MINUTES).build(); // 每次使用加 1min，但不会超过上面的3min

    public static void save(String k, Object v) {
        cache.put(k, v);
        LogUtil.d("save " + k + " to cache");
    }
    public static void delete(String k) {
        cache.invalidate(k);
        LogUtil.d("delete " + k + " from cache");
    }
    public static void delete(List<String> kList) {
        cache.invalidateAll(kList);
        LogUtil.d("delete " + kList + " from cache");
    }
    public static void clear() {
        cache.invalidateAll();
        LogUtil.d("clear all from cache");
    }
    public static Object get(String k) {
        Object result = cache.get(k, key -> null);
        if (result != null) LogUtil.d("get " + k + " from cache");
        return result;
    }

    public static Map<String, Object> get(List<String> kList) {
        Map<String, Object> result = cache.getAllPresent(kList);
        if (!result.isEmpty()) LogUtil.d("get " + kList + " from cache, expect: " + kList.size() + " success: " + result.size());
        return result;
    }
}
