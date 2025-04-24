package pl.edu.pwr.library.database.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="platnosci")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="cena")
    private float price;
    @Column(name="termin")
    private LocalDate payday;
    @OneToOne
    @JoinColumn
    private Receivable receivable;

    public Payment() {
    }

    public Payment(float price, LocalDate payday, Receivable receivable) {
        this.price = price;
        this.payday = payday;
        this.receivable = receivable;
    }

    public Payment(float price, LocalDate payday) {
        this.price = price;
        this.payday = payday;
    }

    public Receivable getReceivables() {
        return receivable;
    }

    public void setReceivables(Receivable receivable) {
        this.receivable = receivable;
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

    public LocalDate getPayday() {
        return payday;
    }

    public void setPayday(LocalDate payday) {
        this.payday = payday;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "id=" + id +
                ", price=" + price +
                ", payday=" + payday +
                '}';
    }
}

