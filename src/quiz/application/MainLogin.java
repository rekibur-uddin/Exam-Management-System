package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MainLogin extends JFrame implements ActionListener {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton, clearButton, signupButton;

    public MainLogin() {
        setTitle("Exam Management System - Login");
        setSize(800, 600);
        setLayout(null);

        // Project Title
        JLabel title = new JLabel("EXAM MANAGEMENT SYSTEM", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 28));
        title.setForeground(Color.BLACK);
        title.setBounds(410, 50, 420, 50); // Centered across the entire frame width
        add(title);

        // Panel for Centered Components
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(new Color(43, 47, 51));
        panel.setBounds(380, 150, 500, 300);
        panel.setBorder(BorderFactory.createLineBorder(new Color(173, 216, 230), 5, true)); // Light blue border with rounded corners
        add(panel);

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setBounds(50, 50, 100, 30);
        panel.add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(200, 50, 200, 30);
        usernameField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(usernameField);

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(50, 100, 100, 30);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 100, 200, 30);
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(passwordField);

        // Buttons
        loginButton = new JButton("LOGIN");
        loginButton.setBounds(50, 200, 100, 30);
        loginButton.setBackground(new Color(0, 122, 255));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.addActionListener(this);
        panel.add(loginButton);

        clearButton = new JButton("CLEAR");
        clearButton.setBounds(200, 200, 100, 30);
        clearButton.setBackground(new Color(220, 53, 69));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.addActionListener(this);
        panel.add(clearButton);

        signupButton = new JButton("SIGN UP");
        signupButton.setBounds(350, 200, 100, 30);
        signupButton.setBackground(new Color(40, 167, 69));
        signupButton.setForeground(Color.WHITE);
        signupButton.setFont(new Font("Arial", Font.BOLD, 14));
        signupButton.addActionListener(this);
        panel.add(signupButton);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = usernameField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            try {
                Conn conn = new Conn();
                String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
                PreparedStatement stmt = conn.c.prepareStatement(query);
                stmt.setString(1, username);
                stmt.setString(2, password);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    JOptionPane.showMessageDialog(null, "Login Successful!");
                    new HomePage(username); // Redirect to the HomePage and pass the username
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid Username or Password!");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else if (e.getSource() == clearButton) {
            usernameField.setText("");
            passwordField.setText("");
        } else if (e.getSource() == signupButton) {
            new Signup(); // Redirect to the Sign Up Page
            setVisible(false);
        }
    }

    public static void main(String[] args) {
        new MainLogin();
    }
}
