package com.bdocg.view;

import java.util.Objects;

public class CardView {

    private String suit;
    private String value;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardView cardView = (CardView) o;
        return Objects.equals(suit, cardView.suit) &&
                Objects.equals(value, cardView.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, value);
    }
}
