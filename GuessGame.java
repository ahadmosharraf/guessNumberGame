import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GuessGame extends JFrame {
    private static final int MAX_ATTEMPTS = 7;

    private int targetNumber;
    private int attemptsRemaining;

    private JTextField guessTextField;
    private JButton guessButton;
    private JLabel resultLabel;

    public GuessGame() {
        setTitle("Guess Number Game");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        int targetNumber =(int)(Math.random()*9-100)+9;
        attemptsRemaining = MAX_ATTEMPTS;

        initializeComponents();
    }

    private void initializeComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        JLabel instructionLabel = new JLabel("Guess the number between 1 and 100:");
        instructionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        guessTextField = new JTextField();
        guessTextField.setFont(new Font("Impact", Font.BOLD, 28));
        guessButton = new JButton("Click to Guess");
        guessButton.setFont(new Font("Arial", Font.BOLD, 16));
        guessButton.setBackground(Color.YELLOW);
        guessButton.setPreferredSize(new Dimension(100, 30));
        guessButton.addActionListener(new GuessButtonListener());

        JButton resetButton = new JButton("Reset");
        resetButton.setPreferredSize(new Dimension(100, 30));
        resetButton.addActionListener(new ResetButtonListener());

        resultLabel = new JLabel("Attempts remaining: " + attemptsRemaining);
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resetButton.setFont(new Font("Arial", Font.BOLD, 16));
        resetButton.setBackground(Color.RED);

        guessTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    guessButton.doClick();
                    guessTextField.setText("");
                }
            }
        });

        panel.add(instructionLabel);
        panel.add(guessTextField);
        panel.add(guessButton);
        panel.add(resetButton);
        panel.add(resultLabel);

        add(panel, BorderLayout.CENTER);
    }

    private class GuessButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int userGuess = Integer.parseInt(guessTextField.getText());
                checkGuess(userGuess);
            } catch (NumberFormatException ex) {
                resultLabel.setText("Invalid input. Please enter a number.");
            }

            guessTextField.setText(""); // Clear the text field
        }
    }


    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset the game state
            targetNumber = (int) (Math.random() * 100) + 1;
            attemptsRemaining = MAX_ATTEMPTS;
            resultLabel.setText("Attempts remaining: " + attemptsRemaining);

            // Enable the text field and buttons
            guessTextField.setEnabled(true);
            guessButton.setEnabled(true);

            // Clear the text field
            guessTextField.setText("");
        }
    }
    private void checkGuess(int guess) {
        attemptsRemaining--;

        if (guess < targetNumber) {
            resultLabel.setText("Too low! Attempts remaining: " + attemptsRemaining);
        } else if (guess > targetNumber) {
            resultLabel.setText("Too high! Attempts remaining: " + attemptsRemaining);
        } else {
            showResult("Congratulations! You guessed the number.");
        }

        if (attemptsRemaining == 0) {
            resultLabel.setText("Sorry, attempts exceed! The correct number is " + targetNumber + ".");
            disableInput();
        }
    }

    private void disableInput() {
        guessTextField.setEnabled(false);
        guessButton.setEnabled(false);
    }

    private void showResult(String message) {
        JOptionPane.showMessageDialog(this, message, "Game Result", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    public static void main(String[] args) {
            GuessGame game = new GuessGame();
            game.setVisible(true);
    }
}
