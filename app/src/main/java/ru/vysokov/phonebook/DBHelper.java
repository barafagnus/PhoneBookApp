package ru.vysokov.phonebook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "PHONES", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE individual (id NUMBER, name TEXT, phone TEXT, address TEXT, age NUMBER, education TEXT, profession TEXT)");
        db.execSQL("CREATE TABLE company (id NUMBER, name TEXT, phone TEXT, address TEXT, mail TEXT, activity TEXT, staff TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS individual;");
        db.execSQL("DROP TABLE IF EXISTS company;");
        onCreate(db);
    }
}
