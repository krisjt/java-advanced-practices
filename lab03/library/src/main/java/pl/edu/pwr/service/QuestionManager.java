package pl.edu.pwr.service;

import pl.edu.pwr.apiconnetion.models.Author;
import pl.edu.pwr.apiconnetion.models.Book;
import pl.edu.pwr.apiconnetion.models.Genre;
import pl.edu.pwr.apiconnetion.models.Kind;

import java.util.List;

public class QuestionManager {

    private List<Author> authors;
    private List<Book> books;
    private List<Kind> kinds;
    private List<Genre> genres;

    public QuestionManager(Language language) {
    }
}
