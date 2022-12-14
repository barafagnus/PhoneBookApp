package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
        ImageView imgCallContact = (ImageView) findViewById(R.id.imgCallContact);
        ImageView imgAddContact = (ImageView) findViewById(R.id.imgAddContact);
        ImageView imgDeleteContact = (ImageView) findViewById(R.id.imgDeleteContact);

        Button buttonEdit = (Button) findViewById(R.id.buttonEdit);
        Intent mainActivity = new Intent(this, MainActivity.class);
        Intent EditUserActivity = new Intent(this, EditUserActivity.class);

        buttonCancel.setOnClickListener(view -> startActivity(mainActivity));

        buttonEdit.setOnClickListener(view -> {
            EditUserActivity.putExtra("entity", entity);
            EditUserActivity.putExtra("info1", info1);
            EditUserActivity.putExtra("info2", info2);
            EditUserActivity.putExtra("info3", info3);
            EditUserActivity.putExtra("info4", info4);
            EditUserActivity.putExtra("info5", info5);
            EditUserActivity.putExtra("info6", info6);
            startActivity(EditUserActivity);
        });

        if (entity.equals("company")) {
            entityImg.setImageResource(R.drawable.ic_company);
            txtInfo1.setText(info1);
            txtInfo2.setText(info2);
            txtInfo3.setText("??????????: " + info3);
            txtInfo4.setText("E-mail: " + info4);
            txtInfo5.setText("????????????????????????: " + info5);
            txtInfo6.setText("????????: " + info6);
        }
        else if (entity.equals("individual")) {
            entityImg.setImageResource(R.drawable.ic_avatar);
            txtInfo1.setText(info1);
            txtInfo2.setText(info2);
            txtInfo3.setText("??????????: " + info3);
            txtInfo4.setText("??????????????: " + info4);
            txtInfo5.setText("??????????????????????: " + info5);
            txtInfo6.setText("??????????????????????????: " + info6);
        }

        else if (entity.equals("mobilecontact")) {
            entityImg.setImageResource(R.drawable.ic_avatar);
            txtInfo1.setText(info1);
            txtInfo2.setText(info2);
        }

        imgCallContact.setOnClickListener(view -> {
            Intent call = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + info2));
            startActivity(call);
        });

        imgAddContact.setOnClickListener(view -> {
            Uri rawContactUri = this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, new ContentValues());
            long id = ContentUris.parseId(rawContactUri);
            ContentValues value = new ContentValues();
            value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
            value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
            value.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, info1);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);
            value.clear();
            value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
            value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
            value.put(ContactsContract.CommonDataKinds.Phone.NUMBER, info2);
            value.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
            getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);

            Toast.makeText(getApplicationContext(), "???????????????????????? ???????????????? ?? ???????????? ??????????????????", Toast.LENGTH_SHORT);
        });

        imgDeleteContact.setOnClickListener(view -> {
            SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();

            if (entity.equals("company")) {
                database.execSQL("DELETE FROM company WHERE id = '" + MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId() +"'");
                database.close();
            }
            else if (entity.equals("individual")) {
                database.execSQL("DELETE FROM individual WHERE id = '" + MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId() +"'");
                database.close();
            }
            else if (entity.equals("mobilecontact")) {
                database.execSQL("DELETE FROM mobilecontact WHERE id = '" + MainActivity.getUsers().get(PhoneAdapter.getUserPos()).getId() +"'");
                database.close();
            }

            userList.remove(MainActivity.getUsers().get(PhoneAdapter.getUserPos()));
            MainActivity.setUsers(userList);
            startActivity(mainActivity);
        });

    }
}