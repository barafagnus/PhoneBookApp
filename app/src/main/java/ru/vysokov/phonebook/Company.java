package ru.vysokov.phonebook;

public class Company extends User {
    private int id;
    private String mail;
    private String activity;
    private String staff;
    public Company(String name, String phone, String address, String mail, String activity, String staff) {
        super(name, phone, address);
        this.id = ENV.ID;
        this.mail = mail;
        this.activity = activity;
        this.staff = staff;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", mail='" + mail + '\'' +
                ", activity='" + activity + '\'' +
                ", staff='" + staff + '\'' +
                '}';
    }
}
