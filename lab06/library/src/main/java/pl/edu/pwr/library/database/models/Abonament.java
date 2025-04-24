package pl.edu.pwr.library.database.models;

import jakarta.persistence.*;

@Entity
public class Abonament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Type abonamentType;
    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    public Abonament() {
    }

    public Abonament(Type abonamentType, Client client) {
        this.abonamentType = abonamentType;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Type getAbonamentType() {
        return abonamentType;
    }

    public void setAbonamentType(Type abonamentType) {
        this.abonamentType = abonamentType;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Abonament{" +
                "id=" + id +
                ", abonamentType=" + abonamentType +
                ", client=" + client +
                '}';
    }
}
