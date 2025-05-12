package pl.edu.pwr.knowak.app.utils;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import pl.edu.pwr.knowak.app.models.Response;

import java.io.File;

public class JaxbParser {
    public Response unmarshall(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            File xmlFile = new File(filename);

            return (Response) unmarshaller.unmarshal(xmlFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}