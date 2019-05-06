package edu.woodson.util.trello;

import com.julienvey.trello.Trello;
import com.julienvey.trello.domain.Board;
import com.julienvey.trello.domain.Card;
import com.julienvey.trello.domain.TList;
import edu.woodson.util.trello.util.Difference;
import edu.woodson.util.trello.util.MovedCard;
import edu.woodson.util.trello.util.TrelloList;
import org.apache.commons.collections4.CollectionUtils;

import java.util.LinkedList;
import java.util.List;

public class TrelloForTwitter {

    public static TrelloList getTrelloList(Trello trello, List<TList> lists) {
        List<List<Card>> result = new LinkedList<>();

        for (TList tl : lists) {
            result.add(getCards(trello, tl));
        }

        return new TrelloList(result);
    }

    private static List<Card> getCards(Trello trello, TList list) {
        return trello.getListCards(list.getId());
    }

    public static MovedCard findMovedCard(Board board, TrelloList oldTrelloList, TrelloList newTrelloList) {
        List<List<Card>> oldBoard = oldTrelloList.getTrelloList();
        List<List<Card>> newBoard = newTrelloList.getTrelloList();

        for (int i = 0; i < oldBoard.size(); i++) {
            List<Card> oldCards = oldBoard.get(i);
            List<Card> newCards = newBoard.get(i);
            if (oldCards.size() != newCards.size()) {
                return new MovedCard(findMovedCardWithinLists(oldCards, newCards), findMovedFrom(board, oldBoard, newBoard), findMovedTo(board, oldBoard, newBoard));
            }
        }
        return null;
    }

    private static TList findMovedTo(Board board, List<List<Card>> oldBoard, List<List<Card>> newBoard) {
        List<Integer> oldSizes = new LinkedList<>();
        List<Integer> newSizes = new LinkedList<>();

        for (List<Card> cardList : oldBoard) {
            oldSizes.add(cardList.size());
        }

        for (List<Card> cardList : newBoard) {
            newSizes.add(cardList.size());
        }

        List<Difference> differences = new LinkedList<>();

        for (int i = 0; i < oldSizes.size(); i++) {
            if (!oldSizes.get(i).equals(newSizes.get(i))) {
                differences.add(new Difference(oldSizes.get(i), newSizes.get(i), i));
            }
        }

        TList movedTo = null;

        for (Difference d : differences) {
            if (d.getPrevious() < d.getCurrent()) {
                movedTo = getTListFromIndex(board, d.getIndex());
            }
        }

        return movedTo;
    }

    private static TList findMovedFrom(Board board, List<List<Card>> oldBoard, List<List<Card>> newBoard) {
        List<Integer> oldSizes = new LinkedList<>();
        List<Integer> newSizes = new LinkedList<>();

        for (List<Card> cardList : oldBoard) {
            oldSizes.add(cardList.size());
        }

        for (List<Card> cardList : newBoard) {
            newSizes.add(cardList.size());
        }

        List<Difference> differences = new LinkedList<>();

        for (int i = 0; i < oldSizes.size(); i++) {
            if (!oldSizes.get(i).equals(newSizes.get(i))) {
                differences.add(new Difference(oldSizes.get(i), newSizes.get(i), i));
            }
        }

        TList movedFrom = null;

        for (Difference d : differences) {
            if (d.getPrevious() > d.getCurrent()) {
                movedFrom = getTListFromIndex(board, d.getIndex());
            }
        }

        return movedFrom;
    }

    private static TList getTListFromIndex(Board board, int index) {
        List<TList> lists = board.fetchLists();
        return lists.get(index);
    }

    private static String findMovedCardWithinLists(List<Card> oldCards, List<Card> newCards) {
        List<String> movedTo;
        List<String> movedFrom;

        if (newCards.size() < oldCards.size()) {
            movedFrom = new LinkedList<>();
            for (Card c : newCards) {
                movedFrom.add(c.getName());
            }
            movedTo = new LinkedList<>();
            for (Card c : oldCards) {
                movedTo.add(c.getName());
            }
        } else {
            movedFrom = new LinkedList<>();
            for (Card c : oldCards) {
                movedFrom.add(c.getName());
            }
            movedTo = new LinkedList<>();
            for (Card c : newCards) {
                movedTo.add(c.getName());
            }
        }

        return new LinkedList<>(CollectionUtils.subtract(movedTo, movedFrom)).get(0);

    }
}
