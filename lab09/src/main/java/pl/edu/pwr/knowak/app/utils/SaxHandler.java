package pl.edu.pwr.knowak.jaxp;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import pl.edu.pwr.knowak.jaxb.models.BabyName;
import pl.edu.pwr.knowak.jaxb.models.Response;
import pl.edu.pwr.knowak.jaxb.models.Row;

import java.util.ArrayList;
import java.util.List;

public class SaxHandler extends DefaultHandler {
    private static final String BIRTH_YEAR = "brth_yr";
    private static final String ROW = "row";
    private static final String GENDER = "gndr";
    private static final String ETHNICITY = "ethcty";
    private static final String NAME = "nm";
    private static final String COUNT = "cnt";
    private static final String RANK = "rnk";

    private Response response;
    private Row row;
    private StringBuilder elementValue;

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (elementValue == null) {
            elementValue = new StringBuilder();
        } else {
            elementValue.append(ch, start, length);
        }
    }

    @Override
    public void startDocument() throws SAXException {
        response = new Response();
        row = new Row();
        row.setRow(new ArrayList<>());
        response.setRow(row);
    }

    @Override
    public void startElement(String uri, String lName, String qName, Attributes attr) throws SAXException {
        switch (qName) {
            case ROW:
                row.getRows().add(new BabyName());
                break;
            case BIRTH_YEAR:
                elementValue = new StringBuilder();
                break;
            case GENDER:
                elementValue = new StringBuilder();
                break;
            case ETHNICITY:
                elementValue = new StringBuilder();
                break;
            case NAME:
                elementValue = new StringBuilder();
                break;
            case COUNT:
                elementValue = new StringBuilder();
                break;
            case RANK:
                elementValue = new StringBuilder();
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case BIRTH_YEAR:
                latestBabyName().setBrth_yr(Integer.parseInt(String.valueOf((elementValue))));
                break;
            case GENDER:
                latestBabyName().setGndr(elementValue.toString());
                break;
            case ETHNICITY:
                latestBabyName().setEthcty(elementValue.toString());
                break;
            case NAME:
                latestBabyName().setNm(elementValue.toString());
                break;
            case COUNT:
                latestBabyName().setCnt(Integer.parseInt(String.valueOf((elementValue))));
                break;
            case RANK:
                latestBabyName().setRnk(Integer.parseInt(String.valueOf((elementValue))));
                break;
        }
    }

    private BabyName latestBabyName() {
        List<BabyName> babyNameList = row.getRows();
        int latestBabyNameIndex = babyNameList.size() - 1;
        return babyNameList.get(latestBabyNameIndex);
    }

    public Response getResponse() {
        return response;
    }
}
