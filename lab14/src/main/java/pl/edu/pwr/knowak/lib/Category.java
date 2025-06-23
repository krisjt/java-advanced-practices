package pl.edu.pwr.knowak.lib;

public class Category implements Comparable<Category>{
    private String name;
    private Integer priority;
    private Integer currentMaxNumber = 0;

    public Category(String name, Integer priority) {
        this.name = name;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }
    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public void setCurrentMaxNumber(Integer currentMaxNumber) {
        this.currentMaxNumber = currentMaxNumber;
    }

    public Integer getCurrentMaxNumber() {
        return currentMaxNumber;
    }

    @Override
    public int compareTo(Category o) {
        return Integer.compare(getPriority(),o.getPriority());
    }

    @Override
    public String toString() {
        return "Category{" +
                "name='" + name + '\'' +
                ", priority=" + priority +
                '}';
    }
}
