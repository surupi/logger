import org.example.ColorCodes;
import org.example.Logger;
import org.example.Emoji;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class LoggerTest {
    public static void main(String[] args) {
        Logger redlogger = Logger.getRedDefaultLogger();
        redlogger.log("This is a red message!");

        Logger logger = Logger.getDefaultLogger();

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
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // Multi-Language Logging
        logger.logInLanguage("Hello", "en");
        logger.logInLanguage("Goodbye", "es");
        logger.logInLanguage("Unknown", "fr");
        logger.logInLanguage("Hello", "de");

        // Sentiment-Based Logging
        logger.logWithSentimentHighlight("This is a fantastic day!");
        logger.logWithSentimentHighlight("This is a terrible mistake.");
        logger.logWithSentimentHighlight("This is just a normal message.");
        logger.logWithSentimentHighlight("bad, good");

        Map<String, Object> logData = new HashMap<>();
        logData.put("message", "Hello world!");
        logData.put("level", "INFO");
        logData.put("timestamp", "2024-10-06T14:32:00Z");
        logger.logAsJson(logData);

        System.setProperty("app.environment", "development");
        Logger envlogger = Logger.getEnvironmentAwareLogger();
        envlogger.log("User login attempt successful.");
        envlogger.log("Loading user preferences...");

        System.setProperty("app.environment", "production");
        Logger plogger = Logger.getEnvironmentAwareLogger();
        plogger.log("User login attempt successful.");
        plogger.log("Loading user preferences...");

        // Emoji Logger
        Logger emojiLogger = Logger.getDefaultLogger();
        emojiLogger.logWithEmoji("You are correct!", Emoji.CORRECT);
        emojiLogger.logWithEmoji("This is an error", Emoji.ERROR);

        // HTML/CSS Logger
        Logger htmlLogger = Logger.getDefaultLogger();
        htmlLogger.logWithHtmlStyle("Hello world!", "color: blue; font-weight: bold");

        // File-Based Logger
        Logger fileLogger = Logger.getFileLogger("log.txt");
        fileLogger.log("This is a file-based log message.");

        // Log Categorization
        Logger categorizedLogger = Logger.getDefaultLogger();
        categorizedLogger.logWithCategory("Unauthorized access attempt detected", "SECURITY");

        logger.logAndHighlightPalindromes("Radar detected a level of civic level responsibility", ColorCodes.RED);

        // Testing Base64 Encoding
        logger.logBase64Encoded("Hello world!"); // SGVsbG8gd29ybGQh

        // Testing Base64 Decoding
        logger.logBase64Decoded("SGVsbG8gd29ybGQh"); // Hello world!

        Logger mlogger = message -> System.out.println("Log: " + message);
        mlogger.log("Hello World");
        mlogger.logInMorseCode("Hello World");

        logger.logInPigLatin("hello world");
        logger.logInPigLatin("This is an apple");
        logger.logInPigLatin("Quick brown fox jumps over the lazy dog");
    }
}