package edu.woodson.util.trello.util;

public class Difference {

    private final int previous;
    private final int current;
    private final int index;

    public Difference(int previous, int current, int index) {
        this.previous = previous;
        this.current = current;
        this.index = index;
    }

    public int getPrevious() {
        return previous;
    }

    public int getCurrent() {
        return current;
    }

    public int getIndex() {
        return index;
    }
}
