package edu.woodson.util.trello.util;

import com.julienvey.trello.domain.TList;

public class MovedCard {

    private final String name;
    private final TList from;
    private final TList to;

    public MovedCard(String name, TList from, TList to) {
        this.name = name;
        this.from = from;
        this.to = to;
    }

    public String getName() {
        return name;
    }

    public TList getFrom() {
        return from;
    }

    public TList getTo() {
        return to;
    }
}
