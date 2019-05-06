package edu.woodson.lab;

import edu.woodson.util.CollectionUtil;
import org.junit.jupiter.api.Test;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author SirMathhman
 * @version 0.0.0
 * @since 3/13/2019
 */
class TJTwitterTest {
    @Test
    void getStatistics() {
        TJTwitter twitter = new TJTwitter(null);
        assertThrows(IllegalStateException.class, twitter::getStatistics);
    }

    @Test
    void getMaxFrequency() {
        TJTwitter twitter = new TJTwitter(null);
        twitter.statistics = new TJTwitterStatistics("test0", "test1", "test1");

        assertEquals(2, twitter.getMaxFrequency());
    }

    @Test
    void getMostPopularWord() {
        TJTwitter twitter = new TJTwitter(null);
        twitter.statistics = new TJTwitterStatistics("test0", "test1", "test1");

        assertEquals("test1", twitter.getMostPopularWord());
    }

    @Test
    void queryHandle() throws Exception {
        TJTwitter twitter = new TJTwitter(new AbstractTwitter() {
            @Override
            public ResponseList<Status> getUserTimeline(String screenName, Paging paging) {
                AbstractStatus status0 = new AbstractStatus() {
                    @Override
                    public String getText() {
                        return "test0";
                    }
                };
                AbstractStatus status1 = new AbstractStatus() {
                    @Override
                    public String getText() {
                        return "test1";
                    }
                };
                List<AbstractStatus> initial = Arrays.asList(status0, status1);
                return new AbstractResponseList<>(initial) {
                };
            }
        });

        String handle = "test";
        Paging paging = new Paging(1,1);

        twitter.queryHandle(paging, handle);
        List<String> words = twitter.statistics.words;
        assertIterableEquals(Arrays.asList("test0", "test1"), words);
    }

    @Test
    void fetchTweets() throws TwitterException {
        TJTwitter twitter = new TJTwitter(new AbstractTwitter() {
            @Override
            public ResponseList<Status> getUserTimeline(String screenName, Paging paging) {
                AbstractStatus status0 = new AbstractStatus() {
                    @Override
                    public String getText() {
                        return "test0";
                    }
                };
                AbstractStatus status1 = new AbstractStatus() {
                    @Override
                    public String getText() {
                        return "test1";
                    }
                };
                List<AbstractStatus> initial = Arrays.asList(status0, status1);
                return new AbstractResponseList<>(initial) {};
            }
        });

        String handle = "test";
        Paging paging = new Paging(1, 1);
        List<Status> statuses = twitter.fetchTweets(paging, handle);
        assertIterableEquals(Arrays.asList("test0", "test1"), twitter.toMessage(statuses));
    }

    @Test
    void splitIntoWords() {
        TJTwitter twitter = new TJTwitter(null);
        List<String> words = twitter.splitIntoWords(Collections.singletonList("test0 test1"));
        assertIterableEquals(Arrays.asList("test0", "test1"), words);
    }

    @Test
    void toMessage() {
        TJTwitter twitter = new TJTwitter(null);
        List<String> strings = twitter.toMessage(Collections.singleton(new AbstractStatus() {
            @Override
            public String getText() {
                return "test";
            }
        }));

        assertEquals(1, strings.size());
        assertEquals("test", CollectionUtil.toSingle(strings));
    }

    @Test
    void loadCommonWordsFromLocation() throws IOException {
        TJTwitter twitter = new TJTwitter(null);
        List<String> words = twitter.loadCommonWordsFromLocation();
        assertFalse(words.isEmpty());
    }

    @Test
    void getCommonWordsURL() {
        TJTwitter twitter = new TJTwitter(null);
        Optional<URL> url = twitter.getCommonWordsURL();
        assertTrue(url.isPresent());
        assertEquals(getClass().getResource("/commonWords.txt"), url.get());
    }

    @Test
    void loadCommonWordsFromStream() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("test0\ntest1".getBytes());
        TJTwitter twitter = new TJTwitter(null);
        List<String> words = twitter.loadCommonWordsFromStream(inputStream);
        assertIterableEquals(Arrays.asList("test0", "test1"), words);
    }

    @Test
    void removePunctuation() {
        TJTwitter twitter = new TJTwitter(null);
        String result = twitter.removePunctuation("I don't like cheese!");
        assertEquals("idon'tlikecheese", result);
    }

    @Test
    void tweetOut() throws TwitterException {
        TJTwitter twitter = new TJTwitter(new AbstractTwitter() {
            @Override
            public Status updateStatus(String status) {
                assertEquals("test", status);
                return null;
            }
        });

        twitter.tweetOut("test");
    }
}