package ru.ssau.twister.validation;

import java.util.*;

public class ValidationContext {
    private final boolean isValid;
    private final Set<String> messages;

    public ValidationContext(boolean isValid, Collection<String> messages) {
        this.isValid = isValid;
        this.messages = new HashSet<>(messages);
    }

    public ValidationContext(boolean isValid, String... messages) {
        this.isValid = isValid;
        this.messages = new HashSet<>(Arrays.asList(messages));
    }

    public boolean isValid() {
        return isValid;
    }

    public Set<String> getMessages() {
        if (isValid()) {
            return null;
        } else {
            return messages;
        }
    }
}
