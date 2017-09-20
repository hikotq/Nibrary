package com.example.pipopa.nibrary;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


/**
 * Created by pipopa on 2017/08/30.
 */

public class BookSearcher {

    static String baseUrl = "https://libopac3-c.nagaokaut.ac.jp";
    private Map<String, String> cookies;
    private int kousenCode;

    public BookSearcher(int kousenCode) {
        String url = String.format(BookSearcher.baseUrl + "/opac/opac_search/?kscode=%03d", kousenCode);
        Connection.Response res = null;
        try {
            res = Jsoup.connect(url).execute();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            System.exit(1);
        }
        this.cookies = res.cookies();
        this.kousenCode = kousenCode;
    }

    public ArrayList<Book> searchBook(String keyword) throws IOException {
        String title = "";
        String author = "";
        String publisher = "";
        ReleaseDate releaseDate = new ReleaseDate();

        Elements elements = fetchBookListElements(keyword);
        ArrayList<Book> books = new ArrayList<>();
        for (Element element : elements) {
            if (element.select(".tit_name") == null) {
                continue;
            }

            String titleAndAuthor[] = element.select("span.tit_name").text().split("/");
            title = titleAndAuthor[0];
            if (titleAndAuthor.length >= 2) {
                author = titleAndAuthor[1];
            }
            String publisherAndReleaseDate[] = element.select(".txt_crea").text().split(",");
            publisher = publisherAndReleaseDate[0];
            if (publisherAndReleaseDate.length >= 2) {
                releaseDate = new ReleaseDate(publisherAndReleaseDate[1]);
            }
            String bookUrl = element.select("a").first().attr("abs:href");
            Book book = new Book(title, author, publisher, releaseDate, bookUrl);
            books.add(book);
        }
        return books;
    }

    public Elements fetchBookListElements(String keyword) throws IOException {
        String url = String.format(
                BookSearcher.baseUrl + "/opac/opac_search/?kscode=%03d&lang=0&amode=2&appname=&version=&list_disp=100&kywd=%s",
                this.kousenCode, keyword);
        String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36";

        Document document = Jsoup.connect(url).userAgent(userAgent).get();
        ;
        Elements elements = document.select(".slDet");
        return elements;
    }

    public Book takeBook(String url) {
        String title;
        String author = "";
        String publisher;
        ReleaseDate releaseDate;
        boolean isLendable;
        String location = "";

        Document document = null;
        try {
            document = Jsoup.connect(url).cookies(this.cookies).get();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            System.exit(1);
        }
        String titleAndAuthor[] = document.getElementsByClass("bb_ttl").first().text().split("/");
        title = titleAndAuthor[0];
        if (titleAndAuthor.length >= 2) {
            author = titleAndAuthor[1];
        }

        publisher = document.getElementsByClass("PUBLISHER").first().select("span").text();
        releaseDate = new ReleaseDate(document.getElementsByClass("PUBYEAR").select("span").text());
        isLendable = confirmLendingStatus(url);

        try {
            location = document.getElementsByClass("LOCATION").get(1).text();
        } catch (IndexOutOfBoundsException ignored) {
        }

        Book book = new Book(title, author, publisher, releaseDate, url, isLendable, location);
        return book;
    }

    public boolean confirmLendingStatus(String detail_url) {
        Document document;
        try {
            document = Jsoup.connect(detail_url).cookies(this.cookies).get();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            return false;
        }

		/*
         * 貸出状況確認用のURLはページ内に dispStatName('/opac/opac_blstat/', '50', '1', '1',
		 * 'BL8013737', '0', '1', '', '1', '0', '%E8%BF%94%E5%8D%B4%E6%9C%9F%E9%99%90',
		 * 'waiting...'); というような形式で現れるのでカッコ内の文字を抜き出してURLの要素を取り出す。
		 */
        String condition_html = "";
        try {
            condition_html = document.select("td.CONDITION").first().getElementsByTag("script").html();
        } catch (java.lang.NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        Pattern p = Pattern.compile("\\(.+?\\)"); // カッコ内の文字にマッチするパターン
        Matcher m = p.matcher(condition_html);
        if (!m.find()) {
            return false;
        }
        String urlParts[] = m.group().replace("(", "").replace("\'", "").replace(" ", "").split(",");
        String staturl = buildUrl(urlParts[0], urlParts[1], urlParts[2], urlParts[3], urlParts[4], urlParts[5],
                urlParts[6], urlParts[7], urlParts[8], urlParts[9], urlParts[10]);

        try {
            document = Jsoup.connect(BookSearcher.baseUrl + staturl).cookies(this.cookies).get();
        } catch (IOException e) {
            // TODO 自動生成された catch ブロック
            e.printStackTrace();
            return false;
        }
        //貸出されていない場合はhtmlは空なのでtrue, そうでない場合は貸出されているということなのでfalseが返る。
        return document.select("body").first().html().equals("") ? true : false;
    }

    public String buildUrl(String url, String phasecd, String hldstat, String lkcd, String blipkey, String prlndflg,
                           String blcd, String odrno, String bbcd, String lang, String addmsg) {
        String staturl = url;
        staturl = staturl + "?lang=" + lang;
        staturl = staturl + "&amp;phasecd=" + phasecd;
        staturl = staturl + "&amp;hldstat=" + hldstat;
        staturl = staturl + "&amp;lkcd=" + lkcd;
        staturl = staturl + "&amp;blipkey=" + blipkey;
        staturl = staturl + "&amp;prlndflg=" + prlndflg;
        staturl = staturl + "&amp;blcd=" + blcd;
        staturl = staturl + "&amp;odrno=" + odrno;
        staturl = staturl + "&amp;bbcd=" + bbcd;
        staturl = staturl + "&amp;addmsg=" + addmsg;
        return staturl;
    }
}
