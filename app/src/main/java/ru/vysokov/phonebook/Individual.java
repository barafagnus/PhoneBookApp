package ru.vysokov.phonebook;

public class Individual extends User {
    private int id;
    private String age;
    private String education;
    private String profession;

    public Individual(String name, String phone, String address, String age, String education, String profession) {
        super(name, phone, address);
        this.id = ENV.ID;
        this.age = age;
        this.education = education;
        this.profession = profession;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public String toString() {
        return "Individual{" +
                "id=" + id +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", address='" + getAddress() + '\'' +
                ", age='" + age + '\'' +
                ", education='" + education + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }


}
