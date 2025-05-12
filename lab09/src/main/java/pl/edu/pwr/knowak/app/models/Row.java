package pl.edu.pwr.knowak.app.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "row")
public class Row {
    private List<BabyName> rows = new ArrayList<>();

    @XmlElement(name = "row")
    public List<BabyName> getRows() {
        return rows;
    }

    public void setRow(List<BabyName> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "Row{" +
                "rows=" + rows +
                "}\n";
    }
}
