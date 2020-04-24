package ee.proov.daemon.service.redis;

import ee.proov.common.model.redis.AbstractCacheableObject;

public interface RedisService {
    void saveRedisObject(final AbstractCacheableObject object);

    AbstractCacheableObject findCacheableObjectByKey(final AbstractCacheableObject object);

    void writeOutputAndStoreInCache(final AbstractCacheableObject objectReceived);
}
