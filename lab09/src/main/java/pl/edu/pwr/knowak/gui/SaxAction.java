package pl.edu.pwr.knowak.gui;

import org.xml.sax.SAXException;
import pl.edu.pwr.knowak.app.utils.JaxpParser;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class SaxParser implements ActionListener {

    private JTextField xmlPathField;
    private XsltViewer xsltViewer;
    private JTextArea resultPane;
    private JaxpParser jaxpParser = new JaxpParser();

    public SaxParser(JTextField xmlPathField, XsltViewer xsltViewer, JTextArea resultPane) {
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

        try {
            resultPane.setText(jaxpParser.parseWithSax(xmlPath).toString());
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }
}