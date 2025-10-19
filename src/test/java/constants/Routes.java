package constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public class Routes {

    public static String withId(String template, long id) {
        return template.replace("{id}", Long.toString(id));
    }

    @Getter
    @RequiredArgsConstructor
    public enum Employee {
        BASE("/employees"),
        BY_ID("/employees/{id}"),
        PROFILE("/employees/profile"),
        CURRENT("/employees/current"),
        ANNIVERSARIES("/employees/anniversaries");

        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Department {
        BASE("/departments");

        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum TimeLog {
        BASE("/time-logs"),
        BY_ID("/time-logs/{id}");

        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Ept {
        CURRENT("/ept/current"),
        BY_ID("/ept/{id}");

        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Health {
        HEALTH("/health");

        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Auth {
        LOGIN("/api/auth/login"),
        LOGOUT("/api/auth/logout");
        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Stack {
        BASE("/stacks");
        private final String path;
    }

    @Getter
    @RequiredArgsConstructor
    public enum Position {
        BASE("/positions");
        private final String path;
    }
}