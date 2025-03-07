package com.example.gui;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.example.MessageDigestAlgorithm;
import org.example.Zipper;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class HelloApplication extends Application {

    private final ListView<String> listView = new ListView<>();
    private final Map<String, String> filePathMap = new HashMap<>();
    private String chosenFilePath = "";
    private final MessageDigestAlgorithm messageDigestAlgorithm = new MessageDigestAlgorithm();
    private final Zipper zipper = new Zipper();

    @Override
    public void start(Stage primaryStage) {
        Button browseButton = new Button("Choose directory");
        Button zipButton = new Button("Zip file");
        Button unzipButton = new Button("Unzip file");
        browseButton.setOnAction(e -> chooseDirectory(primaryStage));
        zipButton.setOnAction(e -> {
            try {
                zipper.zip(new File(chosenFilePath),chosenFilePath);
            } catch (IOException | NoSuchAlgorithmException ex) {
                System.out.println("There has been an issue while zipping a file.");
                ex.printStackTrace();
            }
        });

        listView.setOnMouseClicked(event -> {
            String selectedFile = listView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                handleFileSelection(filePathMap.get(selectedFile));
            }
        });

        VBox root = new VBox(10, browseButton, listView, zipButton);
        Scene scene = new Scene(root, 400, 300);

        primaryStage.setTitle("Zipper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            listFiles(selectedDirectory);
        }
    }

    private void listFiles(File directory) {
        listView.getItems().clear();
        filePathMap.clear();
        File[] files = directory.listFiles();

        if (files != null) {
            for (File file : files) {
                listView.getItems().add(file.getName());
                filePathMap.put(file.getName(), file.getAbsolutePath());
            }
        }
    }

    private void handleFileSelection(String filePath) {
        chosenFilePath = filePath;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
