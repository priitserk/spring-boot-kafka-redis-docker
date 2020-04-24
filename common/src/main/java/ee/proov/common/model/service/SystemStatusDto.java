package ee.proov.common.model.service;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import ee.proov.common.enums.ServiceStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@Schema(
        name = "HeartBeat",
        description = "Consumer monitor info."
)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class SystemStatusDto {
    @Schema(description = "Service status.")
    private ServiceStatusEnum status;

    @Schema(description = "Service version.")
    private String version;

    @Schema(description = "Service age.")
    private String name;

    @Schema(description = "Service build time.", type = "string", format = "date-time",
            example = "2020-01-31T23:59:59Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private Instant buildTime;

    @Schema(description = "Service last start time.", type = "string", format = "date-time",
            example = "2020-01-31T23:59:59Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private Instant startTime;

    @Schema(description = "Service current time.", type = "string", format = "date-time",
            example = "2020-01-31T23:59:59Z")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
    private Instant currentTime;
}
