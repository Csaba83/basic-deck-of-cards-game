package com.bdocg.domain;

public enum Card {

    DIAMOND_2("Diamond", "2"),
    DIAMOND_3("Diamond", "3"),
    DIAMOND_4("Diamond", "4"),
    DIAMOND_5("Diamond", "5"),
    DIAMOND_6("Diamond", "6"),
    DIAMOND_7("Diamond", "7"),
    DIAMOND_8("Diamond", "8"),
    DIAMOND_9("Diamond", "9"),
    DIAMOND_10("Diamond", "10"),
    DIAMOND_J("Diamond", "J"),
    DIAMOND_Q("Diamond", "Q"),
    DIAMOND_K("Diamond", "K"),
    DIAMOND_A("Diamond", "A"),
    SPADE_2("Spade", "2"),
    SPADE_3("Spade", "3"),
    SPADE_4("Spade", "4"),
    SPADE_5("Spade", "5"),
    SPADE_6("Spade", "6"),
    SPADE_7("Spade", "7"),
    SPADE_8("Spade", "8"),
    SPADE_9("Spade", "9"),
    SPADE_10("Spade", "10"),
    SPADE_J("Spade", "J"),
    SPADE_Q("Spade", "Q"),
    SPADE_K("Spade", "K"),
    SPADE_A("Spade", "A"),
    HEART_2("Heart", "2"),
    HEART_3("Heart", "3"),
    HEART_4("Heart", "4"),
    HEART_5("Heart", "5"),
    HEART_6("Heart", "6"),
    HEART_7("Heart", "7"),
    HEART_8("Heart", "8"),
    HEART_9("Heart", "9"),
    HEART_10("Heart", "10"),
    HEART_J("Heart", "J"),
    HEART_Q("Heart", "Q"),
    HEART_K("Heart", "K"),
    HEART_A("Heart", "A"),
    CLUB_2("Club", "2"),
    CLUB_3("Club", "3"),
    CLUB_4("Club", "4"),
    CLUB_5("Club", "5"),
    CLUB_6("Club", "6"),
    CLUB_7("Club", "7"),
    CLUB_8("Club", "8"),
    CLUB_9("Club", "9"),
    CLUB_10("Club", "10"),
    CLUB_J("Club", "J"),
    CLUB_Q("Club", "Q"),
    CLUB_K("Club", "K"),
    CLUB_A("Club", "A");


    private final String suit;
    private final String value;

    Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
    }

    public String getSuit() {
        return suit;
    }

    public String getValue() {
        return value;
    }

    public int getScore() {
        switch (value) {
            case "J": return 11;
            case "Q": return 12;
            case "K": return 13;
            case "A": return 1;
            default: return Integer.parseInt(value);
        }
    }
}
