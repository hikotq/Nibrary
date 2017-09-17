package com.example.pipopa.nibrary;

import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pipopa on 2017/08/27.
 */

public class BookAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    ArrayList<Book> bookList;

    public BookAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setbookList(ArrayList<Book> bookList) {
        this.bookList = bookList;
    }

    @Override
    public int getCount() {
        return bookList.size();
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return bookList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.book,parent,false);
        ((TextView)convertView.findViewById(R.id.title)).setText(bookList.get(position).getTitle());
        ((TextView)convertView.findViewById(R.id.author)).setText(bookList.get(position).getAuthor());
        ((TextView)convertView.findViewById(R.id.release_date)).setText(bookList.get(position).getRelease_date().toString());

        String place = bookList.get(position).getPlace();
        if(!place.isEmpty()) { place = "配下場所: " + place; }
        ((TextView)convertView.findViewById(R.id.place)).setText(place);
        return convertView;
    }

    public void refresh(ArrayList<Book> bookList) {
        this.bookList.clear();
        this.bookList = (ArrayList<Book>) bookList.clone();
        notifyDataSetChanged();
    }


}
