package pl.edu.pwr.apiconnetion.models;

import com.google.gson.annotations.JsonAdapter;
import pl.edu.pwr.apiconnetion.AuthorDeserializer;

import java.util.List;

public class Book {
    public String title;
    public String genre;
    @JsonAdapter(AuthorDeserializer.class)
    public List<String> author;
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
