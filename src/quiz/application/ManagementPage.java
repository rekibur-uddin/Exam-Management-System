package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ManagementPage extends JFrame {

    JTable table;
    JButton addUserButton, deleteUserButton, refreshButton, addExamButton, viewHistoryButton;
    DefaultTableModel model;

    public ManagementPage() {
        // Frame setup
        setTitle("Exam Management System - Admin Panel");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full window
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Layout setup
        setLayout(new BorderLayout());

        // Table setup
        model = new DefaultTableModel(
                new String[]{"User ID", "Username", "Full Name", "Mobile", "Class", "Roll No", "Exam ID", "Exam Name", "Marks", "Exam Date"},
                0
        );
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Button panel setup
        JPanel buttonPanel = new JPanel();
        addUserButton = new JButton("Add User");
        deleteUserButton = new JButton("Delete User");
        refreshButton = new JButton("Refresh");
        addExamButton = new JButton("Add Exam");
        viewHistoryButton = new JButton("View All Exams");

        buttonPanel.add(addUserButton);
        buttonPanel.add(deleteUserButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(addExamButton);
        buttonPanel.add(viewHistoryButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addUserButton.addActionListener(e -> addUser());
        deleteUserButton.addActionListener(e -> deleteUser());
        refreshButton.addActionListener(e -> loadData());
        addExamButton.addActionListener(e -> addExam());
        viewHistoryButton.addActionListener(e -> viewExam());

        // Load initial data
        loadData();
    }

    private void loadData() {
        model.setRowCount(0); // Clear existing data
        try {
            Conn conn = new Conn();
            String query = """
                SELECT u.id AS user_id, u.username, u.full_name, u.mobile_number, u.class, u.roll_no,
                       r.exam_id, e.title AS exam_name, IFNULL(r.marks, 'N/A') AS marks, IFNULL(r.exam_date, 'N/A') AS exam_date
                FROM Users u
                LEFT JOIN Results r ON u.username = r.username
                LEFT JOIN Exams e ON r.exam_id = e.id
            """;
            ResultSet rs = conn.s.executeQuery(query);

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("user_id"),
                        rs.getString("username"),
                        rs.getString("full_name"),
                        rs.getString("mobile_number"),
                        rs.getString("class"),
                        rs.getString("roll_no"),
                        rs.getInt("exam_id"),
                        rs.getString("exam_name"),
                        rs.getString("marks"),
                        rs.getString("exam_date")
                });
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private void addUser() {
        String username = JOptionPane.showInputDialog(this, "Enter Username:");
        String password = JOptionPane.showInputDialog(this, "Enter Password:");
        String fullName = JOptionPane.showInputDialog(this, "Enter Full Name:");
        String mobile = JOptionPane.showInputDialog(this, "Enter Mobile Number:");
        String className = JOptionPane.showInputDialog(this, "Enter Class:");
        String rollNo = JOptionPane.showInputDialog(this, "Enter Roll Number:");

        if (username == null || password == null || fullName == null || mobile == null || className == null || rollNo == null) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO Users (username, password, full_name, mobile_number, class, roll_no) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, fullName);
            pstmt.setString(4, mobile);
            pstmt.setString(5, className);
            pstmt.setString(6, rollNo);

            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "User added successfully.");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage());
        }
    }

    private void deleteUser() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.");
            return;
        }

        int userId = (int) model.getValueAt(selectedRow, 0);
        try {
            Conn conn = new Conn();
            String query = "DELETE FROM Users WHERE id = ?";
            PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "User deleted successfully.");
            loadData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage());
        }
    }

  private void addExam() {
    String examTitle = JOptionPane.showInputDialog(this, "Enter Exam Title:");
    if (examTitle == null || examTitle.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Exam title cannot be empty.");
        return;
    }

    try {
        Conn conn = new Conn();
        String query = "INSERT INTO Exams (title) VALUES (?)";
        PreparedStatement pstmt = conn.c.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        pstmt.setString(1, examTitle);
        pstmt.executeUpdate();

        ResultSet rs = pstmt.getGeneratedKeys();
        if (rs.next()) {
            int examId = rs.getInt(1);
            JOptionPane.showMessageDialog(this, "Exam created successfully. Exam ID: " + examId);

            // Open AddExamPage and pass the examId
            new AddExamPage(examId);
        }
        conn.c.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error creating exam: " + e.getMessage());
    }
}





    private void viewExam() {
    JFrame viewExamFrame = new JFrame("View All Exams");
    viewExamFrame.setSize(600, 400);
    viewExamFrame.setLocationRelativeTo(this);

    // Create a table model for displaying exams
    DefaultTableModel examModel = new DefaultTableModel(new String[]{"Exam ID", "Title"}, 0);
    JTable examTable = new JTable(examModel);
    JScrollPane scrollPane = new JScrollPane(examTable);
    viewExamFrame.add(scrollPane, BorderLayout.CENTER);

    // Load exam data into the table
    try {
        Conn conn = new Conn();
        String query = "SELECT id, title FROM Exams";
        ResultSet rs = conn.s.executeQuery(query);

        while (rs.next()) {
            examModel.addRow(new Object[]{rs.getInt("id"), rs.getString("title")});
        }
        conn.c.close();
    } catch (Exception e) {
        JOptionPane.showMessageDialog(viewExamFrame, "Error loading exams: " + e.getMessage());
    }

    // Add delete button
    JButton deleteButton = new JButton("Delete Exam");
    deleteButton.addActionListener(e -> {
        int selectedRow = examTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(viewExamFrame, "Please select an exam to delete.");
            return;
        }

        int examId = (int) examModel.getValueAt(selectedRow, 0);
        try {
            Conn conn = new Conn();
            String deleteQuery = "DELETE FROM Exams WHERE id = ?";
            PreparedStatement pstmt = conn.c.prepareStatement(deleteQuery);
            pstmt.setInt(1, examId);
            pstmt.executeUpdate();
            conn.c.close();

            JOptionPane.showMessageDialog(viewExamFrame, "Exam deleted successfully.");
            examModel.removeRow(selectedRow);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(viewExamFrame, "Error deleting exam: " + ex.getMessage());
        }
    });

    JPanel buttonPanel = new JPanel();
    buttonPanel.add(deleteButton);
    viewExamFrame.add(buttonPanel, BorderLayout.SOUTH);

    viewExamFrame.setVisible(true);
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ManagementPage().setVisible(true);
        });
    }
}
