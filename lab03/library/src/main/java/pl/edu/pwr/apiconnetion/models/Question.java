package pl.edu.pwr.apiconnetion.models;

import java.util.Map;

public record Question(String questionContent, Map<String, Boolean> answers) {
}
