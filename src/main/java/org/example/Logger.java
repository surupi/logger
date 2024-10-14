package org.example;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

@FunctionalInterface
public interface Logger {
    void log(String message);

    // Default methods for logging with various transformations
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
                .replace("o", "0")
                .replace("s", "5");
        log(leetMessage);
    }

    default void logInReverse(String message) {
        String reversed = new StringBuilder(message).reverse().toString();
        log(reversed);
    }

    default void logInColor(String message, String colorCode) {
        log(colorCode + message + ColorCodes.RESET);
    }

    default void logWithStyle(String message, String colorCode, String backgroundColor, String effect) {
        log(colorCode + backgroundColor + effect + message + ColorCodes.RESET);
    }

    default void logWithoutVowels(String message) {
        String messageWithoutVowels = message.replaceAll("[AEIOUaeiou]", "");
        log(messageWithoutVowels);
    }

    default void logInLanguage(String message, String languageCode) {
        String translatedMessage = translate(message, languageCode);
        log(translatedMessage);
    }

    default void logWithSentimentHighlight(String message) {
        String highlightedMessage = highlightSentiment(message);
        log(highlightedMessage);
    }

    default void logWithTimestamp(String message) {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        log("[" + timestamp + "] " + message);
    }

    default void logExecutionTime(Runnable action) {
        long start = System.currentTimeMillis();
        action.run();
        long end = System.currentTimeMillis();
        log("Execution took " + (end - start) + "ms.");
    }

    private String translate(String message, String languageCode) {
        Map<String, String> translationMap = new HashMap<>();
        translationMap.put("Hello", "Hola"); // Spanish
        translationMap.put("Goodbye", "Adios"); // Spanish
        translationMap.put("Yes", "Sí"); // Spanish
        translationMap.put("No", "No"); // Spanish
        translationMap.put("Thank you", "Gracias"); // Spanish
        translationMap.put("Hello", "Bonjour"); // French
        translationMap.put("Thank you", "Merci"); // French

        // Defaulting to English if the translation is unavailable
        String translatedWord = translationMap.getOrDefault(message, message);

        // Handling unsupported language codes
        if (!languageCode.equals("es") && !languageCode.equals("fr")) {
            return message + " (Translation unavailable)";
        }

        return translatedWord;
    }

    private String highlightSentiment(String message) {
        String[] positiveWords = { "good", "great", "happy", "wonderful", "fantastic", "love", "excellent", "amazing" };
        String[] negativeWords = { "bad", "sad", "hate", "terrible", "horrible", "awful", "worst", "angry" };

        boolean isPositive = false;
        boolean isNegative = false;

        for (String word : positiveWords) {
            if (message.toLowerCase().contains(word)) {
                isPositive = true;
                break;
            }
        }

        for (String word : negativeWords) {
            if (message.toLowerCase().contains(word)) {
                isNegative = true;
                break;
            }
        }

        // Highlight based on sentiment
        if (isPositive) {
            return ColorCodes.GREEN + message + ColorCodes.RESET; // Positive messages in green
        } else if (isNegative) {
            return ColorCodes.RED + message + ColorCodes.RESET; // Negative messages in red
        }

        return message; // Neutral messages remain unchanged
    }

    static Logger getDefaultLogger() {
        return message -> System.out.println(ColorCodes.RED + message + ColorCodes.RESET);
    }

    static Logger getLeetLogger() {
        return message -> {
            String leetMessage = message.replace("o", "0").replace("s", "5");
            System.out.println(leetMessage);
        };
    }

//    static Logger getCaesarCipherLogger(int shift) {
//        return message -> {
//            StringBuilder encoded = new StringBuilder();
//            for (char c : message.toCharArray()) {
//                if (Character.isLetter(c)) {
//                    char base = Character.isLowerCase(c) ? 'a' : 'A';
//                    encoded.append((char) ((c - base + shift) % 26 + base));
//                } else {
//                    encoded.append(c);
//                }
//            }
//            System.out.println(encoded.toString());
//        };
//    }

    static Logger getStyledLogger(String colorCode, String backgroundColor, String effect) {
        return message -> {
            System.out.println(colorCode + backgroundColor + effect + message + ColorCodes.RESET);
        };
    }
}
