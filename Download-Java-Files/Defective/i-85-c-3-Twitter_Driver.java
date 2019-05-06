package edu.woodson;// Name:
// Date:

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class Twitter_Driver {
    private static PrintStream consolePrint;

    public static void main(String[] args) throws TwitterException, IOException {
        consolePrint = System.out; // this preserves the standard output so we can get to it later

        // PART III - Connect
        // set classpath, edit properties file
        Configuration builder = new ConfigurationBuilder()
                .setDebugEnabled(true)
                .setOAuthConsumerKey("egk7giRtajHZYXBbKAjzSVhWb")
                .setOAuthConsumerSecret("bLiAVPF6q14TIztSlsatAKRGUenRKQC5MKtWEfAB8xTfqweeHr")
                .setOAuthAccessToken("1059514626868740096-T5G4cTrK6NExdx1j33ss69X9ODnMq4")
                .setOAuthAccessTokenSecret("pHoPJfLiWHtNbEUmx0vOnFdMnn6O4Ajvpvs3IcJkwDeAY")
                .build();

        Twitter twitter = new TwitterFactory(builder).getInstance();
        TJTwitter bigBird = new TJTwitter(twitter);

        // Part III - Tweet
        // Create and set a String called message below
        // Uncomment this line to test, but then recomment so that the same
        // tweet does not get sent out over and over.

//      String message="Gooooooooo Colonials!";
//      twitter.updateStatus(message);
  
      /*
      String message="Go Colonials!";
      bigBird.tweetOut(message);
      */

        // PART III - Test
        // Choose a public Twitter user's handle

        Scanner scan = new Scanner(System.in);
        consolePrint.print("Please enter a Twitter handle, do not include the @symbol --> ");
        String twitter_handle = scan.next();

        // Find and print the most popular word they tweet
        while (!twitter_handle.equals("done")) {
            bigBird.queryHandle(twitter_handle);
            consolePrint.println("The most common word from @" + twitter_handle + " is: " + bigBird.getMostPopularWord() + ".");
            consolePrint.println("The word appears " + bigBird.getFrequencyMax() + " times.");
            consolePrint.println();
            consolePrint.print("Please enter a Twitter handle, do not include the @ symbol --> ");
            twitter_handle = scan.next();
        }

        // PART IV
        //bigBird.investigate();

    }
}

