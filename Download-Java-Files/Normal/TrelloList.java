package edu.woodson.util.trello.util;

import com.julienvey.trello.domain.Card;

import java.util.List;

public class TrelloList {

    private final List<List<Card>> trelloList;

    public TrelloList(List<List<Card>> trelloList) {
        this.trelloList = trelloList;
    }

    public List<List<Card>> getTrelloList() {
        return trelloList;
    }
}
