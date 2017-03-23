package com.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by jonas on 01.03.17.
 */

public class GetInfo {

    public static void main(String[] args) throws IOException {
        String url="http://www.ntnu.no/studier/mting/miljo";
        Document document = Jsoup.connect(url).get();

        Elements el = document.getElementsByClass("innholdstekst");
        Document doc = Jsoup.parse(el.get(0).toString());
        System.out.println(doc.text());
        
    }

}
