/*  GetInfo
 *
 *  Using Jsoup to retrieve information from the official ntnu-sites.
 *
 *  Created by Jonas Sagild
 *  Copyright © uniBOT
 */

package com.example.erikkjernlie.tdt4140project;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


public class GetInfo {
    public String getBasicInformation(String study) {
        String url = "http://www.ntnu.edu/studies/"; // adress
        url += study; // e.g. study = mting

        Document document = null;
        try {
            document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements el = document.getElementsByClass("innholdstekst");
        Document doc = Jsoup.parse(el.get(0).toString());

        return doc.text();
    }
}
