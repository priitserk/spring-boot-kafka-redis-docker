package ee.proov.daemon.service.generic;

import ee.proov.common.model.service.SystemStatusDto;

public interface MonitoringService {
    SystemStatusDto getSystemStatusInfo();
}
