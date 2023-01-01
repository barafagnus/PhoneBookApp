package ru.vysokov.phonebook;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.telephony.TelephonyManager;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

public class PhoneReceiver extends BroadcastReceiver {
    private LinkedList<Subscriber> subscriberList = CallHistoryActivity.getSubscriberList();

    @Override
    public void onReceive(Context context, Intent intent) {
        String incNum = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
        String incState = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);

        String dateHms = new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime());
        String dateDmy = new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime());

        if (incNum != null & !incState.equals("RINGING")) {
            Subscriber newSubscriber = new Subscriber(incState, incNum, dateHms, dateDmy);
            subscriberList.add(newSubscriber);
            CallHistoryActivity.setSubscriberList(subscriberList);

            SQLiteDatabase database = new DBHelper(context.getApplicationContext()).getWritableDatabase();
            database.execSQL("INSERT INTO subscriber(id, state, phone, timeFormat, dayFormat) VALUES ('" + newSubscriber.getId() + "', '" + incState + "', '" + incNum + "', '"+ dateHms + "', '" + dateDmy + "')");
            database.close();
        }
    }
}
