import org.example.ColorCodes;
import org.example.Logger;

import java.util.function.Function;
import java.util.function.Predicate;

public class LoggerTest {
    public static void main(String[] args) {
        Logger logger = Logger.getDefaultLogger();

        logger.log("This is a red message!");
        logger.logInUppercase("this is an uppercase message.");
        logger.logInLeetSpeak("leet speak message");
        logger.logWithCaesarCipher("Hello World", 3);
        logger.logInReverse("Reverse this message");
        logger.logInColor("This is green text", ColorCodes.GREEN);
        logger.logWithStyle("This is a bold green message on a blue background", ColorCodes.GREEN, ColorCodes.BLUE_BG, ColorCodes.BOLD);
        logger.logWithRepeatedMessage("Repeated message", 3);
        logger.logWithoutVowels("Hello, how are you?");


        Function<String, String> transformation = message -> {
            return message.replace("Hello", "Hi");
        };
        Logger transformationLogger = (message) -> {
            String transformedMessage = transformation.apply(message);
            System.out.println(transformedMessage);
        };
        transformationLogger.log("Hello world!");

    }
}