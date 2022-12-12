package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class EditUserActivity extends AppCompatActivity {
    private int currentUserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        Button buttonSave = (Button) findViewById(R.id.buttonSave);

        Intent info = getIntent();
        EditText edit1 = (EditText) findViewById(R.id.edit1);
        EditText edit2 = (EditText) findViewById(R.id.edit2);
        EditText edit3 = (EditText) findViewById(R.id.edit3);
        EditText edit4 = (EditText) findViewById(R.id.edit4);
        EditText edit5 = (EditText) findViewById(R.id.edit5);
        EditText edit6 = (EditText) findViewById(R.id.edit6);

        String entity = info.getStringExtra("entity");
        String info1 = info.getStringExtra("info1");
        String info2 = info.getStringExtra("info2");
        String info3 = info.getStringExtra("info3");
        String info4 = info.getStringExtra("info4");
        String info5 = info.getStringExtra("info5");
        String info6 = info.getStringExtra("info6");

        Intent mainActivity = new Intent(this, MainActivity.class);
        ImageView imageBack = (ImageView) findViewById(R.id.back);

        imageBack.setOnClickListener(view -> startActivity(mainActivity));

        if (entity.equals("individual")) {
            edit1.setHint("Название: " + info1);
            edit2.setHint("Телефон: " + info2);
            edit3.setHint("Адрес: " + info3);
            edit4.setHint("Возраст: " + info4);
            edit5.setHint("Образование: " + info5);
            edit6.setHint("Специальность: " +info6);
        }
        else if (entity.equals("company")) {
            edit1.setHint("Название: " + info1);
            edit2.setHint("Телефон: " + info2);
            edit3.setHint("Адрес: " + info3);
            edit4.setHint("E-mail: " + info4);
            edit5.setHint("Деятельность: " + info5);
            edit6.setHint("Штат: " + info6);
        }

        buttonSave.setOnClickListener(view -> {
            currentUserId = MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId();
            if (entity.equals("company")) {
                Company editCompany = new Company(edit1.getText().toString(),
                        edit2.getText().toString(),
                        edit3.getText().toString(),
                        edit4.getText().toString(),
                        edit5.getText().toString(),
                        edit6.getText().toString());
                editCompany.setId(currentUserId);
                MainActivity.setUsers().set(PhoneAdapter.getUserPos(), editCompany);

                SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
                database.execSQL("UPDATE company SET name = '" + edit1.getText().toString() + "', phone = '" + edit2.getText().toString() + "', address = '" + edit3.getText().toString() + "', mail = '" + edit4.getText().toString() + "', activity = '" + edit5.getText().toString() + "', staff = '" + edit6.getText().toString() + "' WHERE id = '"+ currentUserId +"'");
                database.close();

            }
            else if (entity.equals("individual")) {
                Individual editIndividual = new Individual(edit1.getText().toString(),
                        edit2.getText().toString(),
                        edit3.getText().toString(),
                        edit4.getText().toString(),
                        edit5.getText().toString(),
                        edit6.getText().toString());
                editIndividual.setId(currentUserId);
                MainActivity.setUsers().set(PhoneAdapter.getUserPos(), editIndividual);
            }

            SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
            database.execSQL("UPDATE individual SET name = '" + edit1.getText().toString() + "', phone = '" + edit2.getText().toString() + "', address = '" + edit3.getText().toString() + "', age = '" + edit4.getText().toString() + "', education = '" + edit5.getText().toString() + "', profession =  '" + edit6.getText().toString() + "' WHERE id = '"+ currentUserId +"'");
            database.close();
            startActivity(mainActivity);
        });
    }
}