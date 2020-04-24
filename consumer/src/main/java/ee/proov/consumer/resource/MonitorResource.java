package ee.proov.consumer.resource;

import ee.proov.common.model.service.SystemStatusDto;
import ee.proov.consumer.service.MonitoringService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Tag(name = "Service Monitoring API", description = "Consumer Service Monitoring API")
public class MonitorResource {
    private final MonitoringService monitoringService;

    @Operation(summary = "Returns Consumer Service status")
    @GetMapping(produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "200",
            description = "Consumer Service Status Info"
    )
    public ResponseEntity<SystemStatusDto> heartBeat() {
        return new ResponseEntity<>(monitoringService.getSystemStatusInfo(), HttpStatus.OK);
    }
}
