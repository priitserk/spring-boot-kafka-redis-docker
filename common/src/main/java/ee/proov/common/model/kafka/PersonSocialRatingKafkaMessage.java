package ee.proov.common.model.kafka;

import ee.proov.common.model.service.PersonDataDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonSocialRatingKafkaMessage extends AbstractKafkaMessage {
    private PersonDataDto personDataDto;
    private UUID someOtherImportantData;
}
