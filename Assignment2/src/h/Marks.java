package h;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("serial")
public class Marks extends JFrame {

    private JLabel averageMarksLabel;    
    private JLabel gradeLabel;
  

    public Marks() {
        try {
            // Set the Nimbus look and feel for a modern UI
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setVisible(true);
        setBounds(0, 0, 800, 800);
        setTitle("ICS Grade Calculator");
        setBackground(Color.cyan);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create a label for the heading
        JLabel headingLabel = new JLabel("STUDENT GRADE CALCULATOR");
        headingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headingLabel.setHorizontalAlignment(JLabel.CENTER);
        add(headingLabel, BorderLayout.NORTH);

        // Create a panel for the input fields and result labels
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Create a panel for the input fields
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 1));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Marks"));

        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        JLabel[] subjectLabels = new JLabel[5];
        JTextField[] subjectTextFields = new JTextField[5];

        for (int i = 0; i < 5; i++) {
            subjectLabels[i] = new JLabel("Subject " + (i + 1) + " Marks: ");
            subjectLabels[i].setFont(labelFont);
            inputPanel.add(subjectLabels[i]);

            subjectTextFields[i] = new JTextField();
            subjectTextFields[i].setFont(labelFont);
            final int index = i;

            subjectTextFields[i].addKeyListener(new KeyListener() {
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if (!Character.isDigit(c)) {
                        e.consume();
                    }
                }

                public void keyPressed(KeyEvent e) {
                }

                public void keyReleased(KeyEvent e) {
                    String text = subjectTextFields[index].getText();
                    try {
                        int marks = Integer.parseInt(text);
                        if (marks > 100) {
                            JOptionPane.showMessageDialog(null, "Invalid marks! Marks should be between 0 and 100.");
                            subjectTextFields[index].setText("");
                        }
                    } catch (NumberFormatException ex) {
                    }
                }
            });

            inputPanel.add(subjectTextFields[i]);
        }

        centerPanel.add(inputPanel);

        // Create a panel for the result
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        averageMarksLabel = new JLabel("Total Marks : ");
        averageMarksLabel.setFont(labelFont);
        resultPanel.add(averageMarksLabel);

        JLabel gradeLabel1 = new JLabel("Average Percentage : ");
        gradeLabel1.setFont(labelFont);
        resultPanel.add(gradeLabel1);

        gradeLabel = new JLabel("Grade : ");
        gradeLabel.setFont(labelFont);
        resultPanel.add(gradeLabel);

        centerPanel.add(resultPanel);

        // Create a panel for the Calculate button
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.PLAIN, 18));

        calculateButton.addActionListener(e -> {
            boolean allFieldsFilled = true;
            for (int i = 0; i < 5; i++) {
                if (subjectTextFields[i].getText().isEmpty()) {
                    allFieldsFilled = false;
                    break;
                }
            }

            if (!allFieldsFilled) {
                JOptionPane.showMessageDialog(null, "Please enter marks for all subjects.");
            } else {

                int totalMarks = 0;
                for (int i = 0; i < 5; i++) {
                    String text = subjectTextFields[i].getText();
                    try {
                        int marks = Integer.parseInt(text);
                        totalMarks += marks;
                    } catch (NumberFormatException ex) {
                    }
                }

                // Calculate average marks
                double averageMarks = (double) totalMarks / 5;

                // Display results
                averageMarksLabel.setText("Total Marks : " + Integer.toString(totalMarks));

                double averagePercentage = (double) totalMarks / (subjectTextFields.length * 100) * 100;
                gradeLabel1.setText("Average Percentage : " + String.format("%.2f%%", averagePercentage));

                String grade = calculateGrade(averageMarks);
                gradeLabel.setText("Grade : " + grade);
            }
        });

        buttonPanel.add(calculateButton);

        // Add panels to the JFrame
        add(headingLabel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

    private String calculateGrade(double averageMarks) {
        if (averageMarks >= 90) {
            return "A+";
        } else if (averageMarks >= 80) {
            return "A";
        } else if (averageMarks >= 70) {
            return "B";
        } else if (averageMarks >= 60) {
            return "C";
        } else if (averageMarks >= 50) {
            return "D";
        } else {
            return "F";
        }
    }

    public static void main(String[] args) {
        new Marks();
    }
}
