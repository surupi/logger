import org.example.ColorCodes;
import org.example.Logger;

import java.util.function.Function;
import java.util.function.Predicate;

public class LoggerTest {
    public static void main(String[] args) {
        // Simple Logging
        Logger logger = Logger.getDefaultLogger();
        logger.log("This is a red message!");

        // Leetspeak Logging
        Logger leetLogger = Logger.getLeetLogger();
        leetLogger.log("Hello world!");

        // Caesar Cipher Logging
//        Logger caesarLogger = Logger.getCaesarCipherLogger(3);
//        caesarLogger.log("attack at dawn");

        // Colored and Styled Logging
        Logger styledLogger = Logger.getStyledLogger(ColorCodes.GREEN, ColorCodes.BLUE_BG, ColorCodes.BOLD);
        styledLogger.log("This is a bold green message on a blue background");

        // Using Function for message transformation
        Function<String, String> transformation = message -> message.replace("Hello", "Hi");
        Logger transformationLogger = (message) -> System.out.println(transformation.apply(message));
        transformationLogger.log("Hello world!");
//
//        // Using Predicate for message filtering
//        Predicate<String> filter = message -> !message.contains("error");
//        String messageToLog = "This is a normal message.";
//        if (filter.test(messageToLog)) {
//            logger.log(messageToLog);
//        }
//
//        // Using composed transformations
//        Function<String, String> upperCase = message -> message.toUpperCase();
//        Function<String, String> leet = message -> message
//                .replace("o", "0")
//                .replace("s", "5")
//                .replace("e", "3");
//        Function<String, String> combinedTransformation = upperCase.andThen(leet);
//        logger.logWithTransformation(combinedTransformation, "Hello leetspeak");

        // Timestamped logging
        logger.logWithTimestamp("This message has a timestamp.");

        // Log execution time
        logger.logExecutionTime(() -> {
            try {
                Thread.sleep(150); // Sleep for 150ms
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Multi-Language Logging
        logger.logInLanguage("Hello", "en"); // Output: Hello
        logger.logInLanguage("Goodbye", "es"); // Output: Adios
        logger.logInLanguage("Unknown", "fr"); // Output: Unknown (Translation unavailable)
        logger.logInLanguage("Hello", "de"); // Output: Hello (Translation unavailable)

        // Sentiment-Based Logging
        logger.logWithSentimentHighlight("This is a fantastic day!"); // Output: in green
        logger.logWithSentimentHighlight("This is a terrible mistake."); // Output: in red
//        logger.logWithSentimentHighlight("This is just a normal message."); // Neutral, no change
    }
}