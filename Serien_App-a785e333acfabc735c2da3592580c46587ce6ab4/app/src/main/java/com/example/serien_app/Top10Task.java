package com.example.serien_app;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.transform.Result;

/**
 * Async Task um die Top20 von der Startseite zu holen.
 * Wird im Home Fragment angezeigt.
 */

public class Top10Task extends AsyncTask<URL, Void, Result> {


    @Override
    protected Result doInBackground(URL... urls) {
        try {
            parseThis(urls[0].toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



    private static void parseThis(String url) throws IOException {

        Document document = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(10000)
                .post();

        Elements links = document.select("a[href]");

        ArrayList<Item> arrayList = new ArrayList<>();

        for (Element element : links) {
            if (element.toString().contains("itemprop=\"url\"")) {

                String s = element.toString();
                String[] res = s.split("\"");

                String s1 = res[16];
                String[] res1 = s1.split("<span>");
                String s2 = res1[1];
                String[] res2 = s2.split("</span>");
                String s3 = res2[0];
                String[] res3 = s3.split(",");

                String kategorie = res3[0].trim();
                String jahr = res3[1].trim();
                String titel = res[11];
                String bildLink = res[9];
                String itemLink = "www.werstreamt.es/" + res[1];

                arrayList.add(new Item(titel, bildLink, itemLink, kategorie + ", " + jahr, kategorie));
            }
        }
        Listen.setTopList(arrayList);
    }


    @Override
    protected void onPostExecute(Result result) {
        super.onPostExecute(result);
    }
}
