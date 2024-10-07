package org.example;
import java.util.function.Function;

@FunctionalInterface
public interface Logger {
    void log(String message);
    
    default void logWithPrefix(String prefix, String message) {
        log(prefix + ": " + message);
    }

    default void logInUppercase(String message) {
        log(message.toUpperCase());
    }

    default void logInColor(String message, String colorCode) {
        log(colorCode + message + "\u001B[0m");
    }

    default void logWithoutVowels(String message) {
        String messageWithoutVowels = message.replaceAll("[AEIOUaeiou]", "");
        log(messageWithoutVowels);
    }

    static Logger getDefaultLogger() {
        return message -> System.out.println("\u001B[31m" + message + "\u001B[0m");
    }

}
