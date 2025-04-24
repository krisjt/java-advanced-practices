package pl.edu.pwr.library.database.models;

import jakarta.persistence.*;

@Entity
@Table(name="cennik")
public class PriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="cena")
    private float price;
    @Column(name="aktywna")
    private boolean active;
    private Type abonamentType;

    public PriceList() {
    }

    public PriceList(float price, boolean active, Type abonamentType) {
        this.price = price;
        this.active = active;
        this.abonamentType = abonamentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Type getAbonamentType() {
        return abonamentType;
    }

    public void setAbonamentType(Type abonamentType) {
        this.abonamentType = abonamentType;
    }

    @Override
    public String toString() {
        return "PriceList{" +
                "id=" + id +
                ", price=" + price +
                ", active=" + active +
                ", abonamentType=" + abonamentType +
                '}';
    }
}
