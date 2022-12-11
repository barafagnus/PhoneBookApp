package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.util.LinkedList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonClearDB = findViewById(R.id.buttonClearDB);
        buttonClearDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
                database.execSQL("DELETE FROM individual");
                database.execSQL("DELETE FROM company");
                database.close();

                LinkedList userList = MainActivity.getUsers();
                userList.clear();
                MainActivity.setUsers(userList);

            }
        });

        ImageView imageBack = (ImageView) findViewById(R.id.back);
        Intent mainActivity = new Intent(this, MainActivity.class);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivity);
            }
        });

    }

}