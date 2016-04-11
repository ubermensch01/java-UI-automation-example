package com.fedii.tools;

import org.apache.commons.lang3.RandomStringUtils;

import de.svenjacobs.loremipsum.LoremIpsum;

/**
 * Created by sfedii on 3/23/16.
 */
public class StringUtilities
{

  public static String generateAlpha(int size)
  {
    return RandomStringUtils.randomAlphabetic(size);
  }

  public static String generateAlphaNumeric(int size)
  {
    return RandomStringUtils.randomAlphanumeric(size);
  }

  public static String generateNumeric(int size)
  {
    return RandomStringUtils.randomNumeric(size);
  }

  public static String generateLoremIpsum(int wordCount)
  {
    return new LoremIpsum().getWords(wordCount);
  }

}
