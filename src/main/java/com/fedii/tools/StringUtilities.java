package com.fedii.tools;

import de.svenjacobs.loremipsum.LoremIpsum;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by sfedii on 3/23/16.
 */
public class StringUtilities {

    /**
     * Generates a random alphabetic character sequence of given {@param size}.
     *
     * @param size size of the sequence
     * @return {@link String} random alphabetic character sequence
     */
    public static String generateAlpha(int size) {
        return RandomStringUtils.randomAlphabetic(size);
    }

    /**
     * Generates a random alphanumeric character sequence of given {@param size}.
     *
     * @param size size of the sequence
     * @return {@link String} random alphanumeric character sequence
     */
    public static String generateAlphaNumeric(int size) {
        return RandomStringUtils.randomAlphanumeric(size);
    }

    /**
     * Generates a random numeric character sequence of given {@param size}.
     *
     * @param size size of the sequence
     * @return {@link String} random numeric character sequence
     */
    public static String generateNumeric(int size) {
        return RandomStringUtils.randomNumeric(size);
    }

    /**
     * Generates Lorem Ipsum of given {@param wordCount}.
     *
     * @param wordCount Number of words.
     * @return {@link String} Lorem Ipsum
     */
    public static String generateLoremIpsum(int wordCount) {
        return new LoremIpsum().getWords(wordCount);
    }

}
