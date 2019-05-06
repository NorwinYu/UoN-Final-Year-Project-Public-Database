package edu.woodson.util.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.TList;
import com.julienvey.trello.impl.TrelloImpl;
import com.julienvey.trello.impl.http.ApacheHttpClient;
import edu.woodson.util.trello.util.MovedCard;
import edu.woodson.util.trello.util.TrelloList;

import java.util.List;
import java.util.Scanner;


class TrelloForTwitterTest {

    public static void main(String[] args) {
        // Using trello account
        // Email: twitterinterfacewithtrello@gmail.com
        // Username: @twitterinterfacewithuser
        // Password: wtwoodsontwitterlab2019twitterinterfacewithtrello

        final String API_KEY = "6a888dffccb4c413711d7d617057fa07";
        final String TOKEN = "186d9044cd5dfdb60c3b5ab3befb2aaeb2daddccdd05244ce152743701fec680";

        Scanner sc = new Scanner(System.in);

        Trello trello = new TrelloImpl(API_KEY, TOKEN, new ApacheHttpClient());
        String boardId = "1XDTOfqt";
        Board board = trello.getBoard(boardId);
        List<TList> oldLists = board.fetchLists();

        TrelloList oldTrelloList = TrelloForTwitter.getTrelloList(trello, oldLists);

        System.out.println("Move the Test Card 6 (Moved) card in the Trello Test board from Doing to Done and then enter any key...");
        sc.next();

        Board newBoard = trello.getBoard(boardId);
        List<TList> newLists = newBoard.fetchLists();

        TrelloList newTrelloList = TrelloForTwitter.getTrelloList(trello, newLists);

        MovedCard moved = TrelloForTwitter.findMovedCard(board, oldTrelloList, newTrelloList);

        MovedCard expected = new MovedCard("Test Card 6 (Moved)", board.fetchLists().get(1), board.fetchLists().get(2));

        System.out.println("Expected: " + expected.getName() + " moved from " + expected.getFrom().getName() + " to " + expected.getTo().getName());

        assert moved != null;
        System.out.println("Reality: " + moved.getName() + " moved from " + moved.getFrom().getName() + " to " + moved.getTo().getName());
    }
}