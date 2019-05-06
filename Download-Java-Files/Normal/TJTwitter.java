package edu.woodson.lab;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class TJTwitter {
    private static final String COMMON_WORDS_LOCATION = "/commonWords.txt";
    private final Twitter twitter;

    TJTwitterStatistics statistics;

    public TJTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    public long getMaxFrequency() {
        return getStatistics().getMaxFrequency();
    }

    public TJTwitterStatistics getStatistics() {
        if (statistics == null) {
            throw new IllegalStateException("Statistics have not been found because nothing was queried yet.");
        }

        return statistics;
    }

    /******************  Part III - Tweet *******************/

    public String getMostPopularWord() {
        return getStatistics().getMostPopularWord();
    }

    /**
     * This method queries the tweets of a particular user's handle.
     *
     * @param paging The paging.
     * @param handle the Twitter handle (username) without the @sign
     */
    @SuppressWarnings("unchecked")
    public TJTwitterStatistics queryHandle(Paging paging, String handle) throws Exception {
        List<Status> statuses = fetchTweets(paging, handle);
        List<String> words = splitIntoWords(toMessage(statuses));
        TJTwitterStatistics statistics = new TJTwitterStatistics();
        statistics.setValues(words);

        statistics.removeCommonWords(loadCommonWordsFromLocation());
        statistics.sortAndRemoveEntries();

        this.statistics = statistics;
        return statistics;
    }

    /**
     * This method fetches the most recent 2,000 tweets of a particular user's handle and
     * stores them in an arrayList of Status objects.  Populates statuses.
     *
     * @param paging The paging.
     * @param handle the Twitter handle (username) without the @sign
     */
    public List<Status> fetchTweets(Paging paging, String handle) throws TwitterException {
        List<Status> statusList = new ArrayList<>();

        /*
        Originally, I had this loop replaced with a Java 8 Stream.
        That is a horrible decision, not only because of the exception in the body of the for loop,
        but also because iteration is necessary for this code's readability.

        Besides, there is no Paging.getPages() method, so it would be even more difficult
        to set up a stream in that regard.
         */
        for (int i = 1; i <= paging.getCount(); i++) {
            paging.setPage(i);

            statusList.addAll(twitter.getUserTimeline(handle, paging));
        }

        return statusList;
    }

    /**
     * This method takes each status and splits them into individual words.
     * Store the word in words.
     */
    public List<String> splitIntoWords(List<String> statuses) {
        return statuses.stream()
                .map(s -> s.split(" "))
                .map(Stream::of)
                .flatMap(Function.identity())
                .collect(Collectors.toList());
    }

    List<String> toMessage(Collection<? extends Status> statuses) {
        return statuses.stream()
                .map(Status::getText)
                .collect(Collectors.toList());
    }

    List<String> loadCommonWordsFromLocation() throws IOException {
        Optional<URL> commonWordsURL = getCommonWordsURL();
        if (!commonWordsURL.isPresent()) {
            throw new IllegalArgumentException("Could not find common words " + COMMON_WORDS_LOCATION);
        }

        return loadCommonWordsFromStream(commonWordsURL.get().openStream());
    }


    Optional<URL> getCommonWordsURL() {
        return Optional.ofNullable(getClass().getResource(TJTwitter.COMMON_WORDS_LOCATION));
    }

    List<String> loadCommonWordsFromStream(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = reader.lines()
                .map(String::trim)
                .map(s -> s.split(" "))
                .filter(strings -> strings.length == 1)
                .map(strings -> strings[0])
                .collect(Collectors.toList());
        reader.close();
        return lines;
    }


    /**
     * This method removes common punctuation from each individual word.
     * This method changes everything to lower case.
     * Consider reusing code you wrote for a previous lab.
     * Consider if you want to remove the # or @ from your words. Could be interesting to keep (or remove).
     *
     * @ param String  the word you wish to remove punctuation from
     * @ return String the word without any punctuation, all lower case
     */
    public String removePunctuation(String s) {
        return s.replaceAll("[^a-z'A-Z]", "").toLowerCase();
    }

    /**
     * This method tweets a given message.
     *
     * @param message a message you wish to Tweet out
     */
    public void tweetOut(String message) throws TwitterException {
        twitter.updateStatus(message);
    }
}
