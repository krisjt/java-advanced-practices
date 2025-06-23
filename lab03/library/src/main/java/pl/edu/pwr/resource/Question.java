package pl.edu.pwr.resource;

import java.util.ListResourceBundle;

public class Question extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"guessAuthor", "Who is the author of the book titled \"{0}\"?"},
                {"guessGenre", "What is the genre of the book \"{0}\"?"},
                {"guessKind", "What is the kind of the book \"{0}\"?"},
                {"matchKind", "These titles: a)\"{0}\", b)\"{1}\", c)\"{2}\", d)\"{3}\" are {4}?"},
                {"genre.epic", "epic"},
                {"genre.lyric", "lyric"},
                {"genre.drama", "drama"},
                {"guessGenreNumber", "How many genres are there?"},
                {"oneKind", "kind"},
                {"someKinds", "kinds"},
                {"manyKinds", "kinds"}
        };
    }
}