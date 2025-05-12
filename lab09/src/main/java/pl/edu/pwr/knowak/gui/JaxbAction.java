package pl.edu.pwr.knowak.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class JaxbParser implements ActionListener {

    private JTextField xmlPathField;
    private XsltViewer xsltViewer;
    private JTextArea resultPane;
    private pl.edu.pwr.knowak.app.utils.JaxbParser jaxbParser = new pl.edu.pwr.knowak.app.utils.JaxbParser();

    public JaxbParser(JTextField xmlPathField, XsltViewer xsltViewer, JTextArea resultPane) {
        this.xmlPathField = xmlPathField;
        this.xsltViewer = xsltViewer;
        this.resultPane = resultPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String xmlPath = xmlPathField.getText();

        if (xmlPath.isEmpty()) {
            JOptionPane.showMessageDialog(xsltViewer,
                    "Please select XML file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultPane.setText(jaxbParser.unmarshall(xmlPath).toString());
    }
}