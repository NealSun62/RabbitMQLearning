package com.sun.overweight.common.utils;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Emoji过滤
 *
 * @author wangkw
 * @date 2020/5/19
 * @description
 */
public class EmojiFilter {

    private static boolean isEmojiCharacter(char codePoint) {
        return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA)
                || (codePoint == 0xD)
                || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
                || ((codePoint >= 0xE000) && (codePoint <= 0xFFFD))
                || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
    }

    /**
     *
     */
    private static final String EMOJI_STR = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
    public static String filterEmoji(String source) {
        if (source != null) {
            Pattern emoji = Pattern.compile(EMOJI_STR,
                    Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                source = emojiMatcher.replaceAll("");
                return source;
            }
            return source;
        }
        return source;
    }

}
