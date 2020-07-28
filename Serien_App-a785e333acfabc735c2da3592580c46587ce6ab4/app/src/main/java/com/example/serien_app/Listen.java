package com.example.serien_app;

import java.util.ArrayList;

/**
 * Klasse in der die Arraylisten zwischengespeichert werden.
 * Wird benutzt um die Listen an einem zentralen Punkt zu haben.
 */

public class Listen {

    private static ArrayList<Item> watchList = new ArrayList<>();
    private static ArrayList<Item> exampleList = new ArrayList<>();
    private static ArrayList<Item> topList = new ArrayList<>();
    private static ArrayList<String> searchHistory = new ArrayList<>();


    public static ArrayList<Item> getWatchList() {
        return watchList;
    }

    public static void setWatchList(ArrayList<Item> watchList) {
        Listen.watchList = watchList;
    }

    public static ArrayList<Item> getExampleList() {
        return exampleList;
    }

    public static void setExampleList(ArrayList<Item> exampleList) {
        Listen.exampleList = exampleList;
    }

    public static ArrayList<Item> getTopList() {
        return topList;
    }

    public static void setTopList(ArrayList<Item> topList) {
        Listen.topList = topList;
    }

    public static ArrayList<String> getSearchHistory() {
        return searchHistory;
    }

    public static void setSearchHistory(ArrayList<String> searchHistory) {
        Listen.searchHistory = searchHistory;
    }

}
