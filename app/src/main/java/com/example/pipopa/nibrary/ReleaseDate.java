package com.example.pipopa.nibrary;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by pipopa on 2017/08/30.
 */

public class ReleaseDate {

    private int year;
    private int month;

    public ReleaseDate() {
        this.year = -1;
        this.month = -1;
    }

    public ReleaseDate(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public ReleaseDate(String str) {
        this.year = -1;
        this.month = -1;

        Pattern p = Pattern.compile("\\d{4}(\\.\\d)?");
        Matcher m = p.matcher(str);
        if (!m.find()) {
            return;
        }

        String match_str = m.group();

        String split_str[] = match_str.split("\\.");
        this.year = Integer.valueOf(split_str[0]);
        if (split_str.length >= 2) {
            this.month = Integer.valueOf(split_str[1]);
        }
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    @Override
    public String toString() {
        String year = this.year != -1 ? Integer.toString(this.year) : "";
        String month = this.month != -1 ? String.format("%02d", this.month) : "";
        if (!month.isEmpty()) {
            month = "/" + month;
        }
        return year + month;
    }
}
