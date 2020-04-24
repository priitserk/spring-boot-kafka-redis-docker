package ee.proov.consumer.resource.v1;

import ee.proov.common.model.service.PersonDataDto;
import ee.proov.consumer.service.ConsumerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("v1/person-data")
@RequiredArgsConstructor
@Tag(name = "Person Data collector, version 1", description = "Person Data collector API")
public class PersonResource {
    final ConsumerService consumerService;

    @Operation(summary = "Collect and process person data")
    @PostMapping(value = "/collect", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    public ResponseEntity collect(@Valid @NotNull @RequestBody final PersonDataDto personDataDto) {
        return consumerService.savePersonDataTx(personDataDto);
    }
}
