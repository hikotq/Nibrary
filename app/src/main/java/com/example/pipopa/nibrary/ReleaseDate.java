package com.example.pipopa.nibrary;

/**
 * Created by pipopa on 2017/08/30.
 */

public class ReleaseDate {

    private int year;
    private int month;

    public ReleaseDate(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public ReleaseDate(String str) {
        this.year = -1;
        this.month = -1;

        String split_str[] = str.split(".");
        this.year = Integer.valueOf(split_str[0]);
        if (split_str.length >= 2) {
            this.month = Integer.valueOf(split_str[1]);
        }
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    @Override
    public String toString() {
        return Integer.toString(this.year) + "/" + String.format("%02d", this.month);
    }
}
