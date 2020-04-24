package ee.proov.daemon.service.redis.impl;

import ee.proov.common.model.redis.AbstractCacheableObject;
import ee.proov.daemon.converters.RedisBytesToUserConverter;
import ee.proov.daemon.converters.RedisUserToBytesConverter;
import ee.proov.daemon.service.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisServiceImpl implements RedisService {
    private final RedisTemplate redisTemplate;
    private final RedisUserToBytesConverter redisUserToBytesConverter;
    private final RedisBytesToUserConverter redisBytesToUserConverter;

    public void saveRedisObject(final AbstractCacheableObject object) {
        try {
            final HashOperations hashOperations = redisTemplate.opsForHash();
            hashOperations.put(object.domain(), object.redisKey(), redisUserToBytesConverter.convert(object));
        } catch (Exception e) {
            log.error("Unable to save object to REDIS cache, exception: ", e);
        }
    }

    public AbstractCacheableObject findCacheableObjectByKey(final AbstractCacheableObject object) {
        try {
            final HashOperations hashOperations = redisTemplate.opsForHash();
            return redisBytesToUserConverter.convert((byte[]) hashOperations.get(object.domain(), object.redisKey()));
        } catch (Exception e) {
            log.error("Unable to retrieve object from REDIS cache, exception: ", e);
        }
        return null;
    }

    public void writeOutputAndStoreInCache(final AbstractCacheableObject objectReceived) {
        final AbstractCacheableObject redisCacheObject = findCacheableObjectByKey(objectReceived);
        final boolean isCachedObjectFound = redisCacheObject != null;

        log.info(StringUtils.join("Received through Queue: ", objectReceived.toString()));
        if (isCachedObjectFound) {
            log.info("Found cached element from REDIS with value: " + redisCacheObject.toString());
            log.info("Updating cache entry in REDIS...");
        } else {
            log.info("No cache entry found, adding...");
        }
        saveRedisObject(objectReceived);
    }


}
