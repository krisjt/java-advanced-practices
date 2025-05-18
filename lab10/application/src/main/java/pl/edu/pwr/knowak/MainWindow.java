package pl.edu.pwr.knowak;

import pl.edu.pwr.knowak.encryption.Decryptor;
import pl.edu.pwr.knowak.encryption.Encryptor;
import pl.edu.pwr.knowak.storage.ChaCha20Provider;
import pl.edu.pwr.knowak.storage.KeyStoreProvider;

import javax.crypto.SecretKey;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.Certificate;

public class MainWindow {
    private JFrame mainFrame;
    private JTextArea textArea;
    private JTextArea fileContentArea;
    private JScrollPane fileContentScrollPane;
    private JRadioButton radioButton1, radioButton2;
    private PrivateKey privateKey;
    private SecretKey secretKey;
    private final KeyStoreProvider keyStoreProvider;
    private final ChaCha20Provider chachaHandler;

    public MainWindow(KeyStoreProvider keyStoreProvider, ChaCha20Provider chachaHandler) {
        this.keyStoreProvider = keyStoreProvider;
        this.chachaHandler = chachaHandler;
    }

    public void createMainWindow(String alias, String password) throws IOException {
        mainFrame = new JFrame("Main Application - " + alias);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(900, 600);
        mainFrame.setLayout(new BorderLayout(10, 10));

        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("KeyStore Contents"));
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setText(keyStoreProvider.listAllItems());
        leftPanel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel keyTypePanel = new JPanel(new GridLayout(2, 1));
        keyTypePanel.setBorder(BorderFactory.createTitledBorder("Key Type"));
        radioButton1 = new JRadioButton("AsymmetricKey - RSA");
        radioButton2 = new JRadioButton("SymmetricKey - ChaCha20");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioButton1);
        radioGroup.add(radioButton2);
        keyTypePanel.add(radioButton1);
        keyTypePanel.add(radioButton2);
        rightPanel.add(keyTypePanel);

        JPanel credPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        credPanel.setBorder(BorderFactory.createTitledBorder("Credentials"));
        credPanel.add(new JLabel("Alias:"));
        JTextField aliasField = new JTextField(alias);
        credPanel.add(aliasField);
        credPanel.add(new JLabel("Password:"));
        JPasswordField passwordField = new JPasswordField(password);
        credPanel.add(passwordField);
        rightPanel.add(credPanel);

        JPanel filePanel = new JPanel(new BorderLayout(5, 5));
        filePanel.setBorder(BorderFactory.createTitledBorder("File Operations"));

        JPanel inputFilePanel = new JPanel(new BorderLayout(5, 5));
        inputFilePanel.add(new JLabel("Input File:"), BorderLayout.WEST);
        JTextField filePathField = new JTextField();
        JButton browseButton = new JButton("Browse");
        JPanel browsePanel = new JPanel(new BorderLayout());
        browsePanel.add(filePathField, BorderLayout.CENTER);
        browsePanel.add(browseButton, BorderLayout.EAST);
        inputFilePanel.add(browsePanel, BorderLayout.CENTER);
        filePanel.add(inputFilePanel, BorderLayout.NORTH);

        JPanel outputFilePanel = new JPanel(new BorderLayout(5, 5));
        outputFilePanel.add(new JLabel("Output File (encryption only):"), BorderLayout.WEST);
        JTextField outputFilePathField = new JTextField();
        outputFilePanel.add(outputFilePathField, BorderLayout.CENTER);
        filePanel.add(outputFilePanel, BorderLayout.CENTER);

        rightPanel.add(filePanel);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createTitledBorder("File Content"));
        fileContentArea = new JTextArea();
        fileContentArea.setEditable(false);
        fileContentScrollPane = new JScrollPane(fileContentArea);
        contentPanel.add(fileContentScrollPane, BorderLayout.CENTER);
        rightPanel.add(contentPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        JButton encryptButton = new JButton("Encrypt");
        JButton decryptButton = new JButton("Decrypt");
        buttonPanel.add(encryptButton);
        buttonPanel.add(decryptButton);
        rightPanel.add(buttonPanel);

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        rightPanel.add(errorLabel);

        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                filePathField.setText(selectedFile.getAbsolutePath());
                try {
                    loadFileContent(selectedFile);
                } catch (IOException ex) {
                    errorLabel.setText("Error loading file: " + ex.getMessage());
                }
            }
        });

        encryptButton.addActionListener(e -> {
            errorLabel.setText(" ");
            if (filePathField.getText().isEmpty()) {
                errorLabel.setText("Please select an input file");
                return;
            }

            if (outputFilePathField.getText().isEmpty()) {
                errorLabel.setText("Please specify an output file for encryption");
                return;
            }

            String keyAlias = aliasField.getText();
            String keyPassword = new String(passwordField.getPassword());

            if (!radioButton1.isSelected() && !radioButton2.isSelected()) {
                errorLabel.setText("Please select key type first");
                return;
            }

            try {
                if (radioButton1.isSelected()) {
                    Certificate certificate = keyStoreProvider.loadCertificate(keyAlias);
                    PublicKey publicKey = certificate.getPublicKey();
                    Encryptor.encryptFile(publicKey,
                            Path.of(filePathField.getText()),
                            Path.of(outputFilePathField.getText()));
                } else {
                    secretKey = chachaHandler.loadKey(keyPassword.toCharArray(), keyAlias);
                    Encryptor.encryptSHAFile(secretKey,
                            Path.of(filePathField.getText()),
                            Path.of(outputFilePathField.getText()));
                }
                loadFileContent(new File(outputFilePathField.getText()));
                errorLabel.setText("Encryption successful!");
            } catch (Exception ex) {
                errorLabel.setText("Encryption error: " + ex.getMessage());
            }
        });

        decryptButton.addActionListener(e -> {
            errorLabel.setText(" ");
            if (filePathField.getText().isEmpty()) {
                errorLabel.setText("Please select an input file");
                return;
            }

            String keyAlias = aliasField.getText();
            String keyPassword = new String(passwordField.getPassword());

            if (!radioButton1.isSelected() && !radioButton2.isSelected()) {
                errorLabel.setText("Please select key type first");
                return;
            }

            try {
                if (radioButton1.isSelected()) {
                    privateKey = (PrivateKey) keyStoreProvider.loadKey(keyPassword.toCharArray(), keyAlias);
                    Decryptor.decryptionFile(privateKey, Path.of(filePathField.getText()));
                } else {
                    secretKey = chachaHandler.loadKey(keyPassword.toCharArray(), keyAlias);
                    Decryptor.decryptChaChaFile(secretKey, Path.of(filePathField.getText()));
                }
                loadFileContent(new File(filePathField.getText()));
                errorLabel.setText("Decryption successful!");
            } catch (Exception ex) {
                errorLabel.setText("Decryption error: " + ex.getMessage());
            }
        });

        mainFrame.add(leftPanel, BorderLayout.WEST);
        mainFrame.add(rightPanel, BorderLayout.CENTER);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void loadFileContent(File file) throws IOException {
        Path path = FileSystems.getDefault().getPath(file.getPath());
        String mimeType = Files.probeContentType(path);

        if (mimeType != null && (mimeType.equals("text/plain") || mimeType.equals("text/css") || mimeType.equals("text/html") || mimeType.equals("text/javascript"))) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                fileContentArea.setText(content);
            } catch (IOException e) {
                fileContentArea.setText("Error reading file: " + e.getMessage());
            }
        } else {
            fileContentArea.setText("File content cannot be displayed - format not text");
        }
    }
}