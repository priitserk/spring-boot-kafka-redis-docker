package ee.proov.daemon.service;

import ee.proov.common.enums.ServiceStatusEnum;
import ee.proov.common.model.service.SystemStatusDto;
import ee.proov.daemon.DaemonServiceUnitTest;
import ee.proov.daemon.service.generic.impl.MonitoringServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest(classes = MonitoringServiceImpl.class)
public class MonitoringServiceImplTest extends DaemonServiceUnitTest {
    private static final String TEST_STRING = "TEST_VALUE";

    @Autowired
    private MonitoringServiceImpl monitoringServiceImpl;

    @MockBean
    private BuildProperties buildProperties;

    @Test
    public void whenGetSystemStatusInfo_thenShouldReturnStatusInfo() {
        // given
        Instant testInstant = Instant.now();
        given(buildProperties.getName()).willReturn(TEST_STRING);
        given(buildProperties.getVersion()).willReturn(TEST_STRING);
        given(buildProperties.getTime()).willReturn(testInstant);

        // when
        SystemStatusDto systemStatusDto = monitoringServiceImpl.getSystemStatusInfo();

        // then
        assertThat(systemStatusDto).isNotNull();
        assertThat(systemStatusDto.getStatus()).isEqualTo(ServiceStatusEnum.UP);
        assertThat(systemStatusDto.getName()).isEqualTo(TEST_STRING);
        assertThat(systemStatusDto.getVersion()).isEqualTo(TEST_STRING);
        assertThat(systemStatusDto.getBuildTime()).isEqualTo(testInstant);
        assertThat(systemStatusDto.getCurrentTime()).isNotNull();
        assertThat(systemStatusDto.getStartTime()).isNotNull();
    }
}
