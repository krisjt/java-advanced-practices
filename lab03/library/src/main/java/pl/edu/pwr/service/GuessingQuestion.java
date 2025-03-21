package pl.edu.pwr.service;

import pl.edu.pwr.apiconnetion.*;
import pl.edu.pwr.apiconnetion.models.*;


import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.*;

public class GuessingQuestion {

    private Subject subject;
    private final Random random = new Random();
    private final AuthorsReader authorsReader = new AuthorsReader();
    private final KindReader kindReader = new KindReader();
    private final GenreReader genreReader = new GenreReader();
    private final Locale locale;
    private final ResourceBundle rb;
    private final BookReader bookReader = new BookReader();
    public GuessingQuestion(Language lang) {
        locale = new Locale(lang.toString().toLowerCase(),lang.toString());
        rb = ResourceBundle.getBundle("Question",locale);
//        rb = ResourceBundle.getBundle("pl.edu.pwr.resource.Question",locale);
    }

    public Question getQuestion(Subject subject){
        this.subject = subject;

        Book book = getBook();
        MessageFormat mf = new MessageFormat("");
        mf.setLocale(locale);
        mf.applyPattern(rb.getString("guess"+ subject));
        Object[] title = {book.title};
        Map<String,Boolean> answers = getAnswers(book);

        return new Question(mf.format(title),answers);
    }

    private Map<String,Boolean> getAnswers(Book book){
        if(subject==Subject.Author) return getData(book.author);
        if(subject==Subject.Genre) return getData(book.genre);
        if(subject==Subject.Kind) return getData(book.kind);
        return Collections.emptyMap();
    }

    private Map<String,Boolean> getData(String name){
        Reader reader1 = null;
        switch (subject){
            case Author -> reader1 = authorsReader;
            case Kind -> reader1 = kindReader;
            case Genre -> reader1 = genreReader;
        }

        int size = reader1.getList().size();
        Map<String,Boolean> answers = new HashMap<>();
        if(size > 4) {
            for (int i = 0; i < 3; i++) {
                if (Objects.equals(reader1.getName(random.nextInt(reader1.getList().size())), name)) {
                    i--;
                    continue;
                }
                answers.put(reader1.getName(random.nextInt(reader1.getList().size())), false);
            }
            answers.put(name,true);
        }
        else{
            for(int i = 0; i < size; i++){
                if(Objects.equals(reader1.getName(i), name))answers.put(reader1.getName(i),true);
                else answers.put(reader1.getName(i), false);
            }
        }
        return answers;
    }

    public Question getQuestionThreeGaps() {
        MessageFormat mf = new MessageFormat("");
        Object[] titles = new Object[4];
        List<Book> bookList = new ArrayList<>(getBooksList());

        String genre = switch (random.nextInt(3)) {
            case 0 -> "Epika";
            case 1 -> "Liryka";
            case 2 -> "Dramat";
            default -> throw new IllegalStateException("Unexpected value");
        };

        System.out.println(genre);

        bookList.removeIf(book -> !Objects.equals(book.kind, genre));

        if (bookList.isEmpty()) {
            throw new IllegalStateException("Brak książek w wybranym gatunku!");
        }

        for (int o = 0; o < 4; o++) {
            Book book = bookList.get(random.nextInt(bookList.size()));
            System.out.println(book);
            titles[o] = book.title;
        }

        mf.setLocale(locale);
        mf.applyPattern(rb.getString("matchKind"));

        Map<String, Boolean> answers = new HashMap<>();
        answers.put(rb.getString("genre.epic"), "Epika".equals(genre));
        answers.put(rb.getString("genre.lyric"), "Liryka".equals(genre));
        answers.put(rb.getString("genre.drama"), "Dramat".equals(genre));

        return new Question(mf.format(titles), answers);
    }

    public Question getNumberQuestion() {
        Map<String, Boolean> answers = new HashMap<>();

        double[] limits = {1, 2, 5};
        String[] formats = {rb.getString("oneKind"), rb.getString("someKinds"), rb.getString("manyKinds")};

        ChoiceFormat choiceFormat = new ChoiceFormat(limits, formats);

        for (int i = 1; i < 9; i++) {
            if (i == 3) continue;

            String kindForm = choiceFormat.format(i);
            answers.put(i + " " + kindForm, false);
        }

        answers.put(3 + " " + choiceFormat.format(3), true);

        return new Question(rb.getString("guessGenreNumber"), answers);
    }

    private Book getBook() {
        return bookReader.getList().get(random.nextInt(bookReader.getList().size()));
    }

    private List<Book> getBooksList(){
        return bookReader.getList();
    }

}
