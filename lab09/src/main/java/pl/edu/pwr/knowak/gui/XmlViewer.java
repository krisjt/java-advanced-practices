package pl.edu.pwr.knowak.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class XsltViewer extends JFrame {

    private JEditorPane resultPane;
    private JTextArea resultJAX;
    private JTextField xmlPathField;
    private JTextField xsltPathField;

    public XsltViewer() {
        setTitle("XSLT Viewer");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 5));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        topPanel.add(new JLabel("XML File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        xmlPathField = new JTextField();
        topPanel.add(xmlPathField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JButton xmlBrowseButton = new JButton("Browse...");
        xmlBrowseButton.addActionListener(new BrowseAction(xmlPathField, "Select XML File", "xml"));
        topPanel.add(xmlBrowseButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        topPanel.add(new JLabel("XSLT File:"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        xsltPathField = new JTextField();
        topPanel.add(xsltPathField, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0.0;
        JButton xsltBrowseButton = new JButton("Browse...");
        xsltBrowseButton.addActionListener(new BrowseAction(xsltPathField, "Select XSLT File", "xslt"));
        topPanel.add(xsltBrowseButton, gbc);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setDividerLocation(0.5); // Dzieli obszar na pół
        splitPane.setResizeWeight(0.5);    // Zachowuje proporcje przy zmianie rozmiaru okna

// Lewy panel (resultPane)
        resultPane = new JEditorPane();
        resultPane.setEditable(false);
        resultPane.setContentType("text/html");
        JScrollPane leftScrollPane = new JScrollPane(resultPane);
        splitPane.setLeftComponent(leftScrollPane);

// Prawy panel (resultJAX)
        resultJAX = new JTextArea();
        resultJAX.setEditable(false);
        JScrollPane rightScrollPane = new JScrollPane(resultJAX);
        splitPane.setRightComponent(rightScrollPane);

// Dodaj splitPane do głównego panelu
        mainPanel.add(splitPane, BorderLayout.CENTER);

        JPanel parserButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton domButton = new JButton("DOM");
        domButton.addActionListener(new DomAction(xmlPathField, this, resultJAX));
        parserButtonsPanel.add(domButton);

        JButton saxButton = new JButton("SAX");
        saxButton.addActionListener(new SaxAction(xmlPathField, this, resultJAX));
        parserButtonsPanel.add(saxButton);

        JButton jaxbButton = new JButton("JAXB");
        jaxbButton.addActionListener(new JaxbAction(xmlPathField, this, resultJAX));
        parserButtonsPanel.add(jaxbButton);

        JButton transformButton = new JButton("Transform");
        transformButton.addActionListener(new TransformAction(xmlPathField, xsltPathField, this, resultPane));
        parserButtonsPanel.add(transformButton);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        topPanel.add(parserButtonsPanel, gbc);

        mainPanel.add(topPanel, BorderLayout.NORTH);

        add(mainPanel);
    }

    private class BrowseAction implements ActionListener {
        private JTextField targetField;
        private String title;
        private String extension;

        public BrowseAction(JTextField targetField, String title, String extension) {
            this.targetField = targetField;
            this.title = title;
            this.extension = extension;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(title);
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setFileFilter(new FileNameExtensionFilter(
                    extension.toUpperCase() + " Files (*." + extension + ")", extension));

            int returnValue = fileChooser.showOpenDialog(XsltViewer.this);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                targetField.setText(selectedFile.getAbsolutePath());
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XsltViewer viewer = new XsltViewer();
            viewer.setVisible(true);
        });
    }
}