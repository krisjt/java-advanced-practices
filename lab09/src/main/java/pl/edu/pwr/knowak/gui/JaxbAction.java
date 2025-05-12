package pl.edu.pwr.knowak.gui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class JaxbAction implements ActionListener {

    private JTextField xmlPathField;
    private XmlViewer xmlViewer;
    private JEditorPane resultPane;
    private pl.edu.pwr.knowak.app.utils.JaxbParser jaxbParser = new pl.edu.pwr.knowak.app.utils.JaxbParser();

    public JaxbAction(JTextField xmlPathField, XmlViewer xmlViewer, JEditorPane resultPane) {
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

        resultPane.setText(jaxbParser.unmarshall(xmlPath).toString());
    }
}