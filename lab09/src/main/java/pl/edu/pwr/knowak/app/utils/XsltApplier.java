package pl.edu.pwr.knowak.xslt;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.StringWriter;

public class StyleApplier {

    public String applyXslt(String xmlPath, String xsltPath) {
        try {
            Source xmlSource = new StreamSource(new File(xmlPath));
            Source xsltSource = new StreamSource(new File(xsltPath));

            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer(xsltSource);

            StringWriter writer = new StringWriter();
            Result result = new StreamResult(writer);
            transformer.transform(xmlSource, result);

            return writer.toString();

        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }

}
