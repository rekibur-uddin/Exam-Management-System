package quiz.application;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Rules extends JFrame implements ActionListener {

    String name;
    int examId;
    JButton start, back;

    Rules(String name, int examId) {
        this.name = name;
        this.examId = examId;
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);

        // Heading
        JLabel heading = new JLabel("Welcome " + name + " to Exam ID: " + examId);
        heading.setBounds(50, 20, 700, 30);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD, 28));
        heading.setForeground(new Color(30, 144, 254));
        add(heading);

        // Rules
        JLabel rules = new JLabel();
        rules.setBounds(20, 90, 700, 350);
        rules.setFont(new Font("Tahoma", Font.PLAIN, 16));
        rules.setText(
                "<html>" +
                        "1. This is an exam based on the syllabus. Answer all questions carefully." + "<br><br>" +
                        "2. The exam consists of multiple-choice questions (MCQs). Only one option is correct." + "<br><br>" +
                        "3. You will have a fixed time to complete the exam. Manage your time carefully." + "<br><br>" +
                        "4. No external resources or IDEs are allowed during the exam." + "<br><br>" +
                        "5. Once you click 'Start,' the timer will begin, and the quiz cannot be paused." + "<br><br>" +
                        "6. Your performance will be recorded, and you will get your results immediately." + "<br><br>" +
                "</html>"
        );
        add(rules);

        // Back Button
        back = new JButton("Back");
        back.setBounds(250, 500, 100, 30);
        back.setBackground(new Color(30, 144, 254));
        back.setForeground(Color.WHITE);
        back.addActionListener(this);
        add(back);

        // Start Button
        start = new JButton("Start");
        start.setBounds(400, 500, 100, 30);
        start.setBackground(new Color(30, 144, 254));
        start.setForeground(Color.WHITE);
        start.addActionListener(this);
        add(start);

        setSize(800, 650);
        setLocation(350, 100);
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Open in full window
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == start) {
            // Start Quiz
            setVisible(false);
            new Quiz(name, examId); // Navigate to Quiz Page with examId
        } else if (ae.getSource() == back) {
            // Go back to HomePage
            setVisible(false);
            new HomePage(name);
        }
    }

    public static void main(String[] args) {
        new Rules("User", 101); // Example usage
    }
}
