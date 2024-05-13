package com.tekup.biblio.dao;

import com.tekup.biblio.models.Admin;
import com.tekup.biblio.models.Student;
import com.tekup.biblio.models.User;

import java.sql.*;

public class AuthDao {
    private static final String url = "jdbc:mysql://localhost:3306/biblio";
    private static final String USERNAME = "biblio";
    private static final String password = "biblio";



    public void createAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO users (userId, username, passwordHash, name, contact, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, admin.getUserId());
            preparedStatement.setString(2, admin.getUsername());
            preparedStatement.setString(3, admin.hashPassword(admin.getPasswordHash()));
            preparedStatement.setString(4, null);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, admin.getRole());
            preparedStatement.executeUpdate();
        }
    }

    public User getUserHashPassword(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String pass = resultSet.getString("passwordHash");
                String role = resultSet.getString("role");
                return new User(0, username, pass, role);
            } else {
                return null; // User not found
            }
        }
    }

    public User login(String username, String password) throws SQLException {
        User user = getUserHashPassword(username);
        if (!user.verifyPassword(password))
            return null;

        if (user.getRole().equals("student"))
            return new StudentDao().getStudentByUsername(username);
        else
            return new Admin(new User(-1, username, "", user.getRole()));
    }


}
