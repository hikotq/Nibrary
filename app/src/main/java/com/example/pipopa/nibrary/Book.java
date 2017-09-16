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
    private boolean isLendable = false;


    Book(String title, String author, String publisher, ReleaseDate release_date, String bookUrl) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_date = release_date;
        this.bookUrl = bookUrl;
    }

    Book(String title, String author, String publisher, ReleaseDate release_date, String bookUrl, boolean isLendable) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_date = release_date;
        this.bookUrl = bookUrl;
        this.isLendable = isLendable;
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

}