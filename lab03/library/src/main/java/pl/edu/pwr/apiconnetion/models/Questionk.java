package pl.edu.pwr.apiconnetion.models;

import java.util.Map;

public class Questionk {

    private String questionContent;
    private Map<String, Boolean> answers;

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

    public Map<String, Boolean> getAnswers() {
        return answers;
    }

    public void setAnswers(Map<String, Boolean> answers) {
        this.answers = answers;
    }

    public Questionk(String questionContent, Map<String, Boolean> answers) {
        this.questionContent = questionContent;
        this.answers = answers;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionContent='" + questionContent + '\'' +
                ", answers=" + answers +
                '}';
    }
}
