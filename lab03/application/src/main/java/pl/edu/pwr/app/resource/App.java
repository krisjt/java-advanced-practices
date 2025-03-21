package pl.edu.pwr.app.resource;

import java.util.ListResourceBundle;

public class App extends ListResourceBundle {
    @Override
    protected Object[][] getContents() {
        return new Object[][] {
                {"welcome", "Welcome to the Quiz!"},
                {"question.number", "Enter the number of questions:"},
                {"question", "Question"},
                {"error.number.of.questions", "Please enter a valid number of questions."},
                {"error.valid.number", "Please enter a valid number."},
                {"next", "Next"},
                {"summary", "Summary"},
                {"result", "You scored {0} points out of {1} questions!"},
                {"start", "Start"},
                {"restart", "Restart"},
                {"language.select", "Choose language"}
        };
    }
}

