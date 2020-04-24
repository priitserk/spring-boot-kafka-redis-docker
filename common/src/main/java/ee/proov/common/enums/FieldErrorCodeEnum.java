package ee.proov.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FieldErrorCodeEnum {
    NULL(Codes.NULL),
    BLANK(Codes.BLANK),
    SIZE(Codes.SIZE),
    IN_PAST(Codes.IN_PAST),
    NEGATIVE(Codes.NEGATIVE);

    public static class Codes {
        public static final String NULL = "null";
        public static final String BLANK = "blank";
        public static final String SIZE = "size";
        public static final String IN_PAST = "in_past";
        public static final String NEGATIVE = "negative";
    }

    private final String code;
}
