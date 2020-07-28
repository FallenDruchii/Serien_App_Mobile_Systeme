package com.example.serien_app;

import android.os.AsyncTask;
import android.os.SystemClock;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * HTML-Daten für direkten Aufruf einer Seite, läd HTML runter und schreibt diese in einen String,
 * hier werden Daten wie Rating, Beschreibung und Streaminganbieter herausgesucht um angezeigt zu werden
 */

public class GetMovieData extends AsyncTask<URL, Void, String> {

    static String movieDesciption;
    static String movieRating;
    static String[] resultNames;
    static String[] splitString;
    static String[] resultLinks;
    static String[] splitProviders;
    static String[] splitRating;
    static String[] resultProviderLinks;
    static String[] resultProviderNames;
    static List<String> streamingLinkList = new ArrayList<>();
    static ArrayList<String> streamingServiceList = new ArrayList<>();
    static List<String> splitStringList = new ArrayList<>();
    static ArrayList<String> providerLinksList = new ArrayList<>();
    static ArrayList<String> ListNames = new ArrayList<>();
    static ArrayList<String> ListLinks = new ArrayList<>();


    @Override
    public String doInBackground(URL... urls) {

        try {
            getDescription(MainActivity.exchangeLink);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void getDescription(String url) throws IOException, MalformedURLException {

        Document doc = Jsoup.connect(url)
                .data("query", "Java")
                .userAgent("Mozilla")
                .cookie("auth", "token")
                .timeout(10000)
                .post();

        movieDesciption = "";
        movieRating = "0";
        resultNames = new String[10];
        splitString = new String[11];
        resultLinks = new String[10];
        splitRating = new String[3];
        streamingServiceList.clear();
        streamingLinkList.clear();
        splitStringList.clear();
        providerLinksList.clear();
        ListNames.clear();
        ListLinks.clear();
/*
        String booleanString;
        boolean isMovie = true;

        booleanString = doc.select("link").toString();
        if (booleanString.contains("<link rel=\"amphtml\" href=\"https://www.werstreamt.es/")) {
            booleanString = booleanString.substring(booleanString.indexOf("<link rel=\"amphtml\" href=\"https://www.werstreamt.es"), booleanString.indexOf("/details"));
            if (booleanString.contains("serie")) {
                isMovie = false;
            } else if (booleanString.contains("film")) {
                isMovie = true;
            }
        }
*/
        movieDesciption = doc.select("p").toString();
        if (movieDesciption.contains("Wir haben noch keine Beschreibung")) {
            movieDesciption = "Hierzu existiert noch keine Beschreibung";
        } else {
            movieDesciption = movieDesciption.substring(movieDesciption.indexOf("<p itemprop=") + 26, movieDesciption.indexOf("</p>"));
            movieDesciption = movieDesciption.replaceAll("\"", "");
            movieDesciption = movieDesciption.replaceAll("<span class=ellips>... \\(weiterlesen\\)</span><span class=more>", "");
            movieDesciption = movieDesciption.replaceAll("</span>", "");
            movieDesciption = movieDesciption.replaceAll("<", "");
            movieDesciption = movieDesciption.replaceAll(">", "");
            movieDesciption = movieDesciption.replaceAll("/", "");
            movieDesciption = movieDesciption.replaceAll("\\u00E4","ae");
            movieDesciption = movieDesciption.replaceAll("\\u00FC","üe");
            movieDesciption = movieDesciption.replaceAll("\\u00F6","oe");
            movieDesciption = movieDesciption.replaceAll("\\u00DF","ss");
            movieDesciption = movieDesciption.replaceAll("\\u2122", "");
            movieDesciption = movieDesciption.replaceAll("\\u00AE", "");
        }

        movieRating = doc.select(".rating").toString();
        splitRating = movieRating.split("</div>");
        movieRating = splitRating[0].substring(splitRating[0].indexOf("data-score") +12,splitRating[0].indexOf("data-score") +15);
        movieRating = movieRating.replaceAll("\" ","");
        System.out.println(movieRating);



/*
        movieRating = doc.select(".rating").get(1).toString();
        movieRating = movieRating.substring(movieRating.indexOf("data-score") + 12, movieRating.indexOf("itemtype="));
        movieRating = movieRating.replace("\"", "");
*/
/*
        movieRating = doc.select("div class").toString();
        movieRating = movieRating.substring(movieRating.indexOf("data-score"), movieRating.indexOf("\" item"));
        System.out.println(movieRating);
        movieRating="4.5";
/*
        if (isMovie) {
            String s = doc.select("form").toString();
            splitString = s.split("form action");
            splitStringList.addAll(Arrays.asList(splitString));

            splitStringList.remove(0);
            splitStringList.remove(splitStringList.size() - 1);

            if (splitStringList.size() == 0) {

                resultLinks[0] = null;

                streamingLinkList.add(resultLinks[0]);
                streamingServiceList.clear();

            } else if (splitStringList.size() > 10) {

                for (int i = 0; i < 10; i++) {
                    splitString[i] = splitStringList.get(i);
                    resultLinks[i] = splitString[i].substring(splitString[i].indexOf("/film/go"), splitString[i].indexOf("\" class"));
                    resultNames[i] = splitString[i].substring(splitString[i].indexOf("value=\"Bei ") + 11, splitString[i].indexOf(" anzeigen"));
                    streamingLinkList.add(resultLinks[i]);
                    streamingServiceList.add(resultNames[i]);
                }
            } else {
                for (int i = 0; i < splitStringList.size(); i++) {
                    splitString[i] = splitStringList.get(i);
                    resultLinks[i] = splitString[i].substring(splitString[i].indexOf("/film/go"), splitString[i].indexOf("\" class"));
                    resultNames[i] = splitString[i].substring(splitString[i].indexOf("value=\"Bei ") + 11, splitString[i].indexOf(" anzeigen"));
                    streamingLinkList.add(resultLinks[i]);
                    streamingServiceList.add(resultNames[i]);
                }
            }
        } else {
            String s = doc.select("h5").toString();
            if (s.contains("class=\"anchorOffset")) {
                splitString = s.split("class=\"anchorOffset");
                splitStringList.addAll(Arrays.asList(splitString));
                splitStringList.remove(0);

                if (splitStringList.size() == 0) {

                    resultLinks[0] = null;
                    resultNames[0] = null;

                    streamingLinkList.add(resultLinks[0]);
                    streamingServiceList.add(resultNames[0]);
                    streamingServiceList.clear();

                } else if (splitStringList.size() > 10) {

                    for (int i = 0; i < 10; i++) {
                        splitString[i] = splitStringList.get(i);
                        resultNames[i] = splitString[i].substring(splitString[i].indexOf(" id=\"") + 5, splitString[i].indexOf("\">"));
                        streamingServiceList.add(resultNames[i]);
                    }
                } else {
                    for (int i = 0; i < splitStringList.size(); i++) {
                        splitString[i] = splitStringList.get(i);
                        resultNames[i] = splitString[i].substring(splitString[i].indexOf(" id=\"") + 5, splitString[i].indexOf("\">"));
                        streamingServiceList.add(resultNames[i]);
                    }
                }
            }
        }
*/
        String providerLinks = doc.select("form").toString();
        int index = 0, fromIndex = 0;
        while((fromIndex = providerLinks.indexOf("<from action",fromIndex)) != -1){
            index++;
            fromIndex++;
        }
        splitProviders = new String[index];
        splitProviders = providerLinks.split("<form action");
        providerLinksList.addAll(Arrays.asList(splitProviders));
        providerLinksList.remove(0);
        providerLinksList.remove(providerLinksList.size()-1);
        splitProviders = new String[providerLinksList.size()];
        for(int i = 0; i<providerLinksList.size(); i++) {
            splitProviders[i] = providerLinksList.get(i);
        }

        for(int i = 0; i<providerLinksList.size(); i++){
            if(splitProviders[i].contains(" \"></form")) {
                splitProviders[i] = splitProviders[i].substring(0, splitProviders[i].indexOf("</form>"));
            }
        }

        resultProviderNames = new String[10];
        resultProviderLinks = new String[10];

        for(int i = 0; i<providerLinksList.size(); i++){
            if(splitProviders[i].contains("Netflix")){
                resultProviderLinks[0]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[0]="https://www.werstreamt.es" + resultProviderLinks[0];
                resultProviderNames[0]="Netflix";
            }else if(splitProviders[i].contains("Amazon")){
                resultProviderLinks[1]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[1]="https://www.werstreamt.es" + resultProviderLinks[1];
                resultProviderNames[1]="Amazon";
            }else if(splitProviders[i].contains("Disney+")){
                resultProviderLinks[2]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[2]="https://www.werstreamt.es" + resultProviderLinks[2];
                resultProviderNames[2]="Disney+";
            }else if(splitProviders[i].contains("Joyn")){
                resultProviderLinks[3]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[3]="https://www.werstreamt.es" + resultProviderLinks[3];
                resultProviderNames[3]="Joyn";
            }else if(splitProviders[i].contains("Maxdome")){
                resultProviderLinks[4]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[4]="https://www.werstreamt.es" + resultProviderLinks[4];
                resultProviderNames[4]="Maxdome";
            }else if(splitProviders[i].contains("Sky Store")){
                resultProviderLinks[5]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[5]="https://www.werstreamt.es" + resultProviderLinks[5];
                resultProviderNames[5]="Sky Store";
            }else if(splitProviders[i].contains("Sky Go")){
                resultProviderLinks[6]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[6]="https://www.werstreamt.es" + resultProviderLinks[6];
                resultProviderNames[6]="Sky Go";
            }else if(splitProviders[i].contains("Google Play")){
                resultProviderLinks[7]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[7]="https://www.werstreamt.es" + resultProviderLinks[7];
                resultProviderNames[7]="Google Play";
            }else if(splitProviders[i].contains("iTunes")){
                resultProviderLinks[8]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[8]="https://www.werstreamt.es" + resultProviderLinks[8];
                resultProviderNames[8]="iTunes";
            }else if(splitProviders[i].contains("Microsoft")){
                resultProviderLinks[9]=splitProviders[i].substring(splitProviders[i].indexOf("=\"")+2,splitProviders[i].indexOf("\" class"));
                resultProviderLinks[9]="https://www.werstreamt.es" + resultProviderLinks[9];
                resultProviderNames[9]="Microsoft";
            }
        }

        ListLinks.addAll(Arrays.asList(resultProviderLinks));
        ListNames.addAll(Arrays.asList(resultProviderNames));
        while(ListNames.remove(null)){}
    }

    @Override
    public void onPostExecute(String result) {
        result = movieDesciption;
        super.onPostExecute(result);
    }

}
