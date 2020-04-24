package ee.proov.consumer.service.impl;

import ee.proov.common.StaticConf;
import ee.proov.common.model.kafka.AbstractKafkaMessage;
import ee.proov.common.model.kafka.PersonSocialRatingKafkaMessage;
import ee.proov.common.model.service.PersonDataDto;
import ee.proov.consumer.service.ConsumerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {

    private final KafkaTemplate kafkaTemplate;

    @Override
    public ResponseEntity savePersonDataTx(final PersonDataDto personDataDto) {
        try {
            sendMessageAsync(PersonSocialRatingKafkaMessage
                    .builder()
                    .personDataDto(personDataDto)
                    .someOtherImportantData(UUID.randomUUID())
                    .build());
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Unable to save person data", e);
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    private void sendMessageAsync(final AbstractKafkaMessage abstractKafkaMessage) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(StaticConf.KAFKA_TOPIC, abstractKafkaMessage);

        future.addCallback(new ListenableFutureCallback<>() {
            public void onSuccess(SendResult<String, String> result) {
                log.info("Sent message=[" + abstractKafkaMessage + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            public void onFailure(Throwable ex) {
                log.info("Unable to send message=[" + abstractKafkaMessage + "] due to : " + ex.getMessage());
            }
        });
    }

}
