package edu.woodson.lab;// Name:
// Date:

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import edu.woodson.util.trello.TrelloForTwitter;
import edu.woodson.util.trello.util.MovedCard;
import edu.woodson.util.trello.util.TrelloList;
import twitter4j.Paging;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.time.Duration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

class TwitterDriver {
    private static final String API_KEY = "6a888dffccb4c413711d7d617057fa07";
    private static final String TOKEN = "186d9044cd5dfdb60c3b5ab3befb2aaeb2daddccdd05244ce152743701fec680";
    private static final String O_AUTH_CONSUMER_KEY = "egk7giRtajHZYXBbKAjzSVhWb";
    private static final String O_AUTH_ACCESS_TOKEN = "1059514626868740096-T5G4cTrK6NExdx1j33ss69X9ODnMq4";
    private static final String O_AUTH_ACCESS_TOKEN_SECRET = "pHoPJfLiWHtNbEUmx0vOnFdMnn6O4Ajvpvs3IcJkwDeAY";
    private static final String O_AUTH_CONSUMER_SECRET = "bLiAVPF6q14TIztSlsatAKRGUenRKQC5MKtWEfAB8xTfqweeHr";
    private static final int THREAD_SIZE = 8;
    private static final Duration CHECK_DURATION = Duration.ofSeconds(3);

    private final ScheduledExecutorService service = Executors.newScheduledThreadPool(THREAD_SIZE);
    private final Scanner scanner = new Scanner(System.in);
    private final TJTwitter twitter = buildTJTwitter();

    public static void main(String[] args) {
        new TwitterDriver().start();
    }

    private void start() {
        try {
            printStatistics();
            listenForTrello();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void listenForTrello() {
        String boardId = getBoardID();

        Trello trello = new TrelloImpl(API_KEY, TOKEN, new ApacheHttpClient());
        TwitterDriverChecker task = new TwitterDriverChecker(trello, boardId);
        service.scheduleAtFixedRate(task::check, 0, CHECK_DURATION.toMillis(), TimeUnit.MILLISECONDS);
    }

    private String getBoardID() {
        System.out.println("Enter the Trello board id to listen to: ");
        return scanner.next();
    }

    private void printStatistics() throws Exception {
        while (true) {
            System.out.println("Please enter a Twitter handle, do not include the @ symbol.");
            System.out.println("To exit this feature, please enter \"done\".");
            String token = scanner.nextLine();
            if (token.equals("done")) {
                break;
            }

            int count = getCount(scanner::nextLine);
            TJTwitterStatistics statistics = twitter.queryHandle(new Paging(1, count), token);
            System.out.println("The most common word from @" + token + " is: " + statistics.mostPopularWords() + ".");
            System.out.println("The word appears " + statistics.getMaxFrequency() + " times.\n");
        }
    }

    private int getCount(Supplier<String> supplier) {
        do {
            System.out.println("Enter in the page count:");
            String next = supplier.get();
            try {
                return Integer.parseInt(next);
            } catch (NumberFormatException e) {
                System.out.println("Invalid token " + next + ". Please try something else:");
            }
        } while (true);
    }

    private TJTwitter buildTJTwitter() {
        Configuration builder = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey(O_AUTH_CONSUMER_KEY)
                .setOAuthConsumerSecret(O_AUTH_CONSUMER_SECRET)
                .setOAuthAccessToken(O_AUTH_ACCESS_TOKEN)
                .setOAuthAccessTokenSecret(O_AUTH_ACCESS_TOKEN_SECRET)
                .build();

        return new TJTwitter(new TwitterFactory(builder).getInstance());
    }

    private class TwitterDriverChecker {
        private final Trello trello;
        private final String boardId;
        Board board;
        private boolean initial = false;
        private TrelloList oldTrelloList;

        TwitterDriverChecker(Trello trello, String boardId) {
            this.trello = trello;
            this.boardId = boardId;
        }

        void check() {
            checkInitial();

            Board newBoard = trello.getBoard(boardId);
            List<TList> newLists = newBoard.fetchLists();
            TrelloList newTrelloList = TrelloForTwitter.getTrelloList(trello, newLists);
            MovedCard moved = TrelloForTwitter.findMovedCard(board, oldTrelloList, newTrelloList);

            if (moved != null) {
                try {
                    String message = "You moved " + moved.getName() + " from " + moved.getFrom().getName() + " to " + moved.getTo().getName();
                    twitter.tweetOut(message);
                    System.out.println("Tweeted out: " + message);
                } catch (TwitterException e) {
                    e.printStackTrace();
                }
            }
        }

        private void checkInitial() {
            if (!initial) {
                board = trello.getBoard(boardId);
                List<TList> oldLists = board.fetchLists();
                oldTrelloList = TrelloForTwitter.getTrelloList(trello, oldLists);
                initial = true;
            }
        }
    }
}

