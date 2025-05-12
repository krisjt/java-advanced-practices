package pl.edu.pwr.knowak.app.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlRootElement(name = "row")
@XmlType(propOrder = { "brth_yr", "gndr", "ethcty", "nm", "cnt", "rnk"})
public class BabyName {
    private int brth_yr;
    private String gndr;
    private String ethcty;
    private String nm;
    private int cnt;
    private int rnk;

    public BabyName() {
    }

    public int getBrth_yr() {
        return brth_yr;
    }

    @XmlElement(name = "brth_yr")
    public void setBrth_yr(int brth_yr) {
        this.brth_yr = brth_yr;
    }

    public String getGndr() {
        return gndr;
    }
    @XmlElement(name = "gndr")
    public void setGndr(String gndr) {
        this.gndr = gndr;
    }

    public String getEthcty() {
        return ethcty;
    }
    @XmlElement(name = "ethcty")
    public void setEthcty(String ethcty) {
        this.ethcty = ethcty;
    }

    public String getNm() {
        return nm;
    }
    @XmlElement(name = "nm")
    public void setNm(String nm) {
        this.nm = nm;
    }

    public int getCnt() {
        return cnt;
    }
    @XmlElement(name = "cnt")
    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public int getRnk() {
        return rnk;
    }
    @XmlElement(name = "rnk")
    public void setRnk(int rnk) {
        this.rnk = rnk;
    }

    @Override
    public String toString() {
        return "BabyName{" +
                "brth_yr=" + brth_yr +
                ", gndr='" + gndr + '\'' +
                ", ethcty='" + ethcty + '\'' +
                ", nm='" + nm + '\'' +
                ", cnt=" + cnt +
                ", rnk=" + rnk +
                "}\n";
    }
}
