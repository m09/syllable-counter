/*
 * Copyright 2015 m09.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.UUID;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author m09
 */
public class SyllableCounterTest {

    private final static String exceptionsFilepath
            = "/eu/crydee/syllablecounter/english-exceptions.txt",
            testFilepath
            = "/eu/crydee/syllablecounter/english-test.txt";

    /**
     * Test of setCacheSize method, of class SyllableCounter.
     */
    @Test
    public void testSetCacheSize() {
        System.out.println("setCacheSize");
        int cacheSize = 300;
        SyllableCounter sc = new SyllableCounter(cacheSize);
        Stream.generate(() -> UUID.randomUUID().toString()).limit(cacheSize * 2)
                .forEach(s -> sc.count(s));
        assertEquals(cacheSize, sc.getCurrentCacheSize());
    }

    /**
     * Test of count method, of class SyllableCounter.
     */
    @Test
    public void testCountFromTestFile() {
        System.out.println("count from test file");
        testFromFile(testFilepath);
    }

    /**
     * Test of count method, of class SyllableCounter.
     */
    @Test(expected = NullPointerException.class)
    public void testCountNull() {
        System.out.println("count null");
        new SyllableCounter().count(null);
    }

    /**
     * Test of count method, of class SyllableCounter.
     */
    @Test
    public void testCountExceptions() {
        System.out.println("count exceptions");
        testFromFile(exceptionsFilepath);
    }

    private void testFromFile(String filepath) {
        SyllableCounter sc = new SyllableCounter();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(
                getClass().getResourceAsStream(filepath)))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    String[] fields = line.split(" ");
                    if (fields.length != 2) {
                        System.err.println("couldn't parse the exceptions "
                                + "file. Didn't find 2 fields in one of the "
                                + "lines.");
                    }
                    int nSyllables = Integer.parseInt(fields[0]);
                    String word = fields[1];
                    int count = sc.count(word);
                    assertEquals(count, nSyllables);
                }
            }
        } catch (IOException ex) {
            fail("could not find " + filepath);
        }
    }
}
