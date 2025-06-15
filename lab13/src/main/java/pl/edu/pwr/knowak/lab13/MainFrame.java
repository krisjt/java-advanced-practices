package pl.edu.pwr.knowak.lab13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainFrame extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/pl/edu/pwr/knowak/lab13/MainFrame.fxml"));

        VBox root = loader.load();
        Scene scene = new Scene(root);

        String cssPath = "/pl/edu/pwr/knowak/lab13/application.css";
        scene.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Motivational quotes");
        stage.show();
    }

    public static List<String> loadTextsFromResource(String resourcePath) {
        List<String> texts = new ArrayList<>();
        try (InputStream is = MainFrame.class.getResourceAsStream(resourcePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            while ((line = br.readLine()) != null) {
                texts.add(line);
            }
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading resource: " + resourcePath);
        }
        return texts;
    }
}