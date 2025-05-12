package pl.edu.pwr.knowak.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class XmlViewer extends JFrame {

    private JEditorPane resultPane;
    private JTextField xmlPathField;
    private JTextField xsltPathField;
    private static final String XSLT_PROJECTROOT_PATH = "/src/main/java/pl/edu/pwr/knowak/app/xslt";

    public XmlViewer() {
        setTitle("XML Viewer");
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        xmlBrowseButton.addActionListener(e-> chooseFile(xmlPathField,"Select XML File",""));
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
        xsltBrowseButton.addActionListener(e-> chooseFile(xsltPathField,"Select XSLT File", XSLT_PROJECTROOT_PATH));
        topPanel.add(xsltBrowseButton, gbc);
        resultPane = new JEditorPane();
        resultPane.setEditable(false);
        resultPane.setContentType("text/html");
        JScrollPane leftScrollPane = new JScrollPane(resultPane);

        mainPanel.add(leftScrollPane, BorderLayout.CENTER);

        JPanel parserButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JButton domButton = new JButton("DOM");
        domButton.addActionListener(new DomAction(xmlPathField, this, resultPane));
        parserButtonsPanel.add(domButton);

        JButton saxButton = new JButton("SAX");
        saxButton.addActionListener(new SaxAction(xmlPathField, this, resultPane));
        parserButtonsPanel.add(saxButton);

        JButton jaxbButton = new JButton("JAXB");
        jaxbButton.addActionListener(new JaxbAction(xmlPathField, this, resultPane));
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

    private void chooseFile(JTextField targetField, String title, String path) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(title);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir"),path));
        int returnValue = fileChooser.showOpenDialog(XmlViewer.this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            targetField.setText(selectedFile.getAbsolutePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            XmlViewer viewer = new XmlViewer();
            viewer.setVisible(true);
        });
    }
}