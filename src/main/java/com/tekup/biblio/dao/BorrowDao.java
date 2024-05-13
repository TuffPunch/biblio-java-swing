package com.tekup.biblio.dao;

import com.tekup.biblio.models.Borrow;

import java.sql.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class BorrowDao {
    private static final String url = "jdbc:mysql://localhost:3306/biblio";
    private static final String USERNAME = "biblio";
    private static final String password = "biblio";

    private final StudentDao studentdao = new StudentDao();

    private final BookDao bookdao = new BookDao();

    private final Connection connection = DriverManager.getConnection(url, USERNAME, password);

    public BorrowDao() throws SQLException {
    }


    public void saveBorrow(Borrow borrow) throws Exception {
        String sql = "INSERT INTO borrow (studentId, bookId, borrowDate) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, borrow.getStudent().getUserId());
        statement.setInt(2, borrow.getBook().getBookId());
        statement.setDate(3, new Date(borrow.getBorrowDate().getTime()));
        statement.executeUpdate();
        statement.close();
    }

    public void updateBorrow(int borrowId, Date returnDate) throws Exception {
        String sql = "UPDATE borrow SET returnDate = ? WHERE borrowId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setDate(1, returnDate);
        statement.setInt(2, borrowId);
        statement.executeUpdate();
        statement.close();

    }

    public void deleteBorrow(Borrow borrow) throws Exception {
        String sql = "DELETE FROM borrow WHERE borrowId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, borrow.getBorrowId());
        statement.executeUpdate();
        statement.close();
    }

    public Borrow getBorrowById(int borrowId) throws Exception {
        String sql = "SELECT * FROM borrow WHERE borrowId = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, borrowId);
        ResultSet resultSet = statement.executeQuery();

        Borrow borrow = null;
        if (resultSet.next()) {
            borrow = new Borrow(
                    resultSet.getInt("borrowId"),
                    studentdao.getStudentById(resultSet.getInt("studentId")),
                    bookdao.getBookById(resultSet.getInt("bookId")),
                    resultSet.getDate("borrowDate"),
                    resultSet.getDate("returnDate")
            );
        }
        resultSet.close();
        statement.close();
        return borrow;
    }

    public ArrayList<Borrow> getAllBorrows() throws Exception {
        String sql = "SELECT * FROM borrow";
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Borrow> borrows = new ArrayList<>();
        while (resultSet.next()) {
            Borrow borrow = new Borrow(
                    resultSet.getInt("borrowId"),
                    studentdao.getStudentById(resultSet.getInt("studentId")),
                    bookdao.getBookById(resultSet.getInt("bookId")),
                    resultSet.getDate("borrowDate"),
                    resultSet.getDate("returnDate")
            );
            borrows.add(borrow);
        }
        resultSet.close();
        statement.close();
        return borrows;
    }

    public ArrayList<Borrow> getStudentBorrows(int studentId) throws Exception {
        String sql = "SELECT * FROM borrow WHERE studentId = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, studentId);
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Borrow> borrows = new ArrayList<>();
        while (resultSet.next()) {
            Borrow borrow = new Borrow(
                    resultSet.getInt("borrowId"),
                    studentdao.getStudentById(resultSet.getInt("studentId")),
                    bookdao.getBookById(resultSet.getInt("bookId")),
                    resultSet.getDate("borrowDate"),
                    resultSet.getDate("returnDate")
            );
            borrows.add(borrow);
        }
        resultSet.close();
        statement.close();
        return borrows;
    }

}
