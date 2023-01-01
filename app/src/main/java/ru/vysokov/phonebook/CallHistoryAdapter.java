package ru.vysokov.phonebook;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class CallHistoryAdapter extends ArrayAdapter<Subscriber> {
    private Context context;
    private LinkedList<Subscriber> subscriberList;

    public CallHistoryAdapter(@NonNull Context context, LinkedList<Subscriber> subscriberList) {
        super(context, R.layout.itemcallhistory, subscriberList);
        this.context = context;
        this.subscriberList = subscriberList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.itemcallhistory, parent, false);
        TextView timeHms = (TextView) rowView.findViewById(R.id.timeHms);
        TextView timeDmy = (TextView) rowView.findViewById(R.id.timeDmy);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView phone = (TextView) rowView.findViewById(R.id.phone);
        TextView state = (TextView) rowView.findViewById(R.id.state);

        timeHms.setText(this.subscriberList.get(position).getTimeFormat());
        timeDmy.setText(this.subscriberList.get(position).getDayFormat());

        name.setText("name");
        phone.setText(this.subscriberList.get(position).getPhone());


        if (this.subscriberList.get(position).getState().equals("IDLE")) {
            state.setTextColor(Color.rgb(245, 173, 66));
            state.setText(this.subscriberList.get(position).getState());
        }
        else if (this.subscriberList.get(position).getState().equals("OFFHOOK")) {
            state.setTextColor(Color.rgb(56, 209, 29));
            state.setText(this.subscriberList.get(position).getState());
        }

        return rowView;
    }
}
