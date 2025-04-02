package pl.edu.pwr.app;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class DirectorySelectionView {
    private final JFrame frame;
    private File selectedDirectory;
    private final JTextField packageNameField;

    public DirectorySelectionView(String text) {
        frame = new JFrame("Wybór katalogu");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);

        JLabel infoLabel = new JLabel(text);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("Wybierz katalog", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JButton chooseButton = new JButton("Wybierz katalog");
        JLabel selectedDirLabel = new JLabel("Nie wybrano katalogu");
        selectedDirLabel.setBorder(BorderFactory.createEtchedBorder());

        JLabel packageLabel = new JLabel("Nazwa pakietu:");
        packageNameField = new JTextField(20);
        packageNameField.setToolTipText("Wprowadź nazwę pakietu (np. com.example.myapp)");

        centerPanel.add(chooseButton, gbc);
        centerPanel.add(selectedDirLabel, gbc);

        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        centerPanel.add(packageLabel, gbc);

        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        centerPanel.add(packageNameField, gbc);
        centerPanel.add(infoLabel,gbc);

        chooseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int returnValue = fileChooser.showOpenDialog(panel);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                selectedDirectory = fileChooser.getSelectedFile();
                selectedDirLabel.setText("Wybrano: " + selectedDirectory.getAbsolutePath());
            }
        });

        JButton nextButton = new JButton("Idź dalej");
        nextButton.addActionListener(e -> {
            String packageName = packageNameField.getText().trim();
            if (selectedDirectory == null) {
                JOptionPane.showMessageDialog(panel,
                        "Proszę wybrać katalog przed kontynuacją",
                        "Błąd", JOptionPane.WARNING_MESSAGE);
            } else if (packageName.isEmpty()) {
                JOptionPane.showMessageDialog(panel,
                        "Proszę wprowadzić nazwę pakietu przed kontynuacją",
                        "Błąd", JOptionPane.WARNING_MESSAGE);
            } else {
                frame.dispose();
                new ProcessorsView(selectedDirectory, packageName);
            }
        });

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(nextButton);

        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(bottomPanel, BorderLayout.SOUTH);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DirectorySelectionView("Started."));
    }
}