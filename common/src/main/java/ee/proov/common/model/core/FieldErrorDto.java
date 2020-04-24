package ee.proov.common.model.core;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(
        name = "FieldError",
        description = "Field error."
)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorDto {
    @Schema(description = "Field age.")
    private String field;
    @Schema(description = "Field error.")
    private String error;
}
