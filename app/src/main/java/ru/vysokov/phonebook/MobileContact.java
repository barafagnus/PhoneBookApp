package ru.vysokov.phonebook;

import androidx.annotation.NonNull;

public class MobileContact extends User {
    private int id;

    public MobileContact(String name, String phone) {
        super(name, phone);
        this.id = ENV.ID;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "MobileContact{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                '}';
    }
}
