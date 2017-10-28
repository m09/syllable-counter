/*
 * Copyright 2014 Hugo “m09” Mougard
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eu.crydee.syllablecounter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Fallback syllable counter.
 *
 * Based on the work of Thomas Jakobsen (thomj05@student.uia.no) and Thomas
 * Skardal (thomas04@student.uia.no) for the
 * <a href="https://github.com/nltk/nltk_contrib/blob/master/nltk_contrib/readability/syllables_en.py">
 * NLTK readability plugin
 * </a>.
 *
 *
 *
 * Their work is itself based on the algorithm in Greg Fast's perl module
 * Lingua::EN::Syllable.
 */
public class SyllableCounter {

    private final static String EXCEPTIONS_PATH
            = "/eu/crydee/syllablecounter/english-exceptions.txt",
            SUBSYL_PATH = "/eu/crydee/syllablecounter/english-subsyls.txt",
            ADDSYL_PATH = "/eu/crydee/syllablecounter/english-addsyls.txt";

    private final Map<String, Integer> exceptions;

    private final Set<Pattern> subSyls, addSyls;

    private final Set<Character> vowels;

    // Package protected for testing purposes
    Stream<String> getRessourceLines(Class<?> clazz, String filepath) {
        try (final BufferedReader fileReader = new BufferedReader(
                new InputStreamReader(clazz.getResourceAsStream(filepath),
                StandardCharsets.UTF_8)
        )) {
            // Collect the read lines before converting back to a Java stream
            // so that we can ensure that we close the InputStream and prevent leaks
            return fileReader.lines().collect(Collectors.toList()).stream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SyllableCounter() {
        exceptions = getRessourceLines(getClass(), EXCEPTIONS_PATH)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .map(line -> line.split(" "))
                .peek(fields -> {
                    if (fields.length != 2) {
                        System.err.println("couldn't parse the exceptions "
                                + "file. Didn't find 2 fields in one of "
                                + "the lines.");
                    }
                })
                .collect(Collectors.toMap(
                        fields -> fields[1],
                        fields -> Integer.parseInt(fields[0])));
        addSyls = getRessourceLines(getClass(), ADDSYL_PATH)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .map(Pattern::compile)
                .collect(Collectors.toSet());
        subSyls = getRessourceLines(getClass(), SUBSYL_PATH)
                .filter(line -> !line.isEmpty() && !line.startsWith("#"))
                .map(Pattern::compile)
                .collect(Collectors.toSet());
        vowels = new HashSet<>(Arrays.asList('a', 'e', 'i', 'o', 'u', 'y'));
    }

    /**
     * Main point of this library. Method to count the number of syllables of a
     * word using a fallback method as documented at the class level of this
     * documentation.
     *
     * @param word the word you want to count the syllables of.
     * @return the number of syllables of the word.
     */
    public int count(final String word) {
        if (word == null) {
            throw new NullPointerException("the word parameter was null.");
        } else if (word.length() == 0) {
            return 0;
        } else if (word.length() == 1) {
            return 1;
        }

        final String lowerCase = word.toLowerCase(Locale.ENGLISH);

        if (exceptions.containsKey(lowerCase)) {
            return exceptions.get(lowerCase);
        }

        final String prunned;
        if (lowerCase.charAt(lowerCase.length() - 1) == 'e') {
            prunned = lowerCase.substring(0, lowerCase.length() - 1);
        } else {
            prunned = lowerCase;
        }

        int count = 0;
        boolean prevIsVowel = false;
        for (char c : prunned.toCharArray()) {
            final boolean isVowel = vowels.contains(c);
            if (isVowel && !prevIsVowel) {
                ++count;
            }
            prevIsVowel = isVowel;
        }
        count += addSyls.stream()
                .filter(pattern -> pattern.matcher(prunned).find())
                .count();
        count -= subSyls.stream()
                .filter(pattern -> pattern.matcher(prunned).find())
                .count();

        return count > 0 ? count : 1;
    }
}
