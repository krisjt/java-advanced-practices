package pl.edu.pwr.service;

import pl.edu.pwr.apiconnetion.*;
import pl.edu.pwr.apiconnetion.models.*;


import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.util.*;

public class QuestionManager {

    private final Random random = new Random();
    private final AuthorsReader authorsReader = new AuthorsReader();
    private final KindReader kindReader = new KindReader();
    private final GenreReader genreReader = new GenreReader();
    private final List<Reader> readers = Arrays.asList(new KindReader(),new GenreReader(), new AuthorsReader());
    private final Locale locale;
    private final ResourceBundle rb;
    private final BookReader bookReader = new BookReader();
    public QuestionManager(Language lang) {
        locale = new Locale(lang.toString().toLowerCase(),lang.toString());
        rb = ResourceBundle.getBundle("Question",locale);
//        rb = ResourceBundle.getBundle("pl.edu.pwr.resource.Question",locale);
    }

    public Question getQuestion(Subject subject){

        Book book = getBook();
        MessageFormat mf = new MessageFormat("");
        mf.setLocale(locale);
        mf.applyPattern(rb.getString("guess"+ subject));
        Object[] title = {book.title};
        Reader reader = getReaderBySubject(subject);
        Map<String,Boolean> answers = getAnswers(book,reader,subject);

        return new Question(mf.format(title),answers);
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

        bookList.removeIf(book -> !Objects.equals(book.kind, genre));

        if (bookList.isEmpty()) {
            throw new IllegalStateException("Brak książek w wybranym gatunku!");
        }

        for (int o = 0; o < 4; o++) {
            Book book = bookList.get(random.nextInt(bookList.size()));
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


    private Reader getReaderBySubject(Subject subject){
        return readers.stream().filter(reader -> reader.getType()==subject).findFirst().orElse(null);
    }

    private Map<String,Boolean> getAnswers(Book book, Reader reader, Subject subject){
        if(subject==Subject.Author) return getData(book.author,reader);
        if(subject==Subject.Genre){
            List<String> list = new ArrayList<>();
            list.add(book.genre);
            return getData(list,reader);
        }
        if(subject==Subject.Kind){
            List<String> list = new ArrayList<>();
            list.add(book.kind);
            return getData(list,reader);
        }
        return Collections.emptyMap();
    }

    private Book getBook() {
        return bookReader.getList().get(random.nextInt(bookReader.getList().size()));
    }

    private List<Book> getBooksList(){
        return bookReader.getList();
    }

    private Map<String, Boolean> getData(List<String> name,Reader reader){

            int size = reader.getListSize();
            Map<String,Boolean> answers = new HashMap<>();
            if(size > 4) {
                for (int i = 0; i < 3; i++) {
                    if (name.contains(reader.getName(random.nextInt(reader.getListSize())))) {
                        i--;
                        continue;
                    }
                    answers.put(reader.getName(random.nextInt(reader.getListSize())), false);
                }
                for(String s : name){
                    answers.put(s,true);
                }
            }
            else{
                for(int i = 0; i < size; i++){
                    if(name.contains(reader.getName(i)))answers.put(reader.getName(i),true);
                    else answers.put(reader.getName(i), false);
                }
            }
            return answers;
    }

}
