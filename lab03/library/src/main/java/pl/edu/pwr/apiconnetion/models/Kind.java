package pl.edu.pwr.apiconnetion.models;

public class Kind {
    private String name;

    @Override
    public String toString() {
        return "Kind{" +
                "name='" + name + '\'' +
                '}';
    }

    public Kind() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
