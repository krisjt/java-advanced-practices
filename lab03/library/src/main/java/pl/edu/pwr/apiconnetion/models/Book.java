package pl.edu.pwr.apiconnetion.models;

public class Book {
    public String title;
    public String genre;
    public String author;
    public String kind;
    public String epoch;

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", author='" + author + '\'' +
                ", kind='" + kind + '\'' +
                ", epoch='" + epoch + '\'' +
                '}';
    }
}
