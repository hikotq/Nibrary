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



    Book(String title, String author, String publisher, ReleaseDate release_date){
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.release_date = release_date;
    }



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title;}

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ReleaseDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(ReleaseDate release_date) {
        this.release_date = release_date;
    }
}
