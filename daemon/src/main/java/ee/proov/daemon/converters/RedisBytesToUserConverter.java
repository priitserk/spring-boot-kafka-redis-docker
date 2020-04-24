package ee.proov.daemon.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.proov.common.model.redis.AbstractCacheableObject;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Component;

@Component
@ReadingConverter
public class RedisBytesToUserConverter implements Converter<byte[], AbstractCacheableObject> {

    private final Jackson2JsonRedisSerializer<AbstractCacheableObject> serializer;

    public RedisBytesToUserConverter() {
        serializer = new Jackson2JsonRedisSerializer<>(AbstractCacheableObject.class);
        serializer.setObjectMapper(new ObjectMapper());
    }

    @Override
    public AbstractCacheableObject convert(byte[] value) {
        return serializer.deserialize(value);
    }
}