package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class AddExamPage extends JFrame {
    private JTextField examNameField, questionField, option1Field, option2Field, option3Field, option4Field;
    private JComboBox<String> correctOptionCombo;
    private JButton addQuestionButton, saveExamButton;
    private int examId;

    public AddExamPage(int examId) {
        this.examId = examId;

        setTitle("Add Exam Questions");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create a panel for the form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(7, 2, 10, 10)); // Grid layout with padding between elements
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add space around the form

        // Exam ID (Already provided from parent window)
        JLabel examNameLabel = new JLabel("Exam ID: " + examId);
        examNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        formPanel.add(examNameLabel);
        formPanel.add(new JLabel(""));  // Empty label to fill space

        // Question Input Fields
        JLabel questionLabel = new JLabel("Enter Question:");
        questionField = new JTextField();
        formPanel.add(questionLabel);
        formPanel.add(questionField);

        // Options
        JLabel option1Label = new JLabel("Option 1:");
        option1Field = new JTextField();
        formPanel.add(option1Label);
        formPanel.add(option1Field);

        JLabel option2Label = new JLabel("Option 2:");
        option2Field = new JTextField();
        formPanel.add(option2Label);
        formPanel.add(option2Field);

        JLabel option3Label = new JLabel("Option 3:");
        option3Field = new JTextField();
        formPanel.add(option3Label);
        formPanel.add(option3Field);

        JLabel option4Label = new JLabel("Option 4:");
        option4Field = new JTextField();
        formPanel.add(option4Label);
        formPanel.add(option4Field);

        // Correct Option Combo Box
        JLabel correctOptionLabel = new JLabel("Correct Option (1-4):");
        correctOptionCombo = new JComboBox<>(new String[]{"1", "2", "3", "4"});
        formPanel.add(correctOptionLabel);
        formPanel.add(correctOptionCombo);

        // Add the form panel to the center of the frame
        add(formPanel, BorderLayout.CENTER);

        // Create a panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 10)); // Center aligned with spacing

        // Add Question Button
        addQuestionButton = new JButton("Add Question");
        addQuestionButton.setFont(new Font("Arial", Font.BOLD, 14));
        addQuestionButton.setForeground(Color.WHITE);
        addQuestionButton.setBackground(new Color(34, 167, 240)); // Blue color
        addQuestionButton.setFocusPainted(false);
        addQuestionButton.setPreferredSize(new Dimension(150, 40));

        // Save Exam Button
        saveExamButton = new JButton("Save Exam");
        saveExamButton.setFont(new Font("Arial", Font.BOLD, 14));
        saveExamButton.setForeground(Color.WHITE);
        saveExamButton.setBackground(new Color(46, 204, 113)); // Green color
        saveExamButton.setFocusPainted(false);
        saveExamButton.setPreferredSize(new Dimension(150, 40));

        buttonPanel.add(addQuestionButton);
        buttonPanel.add(saveExamButton);

        // Add the button panel to the bottom of the frame
        add(buttonPanel, BorderLayout.SOUTH);

        // Button Actions
        addQuestionButton.addActionListener(e -> addQuestion());
        saveExamButton.addActionListener(e -> saveExam());

        setVisible(true);
    }

    private void addQuestion() {
        String questionText = questionField.getText();
        String option1 = option1Field.getText();
        String option2 = option2Field.getText();
        String option3 = option3Field.getText();
        String option4 = option4Field.getText();
        String correctOption = (String) correctOptionCombo.getSelectedItem();

        if (questionText.isEmpty() || option1.isEmpty() || option2.isEmpty() || option3.isEmpty() || option4.isEmpty() || correctOption == null) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        try {
            Conn conn = new Conn();
            String query = "INSERT INTO Questions (exam_id, question_text, option1, option2, option3, option4, correct_option) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.c.prepareStatement(query);
            pstmt.setInt(1, examId);
            pstmt.setString(2, questionText);
            pstmt.setString(3, option1);
            pstmt.setString(4, option2);
            pstmt.setString(5, option3);
            pstmt.setString(6, option4);
            pstmt.setString(7, correctOption);
            pstmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Question added successfully.");

            // Clear fields for next question
            questionField.setText("");
            option1Field.setText("");
            option2Field.setText("");
            option3Field.setText("");
            option4Field.setText("");
            correctOptionCombo.setSelectedIndex(0);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error adding question: " + e.getMessage());
        }
    }

    private void saveExam() {
        JOptionPane.showMessageDialog(this, "Exam saved successfully!");
        this.dispose(); // Close the current window after saving
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddExamPage(1)); // Example exam ID
    }
}
