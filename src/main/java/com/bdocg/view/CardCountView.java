package com.bdocg.view;

import java.util.Objects;

public class CardCountView {

    private String suit;
    private String value;
    private int count;

    public String getSuit() {
        return suit;
    }

    public void setSuit(String suit) {
        this.suit = suit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
        CardCountView that = (CardCountView) o;
        return count == that.count &&
                suit.equals(that.suit) &&
                value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value, count);
    }
}
