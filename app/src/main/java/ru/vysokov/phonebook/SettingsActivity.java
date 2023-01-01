package ru.vysokov.phonebook;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.LinkedList;

public class SettingsActivity extends AppCompatActivity {
    private static LinkedList<User> userList = MainActivity.getUsers();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Button buttonClearDB = findViewById(R.id.buttonClearDB);
        Button buttonSynchToContacts = findViewById(R.id.buttonSynchToContacts);
        Button buttonSynchToApp = findViewById(R.id.buttonSynchToApp);

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
                database.execSQL("DELETE FROM mobilecontact");
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

        buttonSynchToContacts.setOnClickListener(view -> {
            SQLiteDatabase database = new DBHelper(this).getReadableDatabase();
            Cursor cursor = database.rawQuery("SELECT * FROM individual;", null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Uri rawContactUri = this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, new ContentValues());
                long id = ContentUris.parseId(rawContactUri);
                ContentValues value = new ContentValues();
                value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                value.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, cursor.getString(1));
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);
                value.clear();
                value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                value.put(ContactsContract.CommonDataKinds.Phone.NUMBER, cursor.getString(2));
                value.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);
                cursor.moveToNext();
            }
            cursor.close();

            Cursor cursor2 = database.rawQuery("SELECT * FROM company;", null);
            cursor2.moveToFirst();
            while (!cursor2.isAfterLast()) {
                Uri rawContactUri = this.getContentResolver().insert(ContactsContract.RawContacts.CONTENT_URI, new ContentValues());
                long id = ContentUris.parseId(rawContactUri);
                ContentValues value = new ContentValues();
                value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
                value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                value.put(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, cursor2.getString(1));
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);
                value.clear();
                value.put(ContactsContract.RawContacts.Data.RAW_CONTACT_ID, id);
                value.put(ContactsContract.RawContacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                value.put(ContactsContract.CommonDataKinds.Phone.NUMBER, cursor2.getString(2));
                value.put(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
                getContentResolver().insert(ContactsContract.Data.CONTENT_URI, value);
                cursor2.moveToNext();
            }
            cursor2.close();
            Toast.makeText(getApplicationContext(), "Синхронизация из справочника в список контактов прошла успешно", Toast.LENGTH_SHORT).show();

        });

        LinkedList<String> phoneNumber = new LinkedList<>();
        LinkedList<String> name = new LinkedList<>();
        buttonSynchToApp.setOnClickListener(view -> {

            ContentResolver contentResolver = getContentResolver();
            Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
            Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            if (cursor.getCount() >= 0) {
                while (cursor.moveToNext()) {
                    int indexName = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                    name.add(cursor.getString(indexName));
                        while (phoneCursor.moveToNext()) {
                            int indexPhoneNumber = phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                            phoneNumber.add(phoneCursor.getString(indexPhoneNumber));
                        }
                    }
                }

            int contactsCount = cursor.getCount();

            MobileContact[] mobileContact = new MobileContact[contactsCount];
            if (getDbMobileContactPhones(this).isEmpty()) {
                for (int i = 0; i <= contactsCount - 1; i++) {
                    mobileContact[i] = new MobileContact(name.get(i), phoneNumber.get(i));
                    SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
                    database.execSQL("INSERT INTO mobilecontact(id, name, phone) VALUES ('" + mobileContact[i].getId() + "', '" + mobileContact[i].getName() + "', '" + mobileContact[i].getPhone() + "')");
                    database.close();
                    userList.add(mobileContact[i]);
                }
            }
            else {
                int dbNumberCount = getDbMobileContactPhones(this).size();
                for (int n = 0; n <= phoneNumber.size() - 1; n++) {
                    if (phoneNumber.size() == 0) {
                        break;
                    }
                    else {
                        for (int m = 0; m <= dbNumberCount - 1; m++) {
                            if (phoneNumber.get(n).equals(getDbMobileContactPhones(this).get(m))) {
                                phoneNumber.remove(n);
                                name.remove(n);
                            }
                        }
                    }
                }

                for (int i = 0; i <= phoneNumber.size() - 1; i++) {
                    mobileContact[i] = new MobileContact(name.get(i), phoneNumber.get(i));
                    SQLiteDatabase database = new DBHelper(getApplicationContext()).getWritableDatabase();
                    database.execSQL("INSERT INTO mobilecontact(id, name, phone) VALUES ('" + mobileContact[i].getId() + "', '" + mobileContact[i].getName() + "', '" + mobileContact[i].getPhone() + "')");
                    database.close();
                    userList.add(mobileContact[i]);
                }
            }
            MainActivity.setUsers(userList);
            Toast.makeText(getApplicationContext(), "Синхронизация контактов в справочник прошла успешно", Toast.LENGTH_SHORT).show();
        });

//        Button btn = findViewById(R.id.btn);
//        btn.setOnClickListener(view -> {
//
//        });
    }


    public static LinkedList<String> getDbMobileContactPhones(Context context) {
        LinkedList<String> dbMobileContactsPhonesList = new LinkedList<>();
        SQLiteDatabase database = new DBHelper(context).getReadableDatabase();

        Cursor cursor3 = database.rawQuery("SELECT * FROM mobilecontact;", null);
        cursor3.moveToFirst();
        while (!cursor3.isAfterLast()) {
            dbMobileContactsPhonesList.add(cursor3.getString(2));
            cursor3.moveToNext();
        }
        cursor3.close();
        return dbMobileContactsPhonesList;
    }
}