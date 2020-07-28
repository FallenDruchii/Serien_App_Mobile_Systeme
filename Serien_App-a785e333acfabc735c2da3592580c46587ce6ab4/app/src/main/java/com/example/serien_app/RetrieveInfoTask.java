package com.example.serien_app;


import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.transform.Result;

/**
 * Klasse, um einen AsyncTask zu erstellen. Dieser ist dafür verantwortlich, dass die Daten von der Suche nach einer Serie oder einem Film geladen werden.
 * Die Infos werden von werstreamt.es geladen
 * Versuche die Verbindung hier mithilfe von Jsoup aufzubauen
 * Es wird der Titel, Bildlink, Link, Genre und Erscheinungsjahr herausgesucht.
 */
public class RetrieveInfoTask extends AsyncTask<URL, Void, Result> {

    static String[] exchangeNames = new String[18];
    static String[] exchangeLinks = new String[18];
    static String[] exchangePictures = new String[18];
    static String[] exchangeGenreYear = new String[18];
    static ArrayList<Item> arrayList = new ArrayList<>();

    static List<String> exchangeNamesList = new ArrayList<>();

    @Override
    protected Result doInBackground(URL... urls) {

        String TestUrl = urls[0].toString();

        try {
            findLinks(TestUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void findLinks(String url) throws IOException {

        exchangeNamesList.clear();
        arrayList.clear();

        Document doc = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(10000)
                .post();

        String[] showsOrdered = new String[30];
        String[] linksOrdered = new String[18];
        String[] picturesOrdered = new String[18];
        ArrayList<String> genreYearList = new ArrayList<>();

        Set<String> links = new HashSet<>();
        Elements elements = doc.select("a[href]");
        for (Element element : elements) {
            links.add(element.attr("href"));
        }

        int index = 0;
        for (int i = 0; i < elements.size(); i++) {
            String string = doc.select("a[href]").get(i).toString();
            if (string.contains("<a href=\"film/details/")) {
                showsOrdered[index] = string.toString();
                index++;
            } else if (string.contains("<a href=\"serie/details/")) {
                showsOrdered[index] = string.toString();
                index++;
            }

        }

        index--;

        for (int i = 0; i < index * 2; i++) {
            String details = doc.select(".details").get(i).toString();
            if (details.contains("<div class=\"details\">")) {
                String s = details.substring(details.indexOf("<span>") + 6, details.indexOf("</span>"));
                genreYearList.add(s);
            }
        }


        for (int i = 0; i < index; i++) {
            linksOrdered[i] = showsOrdered[i].substring(showsOrdered[i].indexOf("<a href=\"") + 9, showsOrdered[i].indexOf("\" itemprop=\"url\">"));
            picturesOrdered[i] = showsOrdered[i].substring(showsOrdered[i].indexOf("<img itemprop=\"image\" src=\"") + 28, showsOrdered[i].indexOf("\" alt="));
            exchangeLinks[i] = linksOrdered[i];
        }

        for (int i = 0; i < index; i++) {
            String[] nameSplit = exchangeLinks[i].split("/");
            exchangeNames[i] = nameSplit[3];
            exchangeNames[i] = exchangeNames[i].replaceAll("-", " ");

            exchangePictures[i] = picturesOrdered[i].replaceAll("ttps://", "");

            char ch[] = exchangeNames[i].toCharArray();
            for (int j = 0; j < ch.length; j++) {
                ch[j] = Character.toUpperCase(ch[j]);
            }
            String string = new String(ch);
            exchangeNamesList.add(string);
        }

        for (int i = 0; i < genreYearList.size(); i++) {
            exchangeGenreYear[i] = genreYearList.get(i);
        }

        for (int i = 0; i < exchangeNamesList.size(); i++) {

            Item item = new Item();
            item.setImage(exchangePictures[i]);
            item.setTitle(exchangeNamesList.get(i));
            item.setLink(exchangeLinks[i]);
            item.setCategoryAndYear(exchangeGenreYear[i]);
            arrayList.add(item);
        }
    }


    @Override
    protected void onPostExecute(Result result) {

        super.onPostExecute(result);
    }


}