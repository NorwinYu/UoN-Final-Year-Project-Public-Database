package com.msproject.myhome.mydays;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class EventListAdapter extends BaseAdapter {
    ArrayList<Event> events;
    Context context;
    CategoryDBHelper dbHelper;

    public EventListAdapter(ArrayList<Event> events, Context context){
        this.events = events;
        this.context = context;
        dbHelper = new CategoryDBHelper(context, "CATEGORY.db", null, 1);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return events.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public void setItem(int position, Event event){
        this.events.set(position, event);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_item, parent, false);
        TextView timeTextView = view.findViewById(R.id.event_time);
        TextView categoryName = view.findViewById(R.id.category_name);
        TextView eventContent = view.findViewById(R.id.event_content);
        if(events.get(position).eventNo < 10){
            timeTextView.setText("0"+events.get(position).getEventNo()+":00");
        }
        else{
            timeTextView.setText(events.get(position).getEventNo() + ":00");
        }
        categoryName.setText(events.get(position).getCategoryName());
        eventContent.setText(events.get(position).getEventContent());
        if(events.get(position).getCategoryName() != ""){
            String color = dbHelper.getColor(events.get(position).getCategoryName());
            categoryName.setBackgroundColor(Color.parseColor(color));
            eventContent.setBackgroundColor(Color.parseColor(color));
            categoryName.setTextColor(Color.parseColor("#FFFFFF"));
            eventContent.setTextColor(Color.parseColor("#FFFFFF"));
        }


        return view;
    }
}
