package edu.woodson;

import twitter4j.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

class TJTwitter {
    private Twitter twitter;
    private List<Status> statuses;
    private int numberOfTweets;
    private List<String> words;
    private String mostPopularWord;
    private int frequencyMax;

    public TJTwitter(Twitter twitter) {
        // Makes an instance of Twitter - this is re-usable and thread safe.
        // Connects to Twitter and performs authorizations.
        this.twitter = twitter;
        this.statuses = new ArrayList<>();
        this.words = new ArrayList<>();
    }

    public List<String> getWords() {
        return words;
    }

    public int getNumberOfTweets() {
        return numberOfTweets;
    }

    public String getMostPopularWord() {
        return mostPopularWord;
    }

    public int getFrequencyMax() {
        return frequencyMax;
    }

    /******************  Part III - Tweet *******************/
    /**
     * This method tweets a given message.
     *
     * @param message a message you wish to Tweet out
     */
    public void tweetOut(String message) throws TwitterException {
        twitter.updateStatus(message);
    }


    /******************  Part III - Test *******************/
    /**
     * This method queries the tweets of a particular user's handle.
     *
     * @param handle the Twitter handle (username) without the @sign
     */
    @SuppressWarnings("unchecked")
    public void queryHandle(String handle) throws TwitterException, IOException {
        statuses.clear();
        words.clear();
        fetchTweets(handle);
        words.addAll(splitIntoWords(toMessage(statuses)));
        removeCommonEnglishWords();
        words.addAll(sortAndRemoveEmpties(words));
        this.mostPopularWord = CollectionUtil.toSingle(mostPopularWord(words));
    }

    private List<String> toMessage(List<Status> statuses) {
        return statuses.stream()
                .map(Status::getText)
                .collect(Collectors.toList());
    }

    /**
     * This method fetches the most recent 2,000 tweets of a particular user's handle and
     * stores them in an arrayList of Status objects.  Populates statuses.
     *
     * @param handle the Twitter handle (username) without the @sign
     */
    public void fetchTweets(String handle) throws TwitterException, IOException {
        // Creates file for debugging purposes
        PrintStream fileout = new PrintStream(new FileOutputStream("tweets.txt"));
        Paging page = new Paging(1, 200);
        int p = 1;
        while (p <= 10) {
            page.setPage(p);
            statuses.addAll(twitter.getUserTimeline(handle, page));
            p++;
        }
        numberOfTweets = statuses.size();
        fileout.println("Number of tweets = " + numberOfTweets);
    }

    /**
     * This method takes each status and splits them into individual words.
     * Store the word in words.
     */
    public List<String> splitIntoWords(List<String> statuses) {
        return statuses.stream()
                .map(StringTokenizer::new)
                .map(Enumeration::asIterator)
                .map(objectIterator -> (Iterable<Object>) () -> objectIterator)
                .map(Iterable::spliterator)
                .flatMap(objectSpliterator -> StreamSupport.stream(objectSpliterator, false))
                .map(Object::toString)
                .collect(Collectors.toList());

    }

    /**
     * This method removes common English words from the list of words.
     * Remove all words found in commonWords.txt  from the argument list.
     * The count will not be given in commonWords.txt. You must count the number of words in this method.
     * This method should NOT throw an exception.  Use try/catch.
     */
    @SuppressWarnings("unchecked")
    public void removeCommonEnglishWords() {


    }

    /**
     * This method sorts the words in words in alphabetically (and lexicographic) order.
     * You should use your sorting code you wrote earlier this year.
     * Remove all empty strings while you are at it.
     * @param terms
     */
    @SuppressWarnings("unchecked")
    public List<String> sortAndRemoveEmpties(List<String> terms) {
        Collections.sort(removeEmptyStrings(terms));
        return terms;
    }

    private List<String> removeEmptyStrings(List<String> terms) {
        for (String s : terms) {
            if (s.trim().equals(""))
                terms.remove(s);
        }
        return terms;
    }

    /**
     * This method calculates the word that appears the most times
     * Consider case - should it be case sensitive?  The choice is yours.
     *
     * post will populate the frequencyMax variable with the frequency of the most common word
     * @param words The words to search.
     * @post will populate the frequencyMax variable with the frequency of the most common word
     */
    @SuppressWarnings("unchecked")
    public Set<String> mostPopularWord(List<String> words) {
        if (words.isEmpty()) {
            throw new IllegalArgumentException("No words found for " + words);
        }

        Map<String, Long> map = createFrequencyMap(words);
        Optional<Long> maxOptional = calculateMax(map);
        if (!maxOptional.isPresent()) {
            throw new IllegalArgumentException("No maximum found for " + words);
        }

        long max = maxOptional.get();
        return map.keySet()
                .stream()
                .filter(s -> map.get(s).equals(max))
                .collect(Collectors.toSet());
    }

    Optional<Long> calculateMax(Map<String, Long> map) {
        return map.values().stream().max(Long::compareTo);
    }

    Map<String, Long> createFrequencyMap(List<String> words) {
        return words.stream().collect(Collectors.toMap(Function.identity(), string -> words.stream()
                .filter(s -> s.equals(string))
                .count(), Long::max));

  /*      return words.stream().collect(Collectors.toMap(
                s -> s,
                string ->
                ),
                new BinaryOperator<Long>();
        );*/
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

    /******************  Part IV *******************/
    public void investigate() {
        //Enter your code here
    }

    /**
     * This method determines how many people in Arlington, VA
     * tweet about the Miami Dolphins.  Hint:  not many. :(
     */
    public void sampleInvestigate() {
        Query query = new Query("Miami Dolphins");
        query.setCount(100);
        query.setGeoCode(new GeoLocation(38.8372839, -77.1082443), 5, Query.MILES);
        query.setSince("2015-12-1");
        try {
            QueryResult result = twitter.search(query);
            System.out.println("Count : " + result.getTweets().size());
            for (Status tweet : result.getTweets()) {
                System.out.println("@" + tweet.getUser().getName() + ": " + tweet.getText());
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
