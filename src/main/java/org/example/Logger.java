package org.example;
import java.util.function.Function;

@FunctionalInterface
public interface Logger {
    void log(String message);

    // Default Methods
    default void logWithPrefix(String prefix, String message) {
        log(prefix + ": " + message);
    }

    default void logInUppercase(String message) {
        log(message.toUpperCase());
    }

    default void logWithTransformation(Function<String, String> transformer, String message) {
        log(transformer.apply(message));
    }

    default void logInLeetSpeak(String message) {
        String leetMessage = message
                .replace("e", "3")
                .replace("a", "4")
                .replace("t", "7")
                .replace("o", "0")
                .replace("s", "5");
        log(leetMessage);
    }

    default void logWithCaesarCipher(String message, int shift) {
        StringBuilder ciphered = new StringBuilder();
        for (char c : message.toCharArray()) {
            if (Character.isLetter(c)) {
                char shifted = (char) (c + shift);
                ciphered.append(shifted);
            } else {
                ciphered.append(c);
            }
        }
        log(ciphered.toString());
    }

    default void logInReverse(String message) {
        String reversed = new StringBuilder(message).reverse().toString();
        log(reversed);
    }

    default void logInColor(String message, String colorCode) {
        log(colorCode + message + "\u001B[0m"); // Reset color
    }

    default void logWithStyle(String message, String colorCode, String backgroundColor, String effect) {
        log(colorCode + backgroundColor + effect + message + "\u001B[0m"); // Reset color and effect
    }

    default void logWithRepeatedMessage(String message, int repeatCount) {
        for (int i = 0; i < repeatCount; i++) {
            log(message);
        }
    }

    default void logWithoutVowels(String message) {
        String messageWithoutVowels = message.replaceAll("[AEIOUaeiou]", "");
        log(messageWithoutVowels);
    }

    static Logger getDefaultLogger() {
        return message -> System.out.println("\u001B[31m" + message + "\u001B[0m");
    }

}
