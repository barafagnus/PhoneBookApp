package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.LinkedList;

public class UserInfoActivity extends AppCompatActivity {
    private static LinkedList<User> userList = MainActivity.getUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_useinfo);

        Intent info = getIntent();
        String entity = info.getStringExtra("entity");
        String info1 = info.getStringExtra("info1");
        String info2 = info.getStringExtra("info2");
        String info3 = info.getStringExtra("info3");
        String info4 = info.getStringExtra("info4");
        String info5 = info.getStringExtra("info5");
        String info6 = info.getStringExtra("info6");

        ImageView entityImg = findViewById(R.id.entityImg);
        TextView txtInfo1 = findViewById(R.id.info1);
        TextView txtInfo2 = findViewById(R.id.info2);
        TextView txtInfo3 = findViewById(R.id.info3);
        TextView txtInfo4 = findViewById(R.id.info4);
        TextView txtInfo5 = findViewById(R.id.info5);
        TextView txtInfo6 = findViewById(R.id.info6);

        ImageView buttonCancel = (ImageView) findViewById(R.id.back);
        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
        Button buttonDelete = (Button) findViewById(R.id.buttonDelete);
        Intent mainActivity = new Intent(this, MainActivity.class);
        Intent EditUserActivity = new Intent(this, EditUserActivity.class);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(mainActivity);
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditUserActivity.putExtra("entity", entity);
                EditUserActivity.putExtra("info1", info1);
                EditUserActivity.putExtra("info2", info2);
                EditUserActivity.putExtra("info3", info3);
                EditUserActivity.putExtra("info4", info4);
                EditUserActivity.putExtra("info5", info5);
                EditUserActivity.putExtra("info6", info6);
                startActivity(EditUserActivity);
            }
        });

        if (entity.equals("company")) {
            entityImg.setImageResource(R.drawable.ic_company);
            txtInfo1.setText(info1);
            txtInfo2.setText(info2);
            txtInfo3.setText("Адрес: " + info3);
            txtInfo4.setText("E-mail: " + info4);
            txtInfo5.setText("Деятельность: " + info5);
            txtInfo6.setText("Штат: " + info6);
        }
        else if (entity.equals("individual")) {
            entityImg.setImageResource(R.drawable.ic_avatar);
            txtInfo1.setText(info1);
            txtInfo2.setText(info2);
            txtInfo3.setText("Адрес: " + info3);
            txtInfo4.setText("Возраст: " + info4);
            txtInfo5.setText("Образование: " + info5);
            txtInfo6.setText("Специальность: " + info6);
        }

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();

                if (entity.equals("company")) {
                    database.execSQL("DELETE FROM company WHERE id = '" + MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId() +"'");
                    database.close();
                }
                else if (entity.equals("individual")) {
                    database.execSQL("DELETE FROM individual WHERE id = '" + MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId() +"'");
                    database.close();
                }

                userList.remove(MainActivity.getUsers().get(PhoneAdapter.getUserPos()));
                MainActivity.setUsers(userList);
                startActivity(mainActivity);
            }
        });
    }
}