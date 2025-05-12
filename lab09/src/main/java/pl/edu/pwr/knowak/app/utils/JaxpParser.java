package pl.edu.pwr.knowak.app.utils;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import pl.edu.pwr.knowak.app.models.Response;

import javax.xml.parsers.*;
import java.io.File;
import java.io.IOException;

public class JaxpParser {
    public String parseWithDOM(String filename) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new File(filename));

        Element responseElement = document.getDocumentElement();
        NodeList rows = responseElement.getElementsByTagName("row");
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < rows.getLength(); i++) {
            Element row = (Element) rows.item(i);

            String year = getTagValue(row, "brth_yr");
            String gender = getTagValue(row, "gndr");
            String ethnicity = getTagValue(row, "ethcty");
            String name = getTagValue(row, "nm");
            String count = getTagValue(row, "cnt");
            String rank = getTagValue(row, "rnk");

            stringBuilder.append("Row ").append(i + 1).append("\n");
            stringBuilder.append("Birth Year: ").append(year).append("\n");
            stringBuilder.append("Gender: ").append(gender).append("\n");
            stringBuilder.append("Ethnicity: ").append(ethnicity).append("\n");
            stringBuilder.append("Name: ").append(name).append("\n");
            stringBuilder.append("Count: ").append(count).append("\n");
            stringBuilder.append("Rank: ").append(rank).append("\n");
            stringBuilder.append("------------------------------\n");
        }
        return stringBuilder.toString();
    }

    private String getTagValue(Element parent, String tagName) {
        NodeList nodeList = parent.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "";
    }

    public Response parseWithSax(String filename) throws IOException, SAXException, ParserConfigurationException {

        SAXParserFactory saxFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxFactory.newSAXParser();
        SaxHandler saxHandler = new SaxHandler();

        saxParser.parse(filename, saxHandler);

        return saxHandler.getResponse();
    }
}
