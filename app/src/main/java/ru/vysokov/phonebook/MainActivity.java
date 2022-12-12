package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import java.util.LinkedList;


public class MainActivity extends AppCompatActivity{
    private static LinkedList<User> users = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageNewUser = findViewById(R.id.newUser);
        Intent newUserActivity = new Intent(this, NewUserActivity.class);
        imageNewUser.setOnClickListener(view -> startActivity(newUserActivity));

        Intent settingsActivity = new Intent(this, SettingsActivity.class);
        ImageView settings = (ImageView) findViewById(R.id.settings);
        settings.setOnClickListener(view -> startActivity(settingsActivity));

        if (users.isEmpty()) {
            SQLiteDatabase database = new DBHelper(this).getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM individual;", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                users.add(new Individual(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
                cursor.moveToNext();
            }
            cursor.close();

            Cursor cursor2 = database.rawQuery("SELECT * FROM company;", null);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                users.add(new Company(cursor2.getString(1), cursor2.getString(2), cursor2.getString(3), cursor2.getString(4), cursor2.getString(5), cursor2.getString(6)));
                cursor2.moveToNext();
            }
            cursor.close();
        }

        ListView mainList = findViewById(R.id.mainList);
        PhoneAdapter phoneAdapter = new PhoneAdapter(this, users);
        mainList.setAdapter(phoneAdapter);

//        Button checkDB1 = (Button) findViewById(R.id.checkDB1);
//        Button checkDB2 = (Button) findViewById(R.id.checkDB2);
//
//        checkDB1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SQLiteDatabase database = new DBHelper(getApplicationContext()).getReadableDatabase();
//                Cursor cursor = database.rawQuery("SELECT * FROM individual;", null);
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    cursor.moveToNext();
//                }
//                cursor.close();
//            }
//        });
//
//        checkDB2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SQLiteDatabase database = new DBHelper(getApplicationContext()).getReadableDatabase();
//                Cursor cursor = database.rawQuery("SELECT * FROM company;", null);
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    System.out.println(cursor.getInt(0));
//                    System.out.println("User: " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getString(6));
//                    //users.add(new Individual(cursor.getString(1), cursor.getString(2), null, null, null, null));
//                    System.out.println(users);
//                    cursor.moveToNext();
//                }
//                cursor.close();
//            }
//        });
    }

    public static LinkedList<User> setUsers() {
        return users;
    }

    public static LinkedList<User> setUsers(LinkedList users) {
        return users;
    }

    public static LinkedList<User> getUsers() {
        return users;
    }
}