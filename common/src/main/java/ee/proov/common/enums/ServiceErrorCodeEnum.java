package ee.proov.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ServiceErrorCodeEnum {
    SYSTEM_ERROR("system_error"),
    INVALID_REQUEST("invalid_request"),
    DUPLICATE_DECLARATION("duplicate_declaration");

    private final String code;
}
