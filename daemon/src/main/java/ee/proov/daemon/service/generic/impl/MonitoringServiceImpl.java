package ee.proov.daemon.service.generic.impl;

import ee.proov.common.enums.ServiceStatusEnum;
import ee.proov.common.model.service.SystemStatusDto;
import ee.proov.daemon.service.generic.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MonitoringServiceImpl implements MonitoringService {
    private final BuildProperties buildProperties;

    private Instant startupTime;

    @EventListener(ApplicationReadyEvent.class)
    public void onServerStartUp() {
        startupTime = Instant.now();
    }

    @Override
    public SystemStatusDto getSystemStatusInfo() {
        return SystemStatusDto.builder()
                .status(ServiceStatusEnum.UP)
                .version(buildProperties.getVersion())
                .name(buildProperties.getName())
                .buildTime(buildProperties.getTime())
                .currentTime(Instant.now())
                .startTime(startupTime)
                .build();
    }
}
