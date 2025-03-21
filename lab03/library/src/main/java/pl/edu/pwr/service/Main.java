package pl.edu.pwr.service;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {

//        BookReader authorsReader = new BookReader();
//        System.out.println(authorsReader.getData());
//        wypelnianieFormatow();
//        GenreReader genreReader = new GenreReader();
//        System.out.println(genreReader.getKinds());
//        GuessingQuestion guessingQuestion = new GuessingQuestion(Language.LV);
//        System.out.println(guessingQuestion.getQuestion(Subject.Genre));
//        GuessingQuestion genreQuestion = new GuessingQuestion(Language.EN);
//        System.out.println(genreQuestion.getQuestion(Subject.Author));
        GuessingQuestion guessingQuestion2 = new GuessingQuestion(Language.PL);
//        System.out.println(guessingQuestion2.getQuestion(Subject.Author));
//        GuessingQuestion guessingQuestion3 = new GuessingQuestion(Language.EN);
//        System.out.println(guessingQuestion3.getQuestion(Subject.Genre));
//        GuessingQuestion guessingQuestion4 = new GuessingQuestion(Language.PL);
//        System.out.println(guessingQuestion4.getQuestion(Subject.Kind));
//        System.out.println(guessingQuestion4.getQuestion(Subject.Kind));
//        GuessingQuestion guessingQuestion5 = new GuessingQuestion(Language.EN);
//        System.out.println(guessingQuestion5.getQuestion(Subject.Kind));
//        System.out.println(guessingQuestion2.getQuestionThreeGaps());
        System.out.println(guessingQuestion2.getNumberQuestion());
    }

    private static void wypelnianieFormatow(){
        //WYPELNIANIE FORMATOW
        String language = "pl";
        String country = "PL";

        Locale l = new Locale(language, country);

        ResourceBundle rb1 = ResourceBundle.getBundle("pl.edu.pwr.resource.Question", l);
        ResourceBundle rb2 = ResourceBundle.getBundle("Question", l);

        System.out.println(rb1.getString("inquire"));

        MessageFormat mf = new MessageFormat("");
        mf.setLocale(l);

        Object[] arguments = { rb2.getString("oneKind"), 7, new Date() };
        mf.applyPattern(rb2.getString("guessGenre"));

        String output = mf.format(arguments);
        System.out.println(output);
    }

}