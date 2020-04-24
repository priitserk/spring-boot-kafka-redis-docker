package ee.proov.common.model.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "ErrorResponse",
        description = "Consent Service error response."
)
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponseDto<T> {
    @Schema(description = "Error status.")
    private int status;
    @Schema(description = "Error code.")
    private String code;
    @Schema(description = "Error message.")
    private String message;
    @Schema(description = "Error additional details.")
    private T details;
}
