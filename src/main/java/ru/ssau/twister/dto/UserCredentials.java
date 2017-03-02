package ru.ssau.twister.dto;

import java.io.Serializable;
import java.util.*;
import java.util.regex.Pattern;

public class UserCredentials implements Serializable {
    private static final String PATTERN = "^[a-zA-Z0-9_]{4,20}$";

    private String username;
    private String password;

    public UserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getErrorMessage() {
        Pattern pattern = Pattern.compile(PATTERN);
        StringJoiner joiner = new StringJoiner("<br>");

        if (username == null || !pattern.matcher(username).matches()) {
            joiner.add("Имя пользователя должно иметь длину от 4 до 20 символов " +
                    "и содержать только латинские буквы, цифры и символы нижнего подчеркивания");
        }

        if (password == null || !pattern.matcher(password).matches()) {
            joiner.add("Пароль должен иметь длину от 4 до 20 символов " +
                    "и содержать только латинские буквы, цифры и символы нижнего подчеркивания");
        }

        return joiner.length() == 0 ? null : joiner.toString();
    }

    @Override
    public String toString() {
        return "UserCredentials{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)  {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        } else {
            UserCredentials that = (UserCredentials) o;
            return Objects.equals(username, that.username) &&
                    Objects.equals(password, that.password);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}
