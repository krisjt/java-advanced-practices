package pl.edu.pwr.knowak.lab13;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

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
        stage.setTitle("A FXML Example without any Controller");
        stage.show();
    }
}