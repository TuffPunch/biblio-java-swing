package com.tekup.biblio.models;

public class Admin  extends User{
    public Admin(User u){
        super(u.getUserId(), u.getUsername(), u.getPasswordHash(), "admin");
    }
}
