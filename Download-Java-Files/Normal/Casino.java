package io.zipcoder.casino;


import io.zipcoder.casino.CardGames.BlackJack.Blackjack;
import io.zipcoder.casino.CardGames.GoFish.GoFish;
import io.zipcoder.casino.DiceGame.Craps.Craps;
import io.zipcoder.casino.DiceGame.Yahtzee.Yahtzee;
import io.zipcoder.casino.Interfaces.Game;
import io.zipcoder.casino.Utilities.Console;
import io.zipcoder.casino.Utilities.DisplayGraphics;
import io.zipcoder.casino.Utilities.Player;

public class Casino {
    private static Player player;
    private static Game game;

    public static Player getPlayer() {
        return player;
    }

    public static void setPlayer(Player player) {
        Casino.player = player;
    }

    public static void setGame(Game game) {
        Casino.game = game;
    }

    public static Game getGame(){
        return game;
    }

    public static void main(String[] args) {
        Casino.run();
    }


    public static void run() {
        Console console = Console.getInstance();
        boolean running = true;

        console.println(DisplayGraphics.zipCodeCasinoString());

        String name = console.getStringInput("Welcome to the Zip Code GoFish!  What is your name?");
        Double wallet = console.getDoubleInput("\nThanks for playing, %s!  How much money will you be gambling?", name);
        Casino.setPlayer(new Player(name, wallet));

        while (running) {
            console.println("\nWhat game would you like to play, %s?", name);
            String gameToPlay = console.getStringInput("We have Blackjack, Craps, Yahtzee, and Go Fish!");

            switch (gameToPlay.toLowerCase()) {

                case "yahtzee":
                    Casino.setGame(new Yahtzee(player));
                    Casino.game.play();
                    break;

                case "blackjack":
                    Casino.setGame(new Blackjack(player));
                    Casino.game.play();
                    break;

                case "craps":
                    Casino.setGame(new Craps(player));
                    Casino.game.play();
                    break;

                case "go fish":
                    Casino.setGame(new GoFish(player));
                    Casino.game.play();
                    break;

                case "exit":
                    running = false;
                    console.println("Bye %s!  Thank you for playing at the Zip Code Casino!", name);
                    break;

                default:
                    console.println("Invalid Game!  Try again!");
            }
        }
    }
}
