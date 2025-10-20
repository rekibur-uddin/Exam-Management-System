package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup extends JFrame implements ActionListener {

    JTextField usernameField, fullNameField, mobileField, classField, rollNoField;
    JPasswordField passwordField;
    JButton signupButton, backButton, clearButton;

    public Signup() {
        setTitle("Sign Up - Exam Management System");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full window
        setLayout(null);
        getContentPane().setBackground(new Color(60, 63, 65));

        // Title
        JLabel title = new JLabel("Create New Account", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.WHITE);
        title.setBounds(410, 50, 420, 50);
        add(title);

        // Panel for Centered Components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(43, 47, 51));
        panel.setBounds(380, 100, 500, 450);
        panel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 2, true)); // Light blue border with rounded corners
        add(panel);

        // Full Name
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(50, 20, 100, 30);
        fullNameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        fullNameLabel.setForeground(Color.WHITE);
        panel.add(fullNameLabel);

        fullNameField = new JTextField();
        fullNameField.setBounds(200, 20, 200, 30);
        fullNameField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(fullNameField);

        // Mobile Number
        JLabel mobileLabel = new JLabel("Mobile No:");
        mobileLabel.setBounds(50, 70, 100, 30);
        mobileLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        mobileLabel.setForeground(Color.WHITE);
        panel.add(mobileLabel);

        mobileField = new JTextField();
        mobileField.setBounds(200, 70, 200, 30);
        mobileField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(mobileField);

        // Class
        JLabel classLabel = new JLabel("Class:");
        classLabel.setBounds(50, 120, 100, 30);
        classLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        classLabel.setForeground(Color.WHITE);
        panel.add(classLabel);

        classField = new JTextField();
        classField.setBounds(200, 120, 200, 30);
        classField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(classField);

        // Roll Number
        JLabel rollNoLabel = new JLabel("Roll No:");
        rollNoLabel.setBounds(50, 170, 100, 30);
        rollNoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        rollNoLabel.setForeground(Color.WHITE);
        panel.add(rollNoLabel);

        rollNoField = new JTextField();
        rollNoField.setBounds(200, 170, 200, 30);
        rollNoField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(rollNoField);

        // Username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 220, 100, 30);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(Color.WHITE);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 220, 200, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(usernameField);

        // Password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 270, 100, 30);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 270, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passwordField);

        // Buttons
        signupButton = new JButton("SIGN UP");
        signupButton.setBounds(50, 350, 100, 30);
        signupButton.setBackground(new Color(40, 167, 69));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupButton.addActionListener(this);
        panel.add(signupButton);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(200, 350, 100, 30);
        clearButton.setBackground(new Color(220, 53, 69));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(this);
        panel.add(clearButton);

        backButton = new JButton("BACK");
        backButton.setBounds(350, 350, 100, 30);
        backButton.setBackground(new Color(0, 122, 255));
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.addActionListener(this);
        panel.add(backButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == signupButton) {
            String fullName = fullNameField.getText().trim();
            String mobile = mobileField.getText().trim();
            String userClass = classField.getText().trim();
            String rollNo = rollNoField.getText().trim();
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            try {
                if (username.isEmpty() || password.isEmpty() || fullName.isEmpty() || mobile.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "All fields are required!");
                    return;
                }

                Conn conn = new Conn();
                String query = "INSERT INTO Users (username, password, full_name, mobile_number, class, roll_no) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.c.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setString(3, fullName);
                stmt.setString(4, mobile);
                stmt.setString(5, userClass);
                stmt.setString(6, rollNo);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(null, "Account Created Successfully!");
                setVisible(false);
                new HomePage(username); // Redirect to Home Page and pass the username
            } catch (SQLIntegrityConstraintViolationException ex) {
                JOptionPane.showMessageDialog(null, "Username already exists! Try a different one.");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error occurred. Please try again.");
            }
        } else if (e.getSource() == clearButton) {
            fullNameField.setText("");
            mobileField.setText("");
            classField.setText("");
            rollNoField.setText("");
            usernameField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == backButton) {
            new MainLogin();
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new Signup();
    }
}
