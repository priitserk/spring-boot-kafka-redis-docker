package ee.proov.daemon.utils;

import ee.proov.common.model.redis.PersonSocialScore;
import ee.proov.common.model.service.PersonDataDto;

public class TransformUtils {
    public static PersonSocialScore PersonSocialMessageToRedisObject(final PersonDataDto personDataDto, final double socialScore) {
        return PersonSocialScore.builder()
                .firstName(personDataDto.getFirstName())
                .lastName(personDataDto.getLastName())
                .socialScore(socialScore).build();
    }
}
