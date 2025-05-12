package pl.edu.pwr.knowak.jaxb;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import pl.edu.pwr.knowak.jaxb.models.Response;

import java.io.File;

public class BabyNameParser {
    public Response unmarshall(String filename) {
        try {
            JAXBContext context = JAXBContext.newInstance(Response.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            File xmlFile = new File(filename);

            Response response = (Response) unmarshaller.unmarshal(xmlFile);
            System.out.println(response.toString());
            return response;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}