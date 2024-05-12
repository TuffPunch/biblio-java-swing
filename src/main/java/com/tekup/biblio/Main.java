package com.tekup.biblio;

import com.tekup.biblio.dao.AuthDao;
import com.tekup.biblio.gui.LoginGUI;
import com.tekup.biblio.models.Admin;
import com.tekup.biblio.models.User;

import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
        Admin admin = new Admin(new User(-1, "admin", "admin", "admin"));
        AuthDao adao = new AuthDao();
        User u = adao.login(admin.getUsername(), admin.getPasswordHash());
        if ( u == null)
            adao.createAdmin(admin);

        new LoginGUI();
    }
}
