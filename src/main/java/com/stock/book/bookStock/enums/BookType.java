package com.stock.book.bookStock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BookType {
    ACTION_AND_ADVENTURE("Action and Adventure"),
    CLASSICS("Classics"),
    FANTASY("Fantasy"),
    SCI_FI("sci-fi"),
    HORROR("Horror"),
    HISTORICAL("HistoricaL"),
    LITERARY("Literary ");

    private final String description;
}
