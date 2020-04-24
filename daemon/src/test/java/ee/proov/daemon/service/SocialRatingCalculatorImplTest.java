package ee.proov.daemon.service;

import ee.proov.daemon.DaemonServiceUnitTest;
import ee.proov.daemon.service.generic.impl.SocialRatingCalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = SocialRatingCalculatorServiceImpl.class)
public class SocialRatingCalculatorImplTest extends DaemonServiceUnitTest {

    @Autowired
    private SocialRatingCalculatorServiceImpl socialRatingCalculatorServiceImpl;

    @Test
    public void calculatePersonSocialRatingTest() {
        final double seed = socialRatingCalculatorServiceImpl.calculatePersonSocialRating(20);
        assertThat(seed).isEqualTo(16);
    }

}
