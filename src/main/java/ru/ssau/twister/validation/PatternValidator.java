package ru.ssau.twister.validation;

import java.util.Objects;
import java.util.regex.Pattern;

public class PatternValidator implements Validator<String> {
    private static final String ERROR_MESSAGE = "Given string doesn't match pattern";

    private final Pattern pattern;
    private final String errorMessage;

    public PatternValidator(String regex, String errorMessage) {
        this.pattern = Pattern.compile(Objects.requireNonNull(regex));
        this.errorMessage = (errorMessage != null) ? errorMessage : ERROR_MESSAGE;
    }

    @Override
    public ValidationContext validate(String s) {
        if (pattern.matcher(s).matches()) {
            return new ValidationContext(true);
        } else {
            return new ValidationContext(false, errorMessage);
        }
    }
}
