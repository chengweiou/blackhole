package chengweiou.universe.blackhole.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import chengweiou.universe.blackhole.util.LogUtil;

public class BaseDbCache {
    private static final Cache<String, Object> cache = Caffeine.newBuilder().maximumSize(10_000).expireAfterAccess(1, TimeUnit.MINUTES).build();

    public static void save(String k, Object v) {
        cache.put(k, v);
    }
    public static void delete(String k) {
        cache.invalidate(k);
    }
    public static void delete(List<String> kList) {
        cache.invalidateAll(kList);
    }
    public static void clear() {
        cache.invalidateAll();
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
// todo 如果是findbykey，by samele 来更新或者删除的数据，如何清理缓存
