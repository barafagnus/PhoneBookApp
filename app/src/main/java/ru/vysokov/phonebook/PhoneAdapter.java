package ru.vysokov.phonebook;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.LinkedList;
import java.util.List;

public class PhoneAdapter extends ArrayAdapter<User> {
    private static int userPos;
    private Context context;
    private LinkedList<User> users;
    public PhoneAdapter(Context context, List<User> users) {
        super(context, R.layout.item, users);
        this.context = context;
        this.users = (LinkedList<User>) users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item, parent, false);

        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(this.users.get(position).getName());

        TextView phone = (TextView) view.findViewById(R.id.phone);
        phone.setText(this.users.get(position).getPhone());

        ImageView imageView = (ImageView) view.findViewById(R.id.avatar);
        if (this.users.get(position) instanceof Company) {
            imageView.setImageResource(R.drawable.ic_company);
        }
        else if (this.users.get(position) instanceof Individual){
            imageView.setImageResource(R.drawable.ic_avatar);
        }
        else {
            imageView.setImageResource(R.drawable.mobile_contact);
        }


        LinearLayout userPosition = (LinearLayout) view.findViewById(R.id.userPosition);

        userPosition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userInfoActivity = new Intent(context, UserInfoActivity.class);
                setUserPos(position);
                if (users.get(position) instanceof Company) {
                    userInfoActivity.putExtra("entity", "company");
                    userInfoActivity.putExtra("info1", ((Company) users.get(position)).getName());
                    userInfoActivity.putExtra("info2", ((Company) users.get(position)).getPhone());
                    userInfoActivity.putExtra("info3", ((Company) users.get(position)).getAddress());
                    userInfoActivity.putExtra("info4", ((Company) users.get(position)).getMail());
                    userInfoActivity.putExtra("info5", ((Company) users.get(position)).getActivity());
                    userInfoActivity.putExtra("info6", ((Company) users.get(position)).getStaff());
                }

                else if (users.get(position) instanceof Individual) {
                    userInfoActivity.putExtra("entity", "individual");
                    userInfoActivity.putExtra("info1", ((Individual) users.get(position)).getName());
                    userInfoActivity.putExtra("info2", ((Individual) users.get(position)).getPhone());
                    userInfoActivity.putExtra("info3", ((Individual) users.get(position)).getAddress());
                    userInfoActivity.putExtra("info4", ((Individual) users.get(position)).getAge());
                    userInfoActivity.putExtra("info5", ((Individual) users.get(position)).getEducation());
                    userInfoActivity.putExtra("info6", ((Individual) users.get(position)).getProfession());
                }

                else if (users.get(position) instanceof MobileContact) {
                    userInfoActivity.putExtra("entity", "mobilecontact");
                    userInfoActivity.putExtra("info1", (users.get(position)).getName());
                    userInfoActivity.putExtra("info2", (users.get(position)).getPhone());
                }

                context.startActivity(userInfoActivity);
            }
        });

        return view;
    }

    public static int getUserPos() {
        return userPos;
    }

    public void setUserPos(int userPos) {
        this.userPos = userPos;
    }
}
