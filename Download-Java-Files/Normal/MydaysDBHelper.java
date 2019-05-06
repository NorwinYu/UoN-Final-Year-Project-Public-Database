package com.msproject.myhome.mydays;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class MydaysDBHelper extends SQLiteOpenHelper {
    public MydaysDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS MyDays (date TEXT , eventNo INTEGER , categoryName Text, eventContent Text,primary Key(date,eventNo))");
    }

    @Override
    public  void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        onCreate(db);
    }

    public void insert(String date, int eventNo, String categoryName, String eventContent){
        SQLiteDatabase db = getWritableDatabase(); //DB 오픈
        Cursor cursor = db.rawQuery("SELECT count(*) FROM MyDays where date="+date+" and eventNo="+eventNo,null);

        cursor.moveToNext();
        int exist = cursor.getInt(0);

        if(exist == 1){
            update(date,eventNo,categoryName,eventContent);

        }
        else {
            ContentValues values = new ContentValues();
            values.put("date", date);
            values.put("eventNo", eventNo);
            values.put("categoryName", categoryName);
            values.put("eventContent", eventContent);
            db.insert("MyDays", null, values);

        }
//        String insertSql = "INSERT INTO MyDays VALUES("
//        db.execSQL();
    }

    public void update(String date, int eventNo, String categoryName,String eventContent){
        SQLiteDatabase db = getWritableDatabase();
        Log.d("update==", categoryName);
        String updateSql = "Update MyDays SET categoryName ="+'"'+categoryName+'"'+",eventContent = "+'"'+eventContent+'"'+"where date="+'"'+date+'"'+" and eventNo="+eventNo;
        db.execSQL(updateSql);
    }

    public  void delete(String date,int eventNo){
        SQLiteDatabase db = getWritableDatabase();
        String deleteSql = "delete from MyDays where date ="+'"'+date+'"'+" and eventNo="+eventNo;
        db.execSQL(deleteSql);
        db.close();
    }

    public ArrayList<Event> getResult(String date){
        ArrayList<Event> events = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MyDays where date="+date, null);

        while(cursor.moveToNext()){
            events.add(new Event(cursor.getInt(1),cursor.getString(2),cursor.getString(3)));
        }
        db.close();
        return events;
    }

    public ArrayList<Event> getEvents(String date,int startNo){
        ArrayList<Event> events = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Log.d("time==", date + startNo);
        Cursor cursor = db.rawQuery("SELECT * FROM MyDays where date="+date+" and eventNo Between "+startNo+" and "+(startNo+5),null);

        while (cursor.moveToNext()){
            events.add(new Event(cursor.getInt(1),cursor.getString(2),cursor.getString(3)));
            Log.d("date==", cursor.getString(0));
        }
        db.close();
        return events;
    }

    public void clearDB(){
        SQLiteDatabase db =getWritableDatabase();
        db.execSQL("DELETE FROM MyDays");
        db.close();
    }
}
