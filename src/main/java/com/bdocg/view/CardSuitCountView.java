package com.bdocg.view;

import java.util.Objects;

public class CardSuitCountView {

    private String suit;
    private int count;

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardSuitCountView that = (CardSuitCountView) o;
        return count == that.count &&
                Objects.equals(suit, that.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, count);
    }
}
