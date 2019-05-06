package com.msproject.myhome.mydays;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryDBHelper extends SQLiteOpenHelper {
    Context context;
    public CategoryDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS CATEGORY (_id INTEGER PRIMARY KEY AUTOINCREMENT, categoryName TEXT, color TEXT, flag INTEGER);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(String categoryName, String color){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORY where categoryName="+'"'+categoryName+'"', null);
        cursor.moveToNext();
        int exist = cursor.getCount();
        if(exist > 0){
            if(cursor.getInt(3) == 0){
                update(categoryName, color);
            }
            else if(categoryName.equals("수면")){

            }
            else{
                Toast.makeText(context, "이미 존재하는 카테고리입니다.", Toast.LENGTH_SHORT).show();
            }
//            Log.d("exist==", exist + "");
            db.close();
            return;
        }
        db.execSQL("insert into category values(null,"+'"'+categoryName+'"'+","+'"'+color+'"'+",1);");
        db.close();
    }

    public void update(String categoryName, String color){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CATEGORY SET color='"+ color + "', flag = 1 WHERE categoryName='" + categoryName + "';");
        db.close();
    }

    public void delete(String categoryName, String color){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("UPDATE CATEGORY SET color='"+ color + "', flag = 0 WHERE categoryName='" + categoryName + "';");
        db.close();
    }


    public ArrayList<Category> getResult(){
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORY", null);

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getString(1),cursor.getString(2));
            if(cursor.getInt(3) == 0){
                continue;
            }
            categories.add(category);
        }
        db.close();
        return categories;
    }

    public String getColor(String categoryName){
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CATEGORY where categoryName= "+'"'+categoryName+'"',null);
        cursor.moveToNext();

//        Log.d("color==", cursor.getString(2));
        db.close();
        return cursor.getString(2);
    }

    public void clearDB(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM CATEGORY");
    }
}
