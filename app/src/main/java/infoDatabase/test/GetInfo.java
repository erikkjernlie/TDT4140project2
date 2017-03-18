package test;

import android.provider.DocumentsContract;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * Created by jonas on 17.03.2017.
 */

public class GetInfo {


    public static void main (String[] args) throws IOException {
        String url ="http://www.ntnu.edu/studies/mting";
        Document document = Jsoup.connect(url).get();
        Elements el = document.getElementsByClass("innholdstekst");
        Document doc = Jsoup.parse(el.get(0).toString());
        System.out.println(doc.text());
    }
}

