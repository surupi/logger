package org.example;

import java.util.function.Function;

public class DemoFunctionalInterface {
    public static void main(String[] args) {
        // Uppercase logger using lambda expression
        Logger upperCaseLogger = message -> System.out.println(message.toUpperCase());

        // Log a simple message
        upperCaseLogger.log("This is a simple message");

        // Log a prefixed message
        upperCaseLogger.logWithPrefix("INFO", "This is a prefixed message");

        // Default red logger
        Logger redLogger = Logger.getDefaultLogger();
        redLogger.log("This is a default logger message");

        // truncate the message to 10 characters
        Function<String, String> truncateTo10 = message -> message.length() > 10 ? message.substring(0, 10) + "..." : message;

        // Log a transformed message using the truncation function
        upperCaseLogger.logWithTransformation(truncateTo10, "This message will be truncated");
    }
}
