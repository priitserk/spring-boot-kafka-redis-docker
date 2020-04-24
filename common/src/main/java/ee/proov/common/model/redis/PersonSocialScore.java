package ee.proov.common.model.redis;

import ee.proov.common.enums.RedisDomainEnum;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PersonSocialScore extends AbstractCacheableObject {

    private String firstName;
    private String lastName;
    private Double socialScore;

    @Override
    public String redisKey() {
        return "firstName";
    }

    @Override
    public RedisDomainEnum domain() {
        return RedisDomainEnum.REDIS_PERSON_SOCIAL_RATING_DOMAIN;
    }
}
