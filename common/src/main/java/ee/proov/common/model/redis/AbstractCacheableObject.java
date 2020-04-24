package ee.proov.common.model.redis;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ee.proov.common.enums.RedisDomainEnum;
import lombok.Getter;

import java.io.Serializable;

@Getter
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({@JsonSubTypes.Type(value = PersonSocialScore.class, name = "personSocialScore")})
public abstract class AbstractCacheableObject implements Serializable {
    public abstract String redisKey();

    public abstract RedisDomainEnum domain();
}
