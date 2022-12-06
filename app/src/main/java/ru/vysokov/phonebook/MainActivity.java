package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity{
    private static List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageNewUser = findViewById(R.id.newUser);
        Intent newUserActivity = new Intent(this, NewUserActivity.class);

        imageNewUser.setOnClickListener(view -> startActivity(newUserActivity));

        ListView mainList = findViewById(R.id.mainList);
        users = NewUserActivity.getUserList();
        PhoneAdapter phoneAdapter = new PhoneAdapter(this, users);
        mainList.setAdapter(phoneAdapter);

        System.out.println(users);



//        String userData = user.toString();
//        System.out.println("USERDATA\n" + userData);
//        buttonWrite.setOnClickListener(view -> {
//            try {
//                FileReadWrite.writeFile(userData);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//
//        buttonRead.setOnClickListener(view -> {
//            try {
//                FileReadWrite.readFile();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });

//        try {
//            JsonReadWrite.writeJson(user);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            JsonReadWrite.readJson();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

    }

    public static List<User> setUsers() {
        return users;
    }
}