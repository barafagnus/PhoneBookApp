package ru.vysokov.phonebook;

public class Subscriber {
    private int id;
    private String state;
    private String phone;
    private String timeFormat;
    private String dayFormat;

    public Subscriber(String state, String phone, String timeFormat, String dayFormat) {
        ENV.ID++;
        this.id = ENV.ID;
        this.state = state;
        this.phone = phone;
        this.timeFormat = timeFormat;
        this.dayFormat = dayFormat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String name) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTimeFormat() {
        return timeFormat;
    }

    public void setTimeFormat(String timeFormat) {
        this.timeFormat = timeFormat;
    }

    public String getDayFormat() {
        return dayFormat;
    }

    public void setDayFormat(String dayFormat) {
        this.dayFormat = dayFormat;
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", state='" + state + '\'' +
                ", phone='" + phone + '\'' +
                ", timeFormat='" + timeFormat + '\'' +
                ", dayFormat='" + dayFormat + '\'' +
                '}';
    }
}
