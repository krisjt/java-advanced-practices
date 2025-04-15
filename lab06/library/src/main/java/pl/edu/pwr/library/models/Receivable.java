package pl.edu.pwr.library.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="naleznosci")
public class Receivable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name="cena")
    private float price;
    private float alreadyPayed;
    @Column(name="termin")
    private LocalDate payday;
    @ManyToOne
    @JoinColumn(name = "abonament_id", referencedColumnName = "id")
    private Abonament abonament;
    @Column
    private boolean payed;

    public Receivable() {
    }

    public Receivable(float price, float alreadyPayed, LocalDate payday, Abonament abonament, boolean payed) {
        this.price = price;
        this.alreadyPayed = alreadyPayed;
        this.payday = payday;
        this.abonament = abonament;
        this.payed = payed;
    }

    public float getAlreadyPayed() {
        return alreadyPayed;
    }

    public void setAlreadyPayed(float alreadyPayed) {
        this.alreadyPayed = alreadyPayed;
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

    public Abonament getAbonament() {
        return abonament;
    }

    public void setAbonament(Abonament abonament) {
        this.abonament = abonament;
    }

    public boolean isPayed() {
        return payed;
    }

    public void setPayed(boolean payed) {
        this.payed = payed;
    }

    @Override
    public String toString() {
        return "Receivables{" +
                "id=" + id +
                ", price=" + price +
                ", payday=" + payday +
                ", abonament=" + abonament +
                '}';
    }

}
