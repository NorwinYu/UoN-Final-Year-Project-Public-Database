package com.msproject.myhome.mydays;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static android.graphics.Color.*;

public class CategoryGridAdapter extends BaseAdapter {
    ArrayList<Category> categories;
    Context context;
    CategoryGridAdapter gridAdapter;

    public CategoryGridAdapter(ArrayList<Category> categories, Context context){
        this.categories = categories;
        this.context = context;
        gridAdapter = this;
    }
    @Override
    public int getCount() {
        return categories.size();
    }

    private boolean exist(Category category){
        for(int i = 0; i < categories.size(); i++){
            if(categories.get(i).getCategoryName().equals(category.categoryName)){
//                modify(category, i);
                return true;
            }
        }
        return false;
    }

    public void add(Category category){
        if(exist(category)){
            return;
        }
        categories.add(category);
    }
    public void modify(Category category, int index){
        categories.set(index, category);
    }

    public void delete(int index){
        categories.remove(index);
    }

    @Override
    public Object getItem(int position) {
        return categories.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item,parent,false);
        TextView categoryName = view.findViewById(R.id.category_name);
        LinearLayout colorView = view.findViewById(R.id.color_view);

        categoryName.setText(categories.get(position).getCategoryName());

        colorView.setBackgroundColor(Color.parseColor(categories.get(position).getColor()));

        return view;
    }

}
