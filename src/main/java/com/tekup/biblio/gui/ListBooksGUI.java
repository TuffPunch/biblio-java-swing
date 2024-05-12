package com.tekup.biblio.gui;

import com.tekup.biblio.dao.BookDao;
import com.tekup.biblio.dao.BorrowDao;
import com.tekup.biblio.models.Book;
import com.tekup.biblio.models.Borrow;
import com.tekup.biblio.models.Student;
import com.tekup.biblio.models.User;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListBooksGUI extends JFrame {

    private final BookDao bookDao = new BookDao();
    private final BorrowDao borrowDao = new BorrowDao();

    public ListBooksGUI(String currentUserRole, int userId) throws Exception {
        super("Library Management System - List Books");


        // Get all books from the database
        List<Book> books = bookDao.getBooks();
        ArrayList<Borrow> borrows;

        // Create a JTable to display books
        String[] baseColumns = {"Book ID", "Title", "Author", "Publisher"};
        List<String> columns = new ArrayList<>(List.of(baseColumns));

        // Add "Actions" column conditionally based on role
        columns.add("Actions");
        borrows = borrowDao.getAllBorrows();
//        if (currentUserRole.equals("student")) {
//            borrows = borrowDao.getStudentBorrows(userId);
//        } else {
//            borrows = borrowDao.getAllBorrows();
//        }

        List<Integer> borrowedBookIds = new ArrayList<Integer>();
        for (Borrow b : borrows){
                // save only borrows that aren't returned yet

                if (b.getReturnDate() == null)
                    borrowedBookIds.add(b.getBook().getBookId());
        }

        DefaultTableModel model = new DefaultTableModel(columns.toArray(new String[0]), 0);

        // Add book data to the table model
        for (Book book : books) {
            List<Object> rowData = new ArrayList<>();
            rowData.add(book.getBookId());
            rowData.add(book.getTitle());
            rowData.add(book.getAuthor());
            rowData.add(book.getPublisher());


            if (currentUserRole.equals("student")) {
                if (!borrowedBookIds.contains(book.getBookId()))
                    rowData.add("Borrow");
            } else {
                if (borrowedBookIds.contains(book.getBookId()))
                    rowData.add("Return");
            }
            model.addRow(rowData.toArray());
        }

        JTable bookTable = new JTable(model);
        Action borrowAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = (JTable)e.getSource();
                int modelRow = Integer.parseInt( e.getActionCommand() );
                Integer book = (Integer) table.getModel().getValueAt(modelRow, 0);
                Window window = SwingUtilities.windowForComponent(bookTable);
                if (borrowedBookIds.contains(book))
                    JOptionPane.showMessageDialog(window, "Book is already borrowed");
                else {
                    String bookName = (String) table.getModel().getValueAt(modelRow, 1);
                    JOptionPane.showMessageDialog(window, "Borrowing book " + bookName);
                    try {
                        borrowDao.saveBorrow(new Borrow(0, new Student(new User(userId, null, null, null), null,null),new Book(book, null, null, null), new Date(System.currentTimeMillis()), null));
                        table.setValueAt("", modelRow, 4);
                        borrowedBookIds.add(book);

                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        };
        ButtonColumn buttonColumn;
        if (currentUserRole.equals("student")) {
            buttonColumn = new ButtonColumn(bookTable, borrowAction, 4);
            buttonColumn.setMnemonic(KeyEvent.VK_D);
        } else {
            ArrayList<Borrow> finalBorrows = borrows;
            Action returnAction = new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JTable table = (JTable)e.getSource();
                    int modelRow = Integer.parseInt( e.getActionCommand() );
                    Integer book = (Integer) table.getModel().getValueAt(modelRow, 0);
                    Window window = SwingUtilities.windowForComponent(bookTable);
                    if (borrowedBookIds.contains(book)) {
                        JOptionPane.showMessageDialog(window, " returning book");
                        try {
                            Borrow borrow = null;
                            for (Borrow b : finalBorrows) {
                                if (b.getBook().getBookId() == book && b.getReturnDate() == null) {
                                    borrow = b;
                                    break;
                                }
                            }
                            if (borrow != null) {
                                borrowDao.updateBorrow(borrow.getBorrowId(), new Date(System.currentTimeMillis()));
                                table.setValueAt("", modelRow, 4);
                                borrowedBookIds.remove(book);
                            }

                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                    } else {
                        String bookName = (String) table.getModel().getValueAt(modelRow, 1);
                        JOptionPane.showMessageDialog(window, "Book is not borrowed");

                    }
                }
            };
            buttonColumn = new ButtonColumn(bookTable, returnAction, 4);
            buttonColumn.setMnemonic(KeyEvent.VK_D);
        }




        // Add table to a JScrollPane for scrolling if needed

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        c.fill = GridBagConstraints.HORIZONTAL; // Components fill horizontally
        c.insets = new Insets(10, 10, 10, 10); // Add spacing between elements

        JScrollPane scrollPane = new JScrollPane(bookTable);

        if (currentUserRole.equals("admin")){
            JButton addBookBtn = new JButton("Add Book");
            addBookBtn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(getContentPane(), "Add book");
                }
            });
            c.gridx = 0;
            c.gridy = 0;
            panel.add(addBookBtn, c);
        }

        // Add scroll pane to the frame
        c.gridx = 1;
        c.gridy = 0;
        panel.add(scrollPane, c);
        getContentPane().add(panel);



        // Set window properties
        pack(); // Adjust frame size to fit components
        setLocationRelativeTo(null); // Center the window on the screen
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Dispose on close (prevents multiple windows)
        setVisible(true);
    }


}