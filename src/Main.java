import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {

    private JFrame frame;
    private JTextField textField;
    private JPanel panel;
    private String firstNumber = "";
    private String operator = "";
    private boolean startNewNumber = true;

    public Main() {
        frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        textField = new JTextField();
        textField.setEditable(false);
        textField.setHorizontalAlignment(JTextField.RIGHT);
        frame.add(textField, BorderLayout.NORTH);

        panel = new JPanel();
        panel.setLayout(new GridLayout(4, 4));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            panel.add(button);
            button.addActionListener(new ButtonClickListener());
        }

        frame.add(panel);
        frame.setVisible(true);
    }

    private class ButtonClickListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            if (command.charAt(0) >= '0' && command.charAt(0) <= '9' || command.equals(".")) {
                if (startNewNumber) {
                    textField.setText(command);
                    startNewNumber = false;
                } else {
                    textField.setText(textField.getText() + command);
                }
            } else if (command.equals("=")) {
                if (!operator.isEmpty()) {
                    double result = calculate(Double.parseDouble(firstNumber), Double.parseDouble(textField.getText()), operator);
                    textField.setText(String.valueOf(result));
                    operator = "";
                    startNewNumber = true;
                }
            } else {
                if (!operator.isEmpty() && !startNewNumber) {
                    double result = calculate(Double.parseDouble(firstNumber), Double.parseDouble(textField.getText()), operator);
                    textField.setText(String.valueOf(result));
                    firstNumber = String.valueOf(result);
                } else {
                    firstNumber = textField.getText();
                }
                operator = command;
                startNewNumber = true;
            }
        }

        private double calculate(double num1, double num2, String op) {
            switch (op) {
                case "+":
                    return num1 + num2;
                case "-":
                    return num1 - num2;
                case "*":
                    return num1 * num2;
                case "/":
                    if (num2 != 0) {
                        return num1 / num2;
                    } else {
                        JOptionPane.showMessageDialog(frame, "Cannot divide by zero", "Error", JOptionPane.ERROR_MESSAGE);
                        return 0;
                    }
                default:
                    return 0;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }
}
