package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonClearDB = findViewById(R.id.buttonClearDB);

        ImageView imageBack = (ImageView) findViewById(R.id.back);
        Intent mainActivity = new Intent(this, MainActivity.class);
        imageBack.setOnClickListener(view -> startActivity(mainActivity));

        buttonClearDB.setOnClickListener(view -> {
            AlertDialog.Builder abuilder = new AlertDialog.Builder(SettingsActivity.this);
            abuilder.setTitle("Сбросить все данные");
            abuilder.setMessage("Вы уверены?");

            abuilder.setPositiveButton("Да", (dialog, which) -> {
                SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
                database.execSQL("DELETE FROM individual");
                database.execSQL("DELETE FROM company");
                database.close();

                LinkedList userList = MainActivity.getUsers();
                userList.clear();
                MainActivity.setUsers(userList);
                dialog.dismiss();

                Toast toast = Toast.makeText(getApplicationContext(), "Данные успешно удалены", Toast.LENGTH_SHORT);
                toast.show();
            });

            abuilder.setNegativeButton("Нет", (dialog, which) -> dialog.cancel());
            AlertDialog alert = abuilder.create();
            alert.show();
        });
    }
}