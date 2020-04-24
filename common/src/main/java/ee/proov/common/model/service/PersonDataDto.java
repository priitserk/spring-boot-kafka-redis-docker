package ee.proov.common.model.service;

import com.fasterxml.jackson.annotation.JsonInclude;
import ee.proov.common.enums.FieldErrorCodeEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(
        name = "PersonDataDto",
        description = "Defines and declares data and types required for submitting person data"
)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonDataDto {

    @Schema(description = "Person first name", required = true)
    @NotBlank(message = FieldErrorCodeEnum.Codes.BLANK)
    @Size(min = 1, max = 100, message = FieldErrorCodeEnum.Codes.SIZE)
    private String firstName;

    @Schema(description = "Person last name", required = true)
    @NotBlank(message = FieldErrorCodeEnum.Codes.BLANK)
    @Size(min = 1, max = 100, message = FieldErrorCodeEnum.Codes.SIZE)
    private String lastName;

    @Schema(description = "Person age. Allowed range: 18-70", required = true)
    @NotNull
    @Range(min = 18, max = 70)
    private int age;
}
