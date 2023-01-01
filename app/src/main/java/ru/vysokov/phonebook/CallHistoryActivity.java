package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.LinkedList;

public class CallHistoryActivity extends AppCompatActivity {
    private static LinkedList<Subscriber> subscriberList = new LinkedList<>();


    public static LinkedList<Subscriber> getSubscriberList() {
        return subscriberList;
    }

    public static LinkedList<Subscriber> setSubscriberList(LinkedList<Subscriber> subscriberList) {
        return subscriberList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_history);

        ImageView buttonBack = (ImageView) findViewById(R.id.back);
        Intent mainActivity = new Intent(this, MainActivity.class);
        buttonBack.setOnClickListener(view -> startActivity(mainActivity));

        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        buttonDelete.setOnClickListener(view -> {
            SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
            database.execSQL("DELETE FROM subscriber");
            database.close();
            subscriberList.clear();
            Toast toast = Toast.makeText(getApplicationContext(), "Журнал звонков успешно очищен", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(mainActivity);
        });

        if (subscriberList.isEmpty()) {
            SQLiteDatabase database = new DBHelper(this).getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM subscriber;", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                subscriberList.add(new Subscriber(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));
                cursor.moveToNext();
            }
            cursor.close();
        }

        ListView historyList = findViewById(R.id.historyList);
        CallHistoryAdapter callHistoryAdapter = new CallHistoryAdapter(this, subscriberList);
        historyList.setAdapter(callHistoryAdapter);



    }
}