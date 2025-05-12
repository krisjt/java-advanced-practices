package pl.edu.pwr.knowak.gui;

import org.xml.sax.SAXException;
import pl.edu.pwr.knowak.app.utils.JaxpParser;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

class DomAction implements ActionListener {

    private JTextField xmlPathField;
    private XmlViewer xmlViewer;
    private JEditorPane resultPane;
    private JaxpParser jaxpParser = new JaxpParser();

    public DomAction(JTextField xmlPathField, XmlViewer xmlViewer, JEditorPane resultPane) {
        this.xmlPathField = xmlPathField;
        this.xmlViewer = xmlViewer;
        this.resultPane = resultPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String xmlPath = xmlPathField.getText();
        resultPane.setContentType("text/plain");

        if (xmlPath.isEmpty()) {
            JOptionPane.showMessageDialog(xmlViewer,
                    "Please select XML file",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            resultPane.setText(jaxpParser.parseWithDOM(xmlPath));
        } catch (IOException | SAXException | ParserConfigurationException ex) {
            throw new RuntimeException(ex);
        }
    }
}