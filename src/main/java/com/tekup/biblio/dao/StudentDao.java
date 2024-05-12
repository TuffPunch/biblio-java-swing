package com.tekup.biblio.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tekup.biblio.models.Student;
import com.tekup.biblio.models.User;

public class StudentDao {

    private static final String url = "jdbc:mysql://localhost:3306/biblio";
    private static final String USERNAME = "biblio";
    private static final String password = "biblio";

    public void addStudent(Student student) throws SQLException {
        String sql = "INSERT INTO users (userId, username, passwordHash, name, contact, role) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, student.getUserId());
            preparedStatement.setString(2, student.getUsername());
            preparedStatement.setString(3, student.hashPassword(student.getPasswordHash()));
            preparedStatement.setString(4, student.getName());
            preparedStatement.setString(5, student.getContact());
            preparedStatement.setString(6, student.getRole());
            preparedStatement.executeUpdate();
        }
    }

    public List<Student> getStudents() throws SQLException {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM Students";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String username = resultSet.getString("username");
                String passwordHash = "";
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                String role = resultSet.getString("role");
                students.add(new Student(new User(userId, username, passwordHash, role), name, contact));
            }
        }
        return students;
    }

    public Student getStudentByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ? and role = 'student'";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String retrievedUsername = resultSet.getString("username");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                String role = resultSet.getString("role");
                return new Student(new User(userId, retrievedUsername, "", role), name, contact);
            } else {
                return null; // Student not found
            }
        }
    }

    public Student getStudentById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE userId = ? and role = 'student'";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int userId = resultSet.getInt("userId");
                String retrievedUsername = resultSet.getString("username");
                String name = resultSet.getString("name");
                String contact = resultSet.getString("contact");
                String role = resultSet.getString("role");
                return new Student(new User(userId, retrievedUsername, "", role), name, contact);
            } else {
                return null; // Student not found
            }
        }
    }



    // Implement similar methods for getStudentById, updateStudent, and deleteStudent (omitted for brevity)
}

