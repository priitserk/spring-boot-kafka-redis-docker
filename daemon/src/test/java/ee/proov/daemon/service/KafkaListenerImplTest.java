package ee.proov.daemon.service;

import ee.proov.common.model.kafka.AbstractKafkaMessage;
import ee.proov.common.model.kafka.PersonSocialRatingKafkaMessage;
import ee.proov.common.model.service.PersonDataDto;
import ee.proov.daemon.service.generic.impl.SocialRatingCalculatorServiceImpl;
import ee.proov.daemon.service.kafka.KafkaListenerImpl;
import ee.proov.daemon.service.redis.RedisService;
import ee.proov.daemon.utils.TransformUtils;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@EmbeddedKafka(partitions = 1, topics = {"testTopic"})
@SpringBootTest(classes = {KafkaListenerImpl.class, SocialRatingCalculatorServiceImpl.class})
public class KafkaListenerImplTest {
    private static final String TEST_TOPIC = "testTopic";

    @Autowired
    EmbeddedKafkaBroker embeddedKafkaBroker;

    @Autowired
    SocialRatingCalculatorServiceImpl socialRatingCalculatorServiceImpl;

    @MockBean
    RedisService redisService;

    @Test
    public void testReceivingKafkaEvents() {
        Consumer<Integer, AbstractKafkaMessage> consumer = configureConsumer();
        Producer<Integer, AbstractKafkaMessage> producer = configureProducer();

        producer.send(new ProducerRecord<>(TEST_TOPIC, 123,
                new PersonSocialRatingKafkaMessage(
                        PersonDataDto.builder().age(10).firstName("priit").lastName("serk").build(),
                        UUID.randomUUID()
                )
        ));

        ConsumerRecord<Integer, AbstractKafkaMessage> singleRecord = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);
        assertThat(singleRecord).isNotNull();
        assertThat(singleRecord.key()).isEqualTo(123);
        assertThat(singleRecord.value()).isInstanceOf(PersonSocialRatingKafkaMessage.class);

        final PersonSocialRatingKafkaMessage personSocialRatingKafkaMessage = (PersonSocialRatingKafkaMessage) singleRecord.value();
        assertThat((TransformUtils.PersonSocialMessageToRedisObject(
                personSocialRatingKafkaMessage.getPersonDataDto(),
                socialRatingCalculatorServiceImpl.calculatePersonSocialRating(personSocialRatingKafkaMessage.getPersonDataDto().getAge())
        )).toString()).isEqualTo("PersonSocialScore(firstName=priit, lastName=serk, socialScore=8.0)");

        consumer.close();
        producer.close();
    }

    private Consumer<Integer, AbstractKafkaMessage> configureConsumer() {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testGroup", "true", embeddedKafkaBroker);

        consumerProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProps.put(JsonDeserializer.TRUSTED_PACKAGES, "ee.proov.common.model.kafka");

        Consumer<Integer, AbstractKafkaMessage> consumer = new DefaultKafkaConsumerFactory<Integer, AbstractKafkaMessage>(consumerProps).createConsumer();
        consumer.subscribe(Collections.singleton(TEST_TOPIC));
        return consumer;
    }

    private Producer<Integer, AbstractKafkaMessage> configureProducer() {
        Map<String, Object> producerProps = new HashMap<>(KafkaTestUtils.producerProps(embeddedKafkaBroker));
        producerProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<Integer, AbstractKafkaMessage>(producerProps).createProducer();
    }
}
