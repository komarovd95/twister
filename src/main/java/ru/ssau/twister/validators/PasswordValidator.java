package ru.ssau.twister.validators;

import ru.ssau.twister.validation.PatternValidator;
import ru.ssau.twister.validation.ValidationContext;

public class PasswordValidator extends PatternValidator {
    public PasswordValidator() {
        super("^[a-zA-Z_0-9]{4,20}$", "Пароль должен иметь длину от 4 до 20 символов " +
                "и содержать только латинские буквы, цифры и символы нижнего подчеркивания");
    }

    @Override
    public ValidationContext validate(String s) {
        if (s == null) {
            return new ValidationContext(false, "Пароль не должен быть пустым");
        }

        return super.validate(s);
    }
}
