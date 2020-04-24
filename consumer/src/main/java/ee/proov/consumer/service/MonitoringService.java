package ee.proov.consumer.service;


import ee.proov.common.model.service.SystemStatusDto;

public interface MonitoringService {
    SystemStatusDto getSystemStatusInfo();
}
