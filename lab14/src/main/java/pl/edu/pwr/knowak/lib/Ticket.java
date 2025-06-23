package pl.edu.pwr.knowak.lib;

public class Ticket implements Comparable<Ticket>{
    private Category category;
    private Integer number;
    private String id;

    public Ticket(Category category, Integer number) {
        this.category = category;
        this.number = number;
        StringBuilder stringBuilder = new StringBuilder();
        id = stringBuilder.append(category.getName()).append(number).toString();
    }

    public Category getCategory() {
        return category;
    }

    public Integer getNumber() {
        return number;
    }

    public String getId() {
        return id;
    }

    @Override
    public int compareTo(Ticket o) {
        int categoryComparison = Integer.compare(this.category.getPriority(), o.category.getPriority());
        if (categoryComparison != 0) {
            return categoryComparison;
        }
        return Integer.compare(this.number, o.number);
    }

    @Override
    public String toString() {
        return "{id='" + id + '}';
    }
}
