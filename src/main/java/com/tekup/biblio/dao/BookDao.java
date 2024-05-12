package com.tekup.biblio.dao;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.tekup.biblio.models.Book;
public class BookDao {

    private static final String url = "jdbc:mysql://localhost:3306/biblio";
    private static final String USERNAME = "biblio";
    private static final String password = "biblio";

    public void addBook(Book book) throws SQLException {
        String sql = "INSERT INTO Books (bookId, title, author, publisher) VALUES (?, ?, ?, ?)";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, book.getBookId());
            preparedStatement.setString(2, book.getTitle());
            preparedStatement.setString(3, book.getAuthor());
            preparedStatement.setString(4, book.getPublisher()); // Added publisher
            preparedStatement.executeUpdate();
        }
    }

    public List<Book> getBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int bookId = resultSet.getInt("bookId");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher"); // Added publisher
                books.add(new Book(bookId, title, author, publisher));
            }
        }
        return books;
    }

    public Book getBookById(int bookId) throws SQLException {
        String sql = "SELECT * FROM Books WHERE bookId = ?";
        try (Connection connection = DriverManager.getConnection(url, USERNAME, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, bookId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int retrievedBookId = resultSet.getInt("bookId");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String publisher = resultSet.getString("publisher"); // Added publisher
                return new Book(retrievedBookId, title, author, publisher);
            } else {
                return null; // Book not found
            }
        }
    }

}
