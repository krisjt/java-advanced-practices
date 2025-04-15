package pl.edu.pwr.library;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class TerminalApp implements CommandLineRunner {

    private final App app;

    public TerminalApp(App app) {
        this.app = app;
    }

    public static void main(String[] args) {
        SpringApplication.run(TerminalApp.class, args);
    }

    @Override
    public void run(String... args) {
        app.menu();
    }
}