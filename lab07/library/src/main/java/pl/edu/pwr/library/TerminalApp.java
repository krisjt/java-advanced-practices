package pl.edu.pwr.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TerminalApp {

    public static void main(String[] args) {
        SpringApplication.run(TerminalApp.class, args);
    }

}