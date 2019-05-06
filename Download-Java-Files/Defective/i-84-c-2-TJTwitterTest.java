package edu.woodson;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import twitter4j.Twitter;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TJTwitterTest {
    private TJTwitter twitter;

    @BeforeEach
    void beforeEach() {
        twitter = new TJTwitter(Mockito.mock(Twitter.class));
    }

    @Test
    void getTerms() {
    }

    @Test
    void getNumberOfTweets() {
    }

    @Test
    void getMostPopularWord() {
    }

    @Test
    void getFrequencyMax() {
    }

    @Test
    void tweetOut() {
    }

    @Test
    void queryHandle() {
    }

    @Test
    void removePunctuation() {
        TJTwitter tjTwitter = new TJTwitter(Mockito.mock(Twitter.class));
        assertEquals("shouldremovepunctuationbutnot'", tjTwitter.removePunctuation("?Should,Remove,Punctuation!ButNot'"));
    }

    @Test
    void investigate() {
    }

    @Test
    void sampleInvestigate() {
    }

    @ParameterizedTest
    @ValueSource(strings = {"I", "have", "a", "pen,", "an", "apple!"})
    void splitIntoWords(String word) {
        List<String> words = twitter.splitIntoWords(Collections.singletonList("I have a pen, I have an apple!"));
        assertEquals(8, words.size());
        assertTrue(words.contains(word));
    }

    @Test
    void mostPopularWord() {
        Set<String> strings = twitter.mostPopularWord(Arrays.asList("test0", "test1", "test1", "test2"));

        assertEquals(1, strings.size());
        assertEquals("test1", CollectionUtil.toSingle(strings));
    }

    @Test
    void getWords() {
    }

    @Test
    void fetchTweets() {
    }

    @Test
    void removeCommonEnglishWords() {
    }

    @Test
    void sortAndRemoveEmpties() {
        TJTwitter tjTwitter = new TJTwitter(Mockito.mock(Twitter.class));

        List<String> input = new LinkedList<>();
        Collections.addAll(input, "hi", "", "I");

        List<String> expected = new LinkedList<>();
        Collections.addAll(expected, "I", "hi");

        assertEquals(expected, tjTwitter.sortAndRemoveEmpties(input));
    }

    @Test
    void calculateMax() {
        Map<String, Long> map = new HashMap<>();
        map.put("test0", 1L);
        map.put("test1", 2L);
        map.put("test2", 3L);
        Optional<Long> max = twitter.calculateMax(map);
        assertTrue(max.isPresent());
        assertEquals(3L, (long) max.get());
    }

    @Test
    void createFrequencyMap() {
        Map<String, Long> map = twitter.createFrequencyMap(Arrays.asList("test0", "test1", "test1"));
        assertEquals(2, map.size());
        assertTrue(map.containsKey("test0"));
        assertTrue(map.containsKey("test1"));
        assertEquals(1L, (long) map.get("test0"));
        assertEquals(2L, (long) map.get("test1"));
    }
}