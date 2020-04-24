package ee.proov.daemon.service.kafka;

import ee.proov.common.StaticConf;
import ee.proov.common.model.kafka.AbstractKafkaMessage;
import ee.proov.common.model.kafka.PersonSocialRatingKafkaMessage;
import ee.proov.common.model.service.PersonDataDto;
import ee.proov.daemon.service.redis.RedisService;
import ee.proov.daemon.service.generic.SocialRatingCalculatorService;
import ee.proov.daemon.utils.TransformUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class KafkaListenerImpl {

    private final SocialRatingCalculatorService socialRatingCalculatorService;
    private final RedisService redisService;

    @KafkaListener(topics = StaticConf.KAFKA_TOPIC, groupId = StaticConf.KAFKA_GROUP)
    public void listen(AbstractKafkaMessage message) {
        try {
            log.info("============MESSAGE PROCESS START==========");
            if (message instanceof PersonSocialRatingKafkaMessage) {
                processPersonSocialRatingMessage(message);
            } else {
                log.info("Received unknown message in group daemon-group: " + message);
            }
        } catch (Exception e) {
            log.error("Unable to process message, failed with exception", e);
        } finally {
            log.info("=============MESSAGE PROCESS END===========");
        }
    }

    private void processPersonSocialRatingMessage(final AbstractKafkaMessage abstractKafkaMessage) {
        final PersonDataDto personDataDto = ((PersonSocialRatingKafkaMessage) abstractKafkaMessage).getPersonDataDto();
        redisService.writeOutputAndStoreInCache(
                TransformUtils.PersonSocialMessageToRedisObject(
                        personDataDto,
                        socialRatingCalculatorService.calculatePersonSocialRating(personDataDto.getAge())
                )
        );
    }
}

