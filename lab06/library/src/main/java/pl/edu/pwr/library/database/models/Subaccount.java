package pl.edu.pwr.library.database.models;

import jakarta.persistence.*;

@Entity
@Table(name="subkonto")
public class Subaccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String login;
    @Column(name="haslo")
    private String password;
    @Column(name="aktywne")
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "abonament_id", referencedColumnName = "id")
    private Abonament abonament;

    public Subaccount() {
    }

    public Subaccount(String login, String password, boolean active, Abonament abonament) {
        this.login = login;
        this.password = password;
        this.active = active;
        this.abonament = abonament;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Abonament getAbonament() {
        return abonament;
    }

    public void setAbonament(Abonament abonament) {
        this.abonament = abonament;
    }

    @Override
    public String toString() {
        return "Subaccount{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                ", abonament=" + abonament +
                '}';
    }
}
