package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import j2html.TagCreator;

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
        Set<String> positiveWords = new HashSet<>();
        Set<String> negativeWords = new HashSet<>();

        // Define positive and negative words
        positiveWords.add("good");
        positiveWords.add("great");
        positiveWords.add("happy");
        positiveWords.add("fantastic");

        negativeWords.add("bad");
        negativeWords.add("sad");
        negativeWords.add("hate");
        negativeWords.add("terrible");

        String lowerCaseMessage = message.toLowerCase();
        boolean isPositive = positiveWords.stream().anyMatch(lowerCaseMessage::contains);
        boolean isNegative = negativeWords.stream().anyMatch(lowerCaseMessage::contains);

        if (isPositive) {
            log(ColorCodes.GREEN + message + ColorCodes.RESET);
        } else if (isNegative) {
            log(ColorCodes.RED + message + ColorCodes.RESET);
        } else {
            log(message);
        }
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
        translationMap.put("Hello", "Hola");
        translationMap.put("Goodbye", "Adios");
        translationMap.put("Yes", "Sí");
        translationMap.put("No", "No");
        translationMap.put("Thank you", "Gracias");
        translationMap.put("Hello", "Bonjour");
        translationMap.put("Thank you", "Merci");

        // Defaulting to English if the translation is unavailable
        String translatedWord = translationMap.getOrDefault(message, message);

        // Handling unsupported language codes
        if (!languageCode.equals("es") && !languageCode.equals("fr")) {
            return message + " (Translation unavailable)";
        }

        return translatedWord;
    }

    static Logger getRedDefaultLogger() {
        return message -> System.out.println(ColorCodes.RED + message + ColorCodes.RESET);
    }

    static Logger getDefaultLogger() {
        return System.out::println;
    }

    static Logger getLeetLogger() {
        return message -> {
            String leetMessage = message.replace("o", "0").replace("s", "5");
            System.out.println(leetMessage);
        };
    }

    static Logger getCaesarCipherLogger(int shift) {
        return message -> {
            StringBuilder encoded = new StringBuilder();
            for (char c : message.toCharArray()) {
                if (Character.isLetter(c)) {
                    char base = Character.isLowerCase(c) ? 'a' : 'A';
                    encoded.append((char) ((c - base + shift) % 26 + base));
                } else {
                    encoded.append(c);
                }
            }
            System.out.println(encoded.toString());
        };
    }

    static Logger getStyledLogger(String colorCode, String backgroundColor, String effect) {
        return message -> {
            System.out.println(colorCode + backgroundColor + effect + message + ColorCodes.RESET);
        };
    }

    default void logAsJson(Object data) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(data);
            log(json);
        } catch (Exception e) {
//            log("Failed to log as JSON: " + e.getMessage());
            log(data.toString());
        }
    }

    static Logger getEnvironmentAwareLogger() {
        String environment = System.getProperty("app.environment", "development").toLowerCase();

        if ("production".equals(environment)) {
            return message -> System.out.println("[INFO] " + message);
        } else {
            return message -> {
                String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                System.out.println("[DEV] " + timestamp + " - " + message);
            };
        }
    }

    default void logWithDetails(String message) {
        log("Detailed Log: " + message);
    }

    default void logWithEmoji(String message, Emoji emoji) {
        log(emoji.getSymbol() + " " + message);
    }

    default void logWithHtmlStyle(String message, String cssStyles) {
        String htmlMessage = TagCreator.span(message).withStyle(cssStyles).render();
        log(htmlMessage);
    }

    static Logger getFileLogger(String filePath) {
        return message -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
                writer.write(message);
                writer.newLine();
            } catch (IOException e) {
                System.err.println("Failed to log to file: " + e.getMessage());
            }
        };
    }

    default void logWithCategory(String message, String category) {
        log("[" + category.toUpperCase() + "] " + message);
    }

    default void logAndHighlightPalindromes(String message, String highlightColor) {
        // Pattern to find words, ignoring punctuation
        Pattern wordPattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = wordPattern.matcher(message);

        StringBuilder highlightedMessage = new StringBuilder();
        int lastIndex = 0;

        while (matcher.find()) {
            String word = matcher.group();
            String cleanWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();

            if (isPalindrome(cleanWord)) {
                // Highlight the palindrome
                highlightedMessage.append(message, lastIndex, matcher.start())
                        .append(highlightColor).append(word).append(ColorCodes.RESET);
                lastIndex = matcher.end();
            }
        }

        // Append the rest of the message
        highlightedMessage.append(message.substring(lastIndex));

        // Log the final message
        log(highlightedMessage.toString());
    }

    default void logBase64Encoded(String message) {
        String encodedMessage = java.util.Base64.getEncoder().encodeToString(message.getBytes());
        log(encodedMessage);
    }

    default void logBase64Decoded(String message) {
        byte[] decodedBytes = java.util.Base64.getDecoder().decode(message);
        String decodedMessage = new String(decodedBytes);
        log(decodedMessage);
    }

    private boolean isPalindrome(String word) {
        return word.length() > 1 && word.equals(new StringBuilder(word).reverse().toString());
    }

    default void logInMorseCode(String message) {
        Map<Character, String> morseCodeMap = createMorseCodeMap();
        StringBuilder morseMessage = new StringBuilder();

        message = message.toLowerCase();

        for (char ch : message.toCharArray()) {
            if (ch == ' ') {
                morseMessage.append("/ ");
            } else if (morseCodeMap.containsKey(ch)) {
                morseMessage.append(morseCodeMap.get(ch)).append(" ");
            } else {
                System.out.println("Character '" + ch + "' could not be translated.");
            }
        }
        log(morseMessage.toString().trim());
    }

    private Map<Character, String> createMorseCodeMap() {
        Map<Character, String> morseCodeMap = new HashMap<>();
        morseCodeMap.put('a', ".-");
        morseCodeMap.put('b', "-...");
        morseCodeMap.put('c', "-.-.");
        morseCodeMap.put('d', "-..");
        morseCodeMap.put('e', ".");
        morseCodeMap.put('f', "..-.");
        morseCodeMap.put('g', "--.");
        morseCodeMap.put('h', "....");
        morseCodeMap.put('i', "..");
        morseCodeMap.put('j', ".---");
        morseCodeMap.put('k', "-.-");
        morseCodeMap.put('l', ".-..");
        morseCodeMap.put('m', "--");
        morseCodeMap.put('n', "-.");
        morseCodeMap.put('o', "---");
        morseCodeMap.put('p', ".--.");
        morseCodeMap.put('q', "--.-");
        morseCodeMap.put('r', ".-.");
        morseCodeMap.put('s', "...");
        morseCodeMap.put('t', "-");
        morseCodeMap.put('u', "..-");
        morseCodeMap.put('v', "...-");
        morseCodeMap.put('w', ".--");
        morseCodeMap.put('x', "-..-");
        morseCodeMap.put('y', "-.--");
        morseCodeMap.put('z', "--..");
        morseCodeMap.put('0', "-----");
        morseCodeMap.put('1', ".----");
        morseCodeMap.put('2', "..---");
        morseCodeMap.put('3', "...--");
        morseCodeMap.put('4', "....-");
        morseCodeMap.put('5', ".....");
        morseCodeMap.put('6', "-....");
        morseCodeMap.put('7', "--...");
        morseCodeMap.put('8', "---..");
        morseCodeMap.put('9', "----.");
        morseCodeMap.put('.', ".-.-.-");
        morseCodeMap.put(',', "--..--");
        morseCodeMap.put('?', "..--..");
        morseCodeMap.put('!', "-.-.--");
        return morseCodeMap;
    }

    default void logInPigLatin(String message) {
        StringBuilder pigLatinMessage = new StringBuilder();

        // Split the message while keeping spaces intact
        String[] words = message.split("(?<=\\s)|(?=\\s)"); // Regex to preserve spaces

        for (String word : words) {
            if (word.matches("[a-zA-Z]+")) { // Check if the token is a word
                pigLatinMessage.append(convertToPigLatin(word));
            } else {
                pigLatinMessage.append(word); // Preserve non-word characters
            }
        }

        log(pigLatinMessage.toString().trim()); // Log the transformed message
    }

    private String convertToPigLatin(String word) {
        char firstChar = word.charAt(0);
        String lowerCaseWord = word.toLowerCase();

        if (isVowel(firstChar)) {
            return word + "way"; // Append "way" for words starting with a vowel
        } else {
            // Handle consonants
            int index = 0;
            while (index < lowerCaseWord.length() && !isVowel(lowerCaseWord.charAt(index))) {
                index++;
            }

            String consonantCluster = word.substring(0, index);
            String pigLatinWord = word.substring(index) + consonantCluster + "ay";
            // Preserve the capitalization of the first letter
            if (Character.isUpperCase(firstChar)) {
                pigLatinWord = Character.toUpperCase(pigLatinWord.charAt(0)) + pigLatinWord.substring(1).toLowerCase();
            }
            return pigLatinWord;
        }
    }

    private boolean isVowel(char c) {
        return "aeiouAEIOU".indexOf(c) != -1;
    }

    default void logWithRot13(String message) {
        StringBuilder rot13Message = new StringBuilder();
        for (char ch : message.toCharArray()) {
            if (Character.isLetter(ch)) {
                char base = Character.isLowerCase(ch) ? 'a' : 'A';
                ch = (char) ((ch - base + 13) % 26 + base);
            }
            rot13Message.append(ch);
        }
        log(rot13Message.toString());
    }

    default void logPalindromeWords(String message) {
        String[] words = message.split("\\s+");
        String palindromes = Arrays.stream(words)
                .filter(word -> isPalindrome(word))
                .collect(Collectors.joining(" "));
        log(palindromes);
    }

    default void logWordFrequency(String message) {
        String[] words = message.split("\\s+");
        Map<String, Long> frequencyMap = Arrays.stream(words)
                .collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        String result = frequencyMap.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        log(result);
    }

    default void logWithLengthScrambledWords(String message) {
        String[] words = message.split("\\s+");
        Map<Integer, List<String>> lengthGroups = new TreeMap<>(); // Use TreeMap to maintain natural order by length

        // Group words by their lengths
        for (String word : words) {
            int length = word.length();
            lengthGroups.computeIfAbsent(length, k -> new ArrayList<>()).add(word);
        }

        StringBuilder scrambledMessage = new StringBuilder();
        for (List<String> group : lengthGroups.values()) {
            Collections.shuffle(group); // Shuffle the order of words within the group
            scrambledMessage.append(String.join(" ", group)).append(" ");
        }

        log(scrambledMessage.toString().trim());
    }

    default void logWithConditionalReversedWords(String message, int lengthThreshold) {
        String[] words = message.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            if (word.length() > lengthThreshold) {
                result.append(new StringBuilder(word).reverse().toString()).append(" ");
            } else {
                result.append(word).append(" ");
            }
        }

        log(result.toString().trim());
    }

    default void logWithCharacterScrambledWords(String message) {
        String[] words = message.split("\\s+");

        StringBuilder result = new StringBuilder();
        for (String word : words) {
            if (word.length() > 2) {
                result.append(scrambleWord(word)).append(" ");
            } else {
                result.append(word).append(" ");
            }
        }

        System.out.println(result.toString().trim());
    }

    private String scrambleWord(String word) {
        char[] chars = word.toCharArray();
        List<Character> middleChars = new ArrayList<>();
        for (int i = 1; i < chars.length - 1; i++) {
            middleChars.add(chars[i]);
        }
        Collections.shuffle(middleChars);
        for (int i = 1; i < chars.length - 1; i++) {
            chars[i] = middleChars.get(i - 1);
        }
        return new String(chars);
    }

    default void logWithSynonyms(String message) {
        // Assuming we have a pre-defined map of synonyms
        Map<String, String> synonyms = Map.of("happy", "content", "sad", "unhappy");

        String[] words = message.split("\\s+");
        StringBuilder result = new StringBuilder();

        for (String word : words) {
            result.append(synonyms.getOrDefault(word, word)).append(" ");
        }

        System.out.println(result.toString().trim());
    }

//    default void logWithMarkovChainTransformation(String message) {
//        String[] words = message.split("\\s+");
//        Map<String, List<String>> markovChain = new HashMap<>();
//
//        for (int i = 0; i < words.length - 1; i++) {
//            markovChain.computeIfAbsent(words[i], k -> new ArrayList<>()).add(words[i + 1]);
//        }
//
//        StringBuilder result = new StringBuilder();
//        Random random = new Random();
//        String currentWord = words[0];
//        result.append(currentWord).append(" ");
//
//        for (int i = 1; i < words.length; i++) {
//            List<String> nextWords = markovChain.get(currentWord);
//            if (nextWords != null && !nextWords.isEmpty()) {
//                currentWord = nextWords.get(random.nextInt(nextWords.size()));
//                result.append(currentWord).append(" ");
//            } else {
//                break;
//            }
//        }
//
//        System.out.println(result.toString().trim());
//    }

}
