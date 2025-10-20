package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class HomePage extends JFrame implements ActionListener {
    String username;
    JLabel welcomeLabel;
    JButton historyButton, logoutButton, refreshButton;
    JTextArea historyArea;
    JScrollPane historyScrollPane;
    JPanel examPanel;

    public HomePage(String username) {
        this.username = username;
        setTitle("Exam Management System - Home");
        setSize(800, 600);
        setLayout(null);
        getContentPane().setBackground(new Color(60, 63, 65));

        // Title
        JLabel title = new JLabel("Welcome to Exam Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 20, 800, 30);
        add(title);

        // Welcome Message
        welcomeLabel = new JLabel("", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBounds(0, 60, 800, 30);
        add(welcomeLabel);

        // Exam Panel (Dynamic Buttons for Each Exam)
        JLabel examListLabel = new JLabel("Available Exams:");
        examListLabel.setFont(new Font("Arial", Font.BOLD, 18));
        examListLabel.setForeground(Color.WHITE);
        examListLabel.setBounds(50, 120, 200, 30);
        add(examListLabel);

        examPanel = new JPanel();
        examPanel.setLayout(new GridLayout(0, 2, 20, 20));
        examPanel.setBounds(50, 160, 700, 200);
        examPanel.setBackground(new Color(60, 63, 65));
        add(examPanel);

        // History Section
        JLabel historyTitle = new JLabel("Exam History:");
        historyTitle.setFont(new Font("Arial", Font.BOLD, 18));
        historyTitle.setForeground(Color.WHITE);
        historyTitle.setBounds(50, 400, 200, 30);
        add(historyTitle);

        // Create the JTextArea for history
        historyArea = new JTextArea();
        historyArea.setBackground(new Color(43, 47, 51));
        historyArea.setForeground(Color.WHITE);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        historyArea.setEditable(false);
        historyArea.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        // Wrap the JTextArea in a JScrollPane
        historyScrollPane = new JScrollPane(historyArea);
        historyScrollPane.setBounds(50, 440, 700, 100);
        add(historyScrollPane);

        // Footer Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 20, 0)); // Updated to 3 buttons
        buttonPanel.setBounds(100, 560, 600, 50);
        buttonPanel.setBackground(new Color(60, 63, 65));
        add(buttonPanel);

        historyButton = new JButton("View History");
        historyButton.setBackground(new Color(40, 167, 69));
        historyButton.setForeground(Color.WHITE);
        historyButton.setFont(new Font("Arial", Font.BOLD, 14));
        historyButton.addActionListener(this);
        buttonPanel.add(historyButton);

        logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.addActionListener(this);
        buttonPanel.add(logoutButton);

        refreshButton = new JButton("Refresh"); // New Refresh Button
        refreshButton.setBackground(new Color(23, 162, 184));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.addActionListener(this);
        buttonPanel.add(refreshButton);

        // Fetch Details
        fetchUserDetails();
        fetchExamList();
        fetchHistory();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Fetch the full name of the user
    private void fetchUserDetails() {
        try {
            Conn conn = new Conn();
            String query = "SELECT full_name FROM Users WHERE username = ?";
            PreparedStatement stmt = conn.c.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String fullName = rs.getString("full_name");
                welcomeLabel.setText("Welcome, " + fullName + "!");
            } else {
                welcomeLabel.setText("Welcome, User!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            welcomeLabel.setText("Welcome, User!");
        }
    }

    private void fetchExamList() {
        try {
            Conn conn = new Conn();
            String query = "SELECT id, title FROM Exams";
            PreparedStatement stmt = conn.c.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            examPanel.removeAll(); // Clear existing buttons
            while (rs.next()) {
                int examId = rs.getInt("id");
                String examName = rs.getString("title");

                JButton examButton = new JButton(examName + " (ID: " + examId + ")");
                examButton.setFont(new Font("Arial", Font.PLAIN, 14));
                examButton.setBackground(new Color(0, 122, 255));
                examButton.setForeground(Color.WHITE);
                examButton.addActionListener(e -> openRulesPage(examId));
                examPanel.add(examButton);
            }
            examPanel.revalidate();
            examPanel.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading exams: " + e.getMessage());
        }
    }

    private void fetchHistory() {
        historyArea.setText(""); // Clear previous data
        try {
            Conn conn = new Conn();
            String query = "SELECT r.exam_date, r.marks, e.id AS exam_id, e.title AS exam_title " +
                           "FROM Results r " +
                           "JOIN Exams e ON r.exam_id = e.id " +
                           "WHERE r.username = ? " +
                           "ORDER BY r.exam_date DESC";
            PreparedStatement stmt = conn.c.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            historyArea.append("Exam ID\tExam Title\t\tExam Date & Time\t\tMarks\n");
            historyArea.append("----------------------------------------------------------------------\n");

            while (rs.next()) {
                int examId = rs.getInt("exam_id");
                String examTitle = rs.getString("exam_title");
                String date = rs.getString("exam_date");
                int marks = rs.getInt("marks");
                historyArea.append(examId + "\t" + examTitle + "\t\t" + date + "\t\t" + marks + "\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
            historyArea.setText("Error fetching exam history.");
        }
    }

    private void openRulesPage(int examId) {
        setVisible(false);
        new Rules(username, examId);
    }

    private void refreshPage() {
        fetchUserDetails();
        fetchExamList();
        fetchHistory();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == historyButton) {
            fetchHistory();
        } else if (e.getSource() == logoutButton) {
            setVisible(false);
            new MainLogin();
        } else if (e.getSource() == refreshButton) {
            refreshPage();
        }
    }

    public static void main(String[] args) {
        new HomePage("testuser"); // For testing purposes
    }
}
