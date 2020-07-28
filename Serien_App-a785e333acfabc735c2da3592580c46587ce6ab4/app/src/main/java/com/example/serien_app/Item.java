package com.example.serien_app;

import java.util.ArrayList;

/**
 * Klasse für das Film-Objekt, mit seinen Attributen, Titel, Beschreibung, Bild, Link, Kategorie und Jahr.
 */


public class Item {

    private String title;
    private String description;
    private String image;
    private String link;
    private String categoryAndYear;
    private float rating;
    private ArrayList<String> provider = new ArrayList<>();
    private ArrayList<String> providerlinks = new ArrayList<>();

    public Item() {
    }

    public Item(String title, String image, String link, String description, String categoryAndYear, float rating, ArrayList<String> provider, ArrayList<String> providerlinks) {
        this.title = title;
        this.image = image;
        this.link = link;
        this.description = description;
        this.categoryAndYear = categoryAndYear;
        this.rating = rating;
        this.provider = provider;
        this.providerlinks = providerlinks;
    }


    public Item(String title, String image, String link, String description, String categoryAndYear, float rating) {
        this.title = title;
        this.image = image;
        this.link = link;
        this.description = description;
        this.categoryAndYear = categoryAndYear;
        this.rating = rating;
    }

    public Item(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public Item(String title, String description, String image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public Item(String title, String image, String link, String val) {
        this.title = title;
        this.image = image;
    }

    public Item(String title, String image, String link, String description, String categoryAndYear) {
        this.title = title;
        this.image = image;
        this.link = link;
        this.description = description;
        this.categoryAndYear = categoryAndYear;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCategoryAndYear() {
        return categoryAndYear;
    }

    public void setCategoryAndYear(String categoryAndYear) {
        this.categoryAndYear = categoryAndYear;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public ArrayList<String> getProvider() {
        return provider;
    }

    public void setProvider(ArrayList<String> provider) {
        this.provider = provider;
    }

    public ArrayList<String> getProviderlinks() { return providerlinks; }

    public void setProviderlinks(ArrayList<String> providerlinks) { this.providerlinks = providerlinks; }
}
