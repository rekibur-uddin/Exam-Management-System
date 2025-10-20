package quiz.application;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Quiz extends JFrame implements ActionListener {

    JLabel qno, question, timerLabel;
    JRadioButton opt1, opt2, opt3, opt4;
    ButtonGroup groupoptions;
    JButton next, previous, submit;

    String[][] questions;
    String[][] answers;
    String[][] useranswers;

    int timer = 15; // Timer duration
    int count = 0;
    int score = 0;
    String studentName;
    int examId;

    Timer t;

    public Quiz(String studentName, int examId) {
        this.studentName = studentName;
        this.examId = examId;

        setBounds(100, 100, 1200, 600);
        getContentPane().setBackground(Color.WHITE);
        setLayout(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Title
        JLabel title = new JLabel("Exam Management System");
        title.setBounds(400, 20, 400, 30);
        title.setFont(new Font("Tahoma", Font.BOLD, 24));
        title.setForeground(new Color(30, 144, 255));
        add(title);

        // Timer
        timerLabel = new JLabel("Time Left: " + timer + " seconds");
        timerLabel.setBounds(950, 50, 200, 30);
        timerLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
        timerLabel.setForeground(Color.RED);
        add(timerLabel);

        // Question Number
        qno = new JLabel();
        qno.setBounds(100, 100, 50, 30);
        qno.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(qno);

        // Question
        question = new JLabel();
        question.setBounds(150, 100, 900, 30);
        question.setFont(new Font("Tahoma", Font.PLAIN, 20));
        add(question);

        // Options
        opt1 = createOption(150, 180);
        opt2 = createOption(150, 230);
        opt3 = createOption(150, 280);
        opt4 = createOption(150, 330);

        groupoptions = new ButtonGroup();
        groupoptions.add(opt1);
        groupoptions.add(opt2);
        groupoptions.add(opt3);
        groupoptions.add(opt4);

        // Buttons
        previous = createButton("Previous", 650, 450, Color.ORANGE);
        previous.setEnabled(false);
        next = createButton("Next", 800, 450, Color.BLUE);
        submit = createButton("Submit", 950, 450, Color.GREEN);
        submit.setEnabled(false);

        // Load questions from the database
        loadQuestionsFromDatabase();

        if (questions.length > 0) {
            useranswers = new String[questions.length][1];
            start(count);
        } else {
            JOptionPane.showMessageDialog(this, "No questions found for this exam.");
            setVisible(false);
        }

        // Timer
        t = new Timer(1000, e -> {
            timer--;
            timerLabel.setText("Time Left: " + timer + " seconds");
            if (timer <= 0) {
                saveAnswer();
                timer = 15;
                count++;
                updateButtons();
                if (count == questions.length) {
                    t.stop();
                    submitQuiz();
                } else {
                    start(count);
                }
            }
        });
        t.start();

        setVisible(true);
    }

    private JRadioButton createOption(int x, int y) {
        JRadioButton option = new JRadioButton();
        option.setBounds(x, y, 700, 30);
        option.setBackground(Color.WHITE);
        option.setFont(new Font("Dialog", Font.PLAIN, 18));
        add(option);
        return option;
    }

    private JButton createButton(String text, int x, int y, Color color) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 120, 40);
        button.setFont(new Font("Tahoma", Font.PLAIN, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.addActionListener(this);
        add(button);
        return button;
    }

    private void loadQuestionsFromDatabase() {
    try {
        Conn conn = new Conn();
        String query = "SELECT * FROM Questions WHERE exam_id = ?";
        PreparedStatement pstmt = conn.c.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        pstmt.setInt(1, examId);
        ResultSet rs = pstmt.executeQuery();

        // Move cursor to the last row to get row count
        rs.last();
        int rowCount = rs.getRow();  // Get the total number of rows
        rs.beforeFirst();  // Move cursor back to the first row

        questions = new String[rowCount][5];
        answers = new String[rowCount][2];

        int i = 0;
        while (rs.next()) {
            questions[i][0] = rs.getString("question_text");
            questions[i][1] = rs.getString("option1");
            questions[i][2] = rs.getString("option2");
            questions[i][3] = rs.getString("option3");
            questions[i][4] = rs.getString("option4");
            answers[i][1] = rs.getString("correct_option");
            i++;
        }
        conn.c.close();
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error loading questions: " + e.getMessage());
    }
}

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == next) {
            saveAnswer();
            count++;
            timer = 15;
            updateButtons();
            start(count);
        } else if (ae.getSource() == previous) {
            saveAnswer();
            count--;
            timer = 15;
            updateButtons();
            start(count);
        } else if (ae.getSource() == submit) {
            saveAnswer();
            t.stop();
            submitQuiz();
        }
    }

   private void saveAnswer() {
    if (groupoptions.getSelection() == null) {
        useranswers[count][0] = ""; // Mark as unanswered
    } else {
        useranswers[count][0] = groupoptions.getSelection().getActionCommand(); // Store option index
    }
}


    private void calculateScore() {
    for (int i = 0; i < questions.length; i++) {
        if (useranswers[i][0] != null && useranswers[i][0].equals(answers[i][1])) {
            score += 5;
        }
    }
}


    private void start(int count) {
    qno.setText("Q" + (count + 1) + ":");
    question.setText(questions[count][0]);

    opt1.setText(questions[count][1]);
    opt1.setActionCommand("1"); // Set index as ActionCommand
    opt2.setText(questions[count][2]);
    opt2.setActionCommand("2");
    opt3.setText(questions[count][3]);
    opt3.setActionCommand("3");
    opt4.setText(questions[count][4]);
    opt4.setActionCommand("4");

    groupoptions.clearSelection();
}


    private void updateButtons() {
        previous.setEnabled(count > 0);
        next.setEnabled(count < questions.length - 1);
        submit.setEnabled(count == questions.length - 1);
    }

    private void submitQuiz() {
        calculateScore();
        storeResultsToDatabase();
        JOptionPane.showMessageDialog(this, "Exam is Over! Your Marks: " + score);
        setVisible(false);
        new HomePage(studentName);
    }

   private void storeResultsToDatabase() {
    try {
        Conn conn = new Conn();
        String query = "INSERT INTO Results (username, exam_id, marks, exam_date) VALUES (?, ?, ?, NOW())";
        PreparedStatement stmt = conn.c.prepareStatement(query);
        stmt.setString(1, studentName); // Pass student's username
        stmt.setInt(2, examId);        // Pass exam ID
        stmt.setInt(3, score);         // Pass calculated score
        stmt.executeUpdate();
        conn.c.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


   public static void main(String[] args) {
    try {
        int examId = Integer.parseInt(JOptionPane.showInputDialog("Enter Exam ID:"));
        new Quiz("Student Name", examId); // Pass exam ID
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Invalid Exam ID entered!");
    }
}
}
