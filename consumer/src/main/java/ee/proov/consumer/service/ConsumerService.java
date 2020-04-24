package ee.proov.consumer.service;


import ee.proov.common.model.service.PersonDataDto;
import org.springframework.http.ResponseEntity;

public interface ConsumerService {
    ResponseEntity savePersonDataTx(final PersonDataDto personDataDto);
}
