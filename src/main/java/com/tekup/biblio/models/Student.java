package com.tekup.biblio.models;

public class Student extends User{
    private String name;
    private String contact;
    public Student(User u, String name, String contact) {
        super(u.getUserId(), u.getUsername(), u.getPasswordHash(), "student");
        this.contact = contact;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }
}
