package pl.edu.pwr.knowak.app;

import javax.swing.*;
import java.awt.*;

public class InitialFrame extends JFrame {
    private final JTextField stationsField;

    public InitialFrame() {
        setTitle("Initial Configuration");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Number of service stations/categories:");
        stationsField = new JTextField();
        JButton submitButton = new JButton("Submit");

        inputPanel.add(label);
        inputPanel.add(stationsField);
        inputPanel.add(new JLabel());
        inputPanel.add(submitButton);

        add(inputPanel, BorderLayout.CENTER);

        submitButton.addActionListener(e -> validateAndProceed());
    }

    private void validateAndProceed() {
        String input = stationsField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter the number of service stations",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int numberOfStations = Integer.parseInt(input);

            if (numberOfStations <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Number of stations must be positive",
                        "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            MainFrame mainFrame = new MainFrame(numberOfStations);
            mainFrame.setVisible(true);

            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid number",
                    "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialFrame frame = new InitialFrame();
            frame.setVisible(true);
        });
    }
}