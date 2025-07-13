package pl.edu.pwr.knowak;

import pl.edu.pwr.knowak.storage.ChaCha20Provider;
import pl.edu.pwr.knowak.storage.KeyStoreProvider;

import javax.swing.*;
import java.awt.*;

public class LoginWindow {

    private KeyStoreProvider keyStoreProvider = null;
    private ChaCha20Provider chachaHandler;
    private MainWindow mainWindow;
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginWindow().createLoginWindow());
    }

    private void createLoginWindow() {
        JFrame loginFrame;
        loginFrame = new JFrame("KeyStore - JCEKS");
        loginFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 200);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel aliasPanel = new JPanel(new BorderLayout());
        JLabel aliasLabel = new JLabel("Alias:");
        JTextField aliasField = new JTextField(20);
        aliasPanel.add(aliasLabel, BorderLayout.WEST);
        aliasPanel.add(aliasField, BorderLayout.CENTER);

        JPanel passwordPanel = new JPanel(new BorderLayout());
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordPanel.add(passwordLabel, BorderLayout.WEST);
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton enterButton = new JButton("Enter");
        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        buttonPanel.add(errorLabel, BorderLayout.CENTER);
        buttonPanel.add(enterButton, BorderLayout.EAST);

        mainPanel.add(aliasPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(passwordPanel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(buttonPanel);

        loginFrame.add(mainPanel);
        loginFrame.setLocationRelativeTo(null);
        loginFrame.setVisible(true);

        enterButton.addActionListener(e -> {
            String alias = aliasField.getText();
            String password = new String(passwordField.getPassword());

            errorLabel.setText(" ");
            if (!alias.isEmpty() && !password.isEmpty()) {
                try {
                    keyStoreProvider = new KeyStoreProvider("JCEKS", alias, password.toCharArray());
                    keyStoreProvider.loadKeyStore();
                    chachaHandler = new ChaCha20Provider(keyStoreProvider);
                    mainWindow = new MainWindow(keyStoreProvider,chachaHandler);
                    loginFrame.dispose();
                    mainWindow.createMainWindow(alias, password);
                } catch (Exception ex) {
                    errorLabel.setText("Error: " + ex.getMessage());
                }
            } else {
                errorLabel.setText("Please enter both alias and password");
            }
        });
    }
}
