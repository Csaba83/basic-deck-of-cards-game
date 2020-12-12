package com.bdocg.view;

import java.util.Objects;

public class PlayerView {

    private String name;
    private int totalValueOfCards;

    public void setName(String name) {
        this.name = name;
    }

    public void setTotalValueOfCards(int totalValueOfCards) {
        this.totalValueOfCards = totalValueOfCards;
    }

    public int getTotalValueOfCards() {
        return totalValueOfCards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerView that = (PlayerView) o;
        return totalValueOfCards == that.totalValueOfCards &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, totalValueOfCards);
    }
}
