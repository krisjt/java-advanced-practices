package pl.edu.pwr.knowak.gui;

import pl.edu.pwr.knowak.app.utils.XsltApplier;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TransformAction implements ActionListener {

    private JTextField xmlPathField;
    private JTextField xsltPathField;
    private XmlViewer xmlViewer;
    private JEditorPane resultPane;
    private XsltApplier xsltApplier = new XsltApplier();

    public TransformAction(JTextField xmlPathField, JTextField xsltPathField, XmlViewer xmlViewer, JEditorPane resultPane) {
        this.xmlPathField = xmlPathField;
        this.xsltPathField = xsltPathField;
        this.xmlViewer = xmlViewer;
        this.resultPane = resultPane;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String xmlPath = xmlPathField.getText();
        String xsltPath = xsltPathField.getText();
        resultPane.setContentType("text/html");

        if (xmlPath.isEmpty() || xsltPath.isEmpty()) {
            JOptionPane.showMessageDialog(xmlViewer,
                    "Please select both XML and XSLT files",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultPane.setText(xsltApplier.applyXslt(xmlPath,xsltPath));
    }
}