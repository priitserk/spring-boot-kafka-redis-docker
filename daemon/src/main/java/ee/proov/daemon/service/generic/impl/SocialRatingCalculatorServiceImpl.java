package ee.proov.daemon.service.generic.impl;

import ee.proov.daemon.service.generic.SocialRatingCalculatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SocialRatingCalculatorServiceImpl implements SocialRatingCalculatorService {

    @Value(value = "${calculation.base.seed}")
    private double calculationBaseSeed;

    public double calculatePersonSocialRating(final int personAge) {
        return calculationBaseSeed * personAge;
    }

}
