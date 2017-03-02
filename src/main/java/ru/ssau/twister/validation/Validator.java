package ru.ssau.twister.validation;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public interface Validator<T> {
    ValidationContext validate(T t);

    static <T> Validator<List<T>> and(List<Validator<? super T>> validators) {
        return ts -> {
            Stream<ValidationContext> validationContextStream = IntStream.range(0, ts.size())
                    .mapToObj(i -> validators.get(i).validate(ts.get(i)));

            boolean isValid = validationContextStream.allMatch(ValidationContext::isValid);

            Set<String> messages = validationContextStream.filter(vc -> vc.getMessages() != null)
                    .flatMap(vc -> vc.getMessages().stream()).collect(Collectors.toSet());

            return new ValidationContext(isValid, messages);
        };
    }
}
