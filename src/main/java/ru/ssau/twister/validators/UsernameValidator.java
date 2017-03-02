package ru.ssau.twister.validators;

import ru.ssau.twister.validation.PatternValidator;
import ru.ssau.twister.validation.ValidationContext;

public class UsernameValidator extends PatternValidator {
    public UsernameValidator() {
        super("^[a-zA-Z_0-9]{4,20}$", "Имя пользователя должно иметь длину от 4 до 20 символов " +
                "и содержать только латинские буквы, цифры и символы нижнего подчеркивания");
    }

    @Override
    public ValidationContext validate(String s) {
        if (s == null) {
            return new ValidationContext(false, "Имя пользователя не должно быть пустым");
        }

        return super.validate(s);
    }
}
