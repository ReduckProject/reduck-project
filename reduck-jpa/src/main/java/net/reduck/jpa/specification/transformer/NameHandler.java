package net.reduck.jpa.specification.transformer;

import org.springframework.util.StringUtils;

/**
 * @author Reduck
 * @since 2022/9/23 10:59
 */
public class NameHandler {

    public static String withLine(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        StringBuilder charBuilder = new StringBuilder();
        char[] chars = str.toCharArray();
        charBuilder.append(tryToLow(chars[0]));
        for (int i = 1; i < chars.length; i++) {
            if (isUpper(chars[i])) {
                if (isLow(chars[i - 1])) {
                    charBuilder.append("_").append(toLow(chars[i]));

                } else {
                    charBuilder.append(toLow(chars[i]));
                }
            } else {
                charBuilder.append(chars[i]);
            }
        }

        return charBuilder.toString();
    }

    private static boolean isUpper(char c) {
        return c >= 'A' && c <= 'Z';
    }

    private static boolean isLow(char c) {
        return c >= 'a' && c <= 'z';
    }

    private static char toLow(char upper) {
        return (char) (upper + 32);
    }

    private static char tryToLow(char upper) {
        if (!isUpper(upper)) {
            return upper;
        }
        return (char) (upper + 32);
    }

    private static char toUpper(char low) {
        return (char) (low - 32);
    }

    private static char tryToUpper(char low) {
        if (!isLow(low)) {
            return low;
        }
        return (char) (low - 32);
    }


    private static boolean isLine(char c) {
        return c == '_';
    }

    public static String withCase(String str) {
        if (StringUtils.isEmpty(str)) {
            return str;
        }

        StringBuilder charBuilder = new StringBuilder();
        char[] chars = str.toCharArray();

        charBuilder.append(tryToLow(chars[0]));

        for (int i = 1; i < chars.length; i++) {
            if (isLine(chars[i])) {
                if (i == chars.length - 1) {
                    charBuilder.append(chars[i]);
                }
                continue;
            }

            if (isLine(chars[i - 1]) && i > 1) {
                charBuilder.append(tryToUpper(chars[i]));
            } else {
                charBuilder.append(tryToLow(chars[i]));
            }
        }

        return charBuilder.toString();
    }
}
