package edu.woodson.lab;

import edu.woodson.util.CollectionUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static java.lang.Long.valueOf;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/13/2019
 */
class TJTwitterStatisticsTest {
    private TJTwitterStatistics statistics;

    @BeforeEach
    void beforeEach(){
        this.statistics = new TJTwitterStatistics("test0", "test1", "", "test2", "test1");
    }

    @Test
    void calculateMax() {
        Optional<Long> max = statistics.calculateMax(statistics.createFrequencyMap());
        assertTrue(max.isPresent());
        assertEquals(valueOf(2), max.get());
    }

    @Test
    void createFrequencyMap() {
        Map<String, Long> map = statistics.createFrequencyMap();
        assertEquals(4, map.size());
        assertEquals(valueOf(1), map.get(""));
        assertEquals(valueOf(1), map.get("test0"));
        assertEquals(valueOf(2), map.get("test1"));
        assertEquals(valueOf(1), map.get("test2"));
    }

    @Test
    void getMaxFrequency() {
        assertEquals(2, statistics.getMaxFrequency());
    }

    @Test
    void getMostPopularWord() {
        assertEquals("test1", statistics.getMostPopularWord());
    }

    @Test
    void mostPopularWord() {
        Set<String> strings = statistics.mostPopularWords();
        assertEquals(1, strings.size());
        assertEquals("test1", CollectionUtil.toSingle(strings));
    }

    @Test
    void removeCommonWords() {
        statistics.removeCommonWords(Arrays.asList("test1", "test2"));
        assertEquals(2, statistics.words.size());
        assertTrue(statistics.words.contains("test0"));
        assertTrue(statistics.words.contains(""));
    }

    @Test
    void removeEmptyStrings() {
        statistics.removeEmptyStrings();

        assertIterableEquals(Arrays.asList("test0", "test1", "test2", "test1"), statistics.words);
    }

    @Test
    void setValues() {
        List<String> words = Arrays.asList("a", "b", "c");
        statistics.setValues(words);
        assertIterableEquals(words, statistics.words);
    }

    @Test
    void sortAndRemoveEntries() {
        statistics.sortAndRemoveEntries();

        assertIterableEquals(Arrays.asList("test0", "test1", "test2", "test1"), statistics.words);
    }
}