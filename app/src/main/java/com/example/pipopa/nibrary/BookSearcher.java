package com.example.pipopa.nibrary;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Created by pipopa on 2017/08/30.
 */

public class BookSearcher {

    public static Elements acquisition_element() throws IOException {
        int kscode = 34;
        String keyword = "vim";
        String url = String.format(
                "https://libopac3-c.nagaokaut.ac.jp/opac/opac_search/?kscode=0%d&lang=0&amode=2&appname=&version=&kywd=%s",
                kscode, keyword);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";

        Document document = Jsoup.connect(url).userAgent(userAgent).get();
        //System.out.println(document.toString());
        Elements elements = document.select(".slDet");
        return elements;
    }

    public static ArrayList<Book> search_book() throws IOException {
        Elements elements = acquisition_element();
        ArrayList<Book> books = new ArrayList<Book>();
        for (Element element : elements) {
            if (element.select(".tit_name") == null) {
                continue;
            }
            String title_and_author[] = element.select("span.tit_name").text().split("/");
            String title = title_and_author[0];
            String author = title_and_author[1];
            String publisher_and_release_date[] = element.select(".txt_crea").text().split(",");
            String publisher = publisher_and_release_date[0];
            ReleaseDate release_date = new ReleaseDate(publisher_and_release_date[1]);
            Book book = new Book(title, author, publisher, release_date);
            books.add(book);
        }
        return books;
    }
}
