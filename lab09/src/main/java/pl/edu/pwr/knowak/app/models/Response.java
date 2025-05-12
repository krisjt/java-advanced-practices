package pl.edu.pwr.knowak.jaxb.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {
    private Row row;

    @XmlElement(name = "row")
    public Row getRow() {
        return row;
    }

    public void setRow(Row row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "Response{" +
                "row=" + row +
                '}';
    }
}
