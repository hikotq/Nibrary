package com.example.pipopa.nibrary;

/**
 * Created by pipopa on 2017/08/27.
 */

public class Book {
    long id;
    private String title;
    private String author;
    private String publisher;
    private ReleaseDate release_date;
    private String bookUrl;
    private boolean isLendable;
    private String place;


    Book(String title, String author, String publisher, ReleaseDate release_date, String bookUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_date = release_date;
        this.bookUrl = bookUrl;
        this.isLendable = false;
        this.place = "情報科学";
    }

    Book(String title, String author, String publisher, ReleaseDate release_date, String bookUrl, boolean isLendable, String place) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_date = release_date;
        this.bookUrl = bookUrl;
        this.isLendable = isLendable;
        this.place = place;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public ReleaseDate getRelease_date() {
        return release_date;
    }

    public String getBookUrl() {
        return bookUrl;
    }

    public boolean isLendable() {
        return isLendable;
    }

    public String getPlace() { return place; }

}