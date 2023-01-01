package ru.vysokov.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "PHONES", null, 7);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE individual (id NUMBER, name TEXT, phone TEXT, address TEXT, age NUMBER, education TEXT, profession TEXT)");
        db.execSQL("CREATE TABLE company (id NUMBER, name TEXT, phone TEXT, address TEXT, mail TEXT, activity TEXT, staff TEXT)");
        db.execSQL("CREATE TABLE mobilecontact (id NUMBER, name TEXT, phone TEXT)");
        db.execSQL("CREATE TABLE subscriber (id NUMBER, state TEXT, phone TEXT, timeFormat TEXT, dayFormat TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS individual;");
        db.execSQL("DROP TABLE IF EXISTS company;");
        db.execSQL("DROP TABLE IF EXISTS mobilecontact;");
        db.execSQL("DROP TABLE IF EXISTS subscriber;");
        onCreate(db);
    }
}
