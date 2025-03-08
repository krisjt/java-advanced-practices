package com.example.gui;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.example.Zipper;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class FileZipperView {
    private final HomePage homePage;
    private final Zipper zipper = new Zipper();
    private File currentDirectory;
    private String chosenFilePath = "";
    private final ListView<String> listView = new ListView<>();
    private final Map<String, String> filePathMap = new HashMap<>();

    public FileZipperView(HomePage homePage) {
        this.homePage = homePage;
    }

    void showFileBrowserScene(Stage stage) {
        Button backButton = new Button("Return");
        backButton.setOnAction(e -> homePage.start(stage));

        Button zipButton = new Button("Zip file");
        zipButton.setOnAction(e -> zipFile());

        Button unzipButton = new Button("Unzip file");
        unzipButton.setOnAction(e -> unzipFile());

        Button chooseDirButton = new Button("Choose directory");
        chooseDirButton.setOnAction(e -> chooseDirectory(stage));

        listView.setOnMouseClicked(event -> {
            String selectedFile = listView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                chosenFilePath = filePathMap.get(selectedFile);
            }
        });

        VBox root = new VBox(10, chooseDirButton, listView, backButton, zipButton, unzipButton);
        stage.setScene(new Scene(root, 400, 300));
    }

    private void chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");
        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            currentDirectory = selectedDirectory;
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

    private void zipFile() {
        if (!chosenFilePath.isEmpty()) {
            try {
                zipper.zip(new File(chosenFilePath), chosenFilePath);
                if (currentDirectory != null) {
                    listFiles(currentDirectory);
                }
            } catch (IOException | NoSuchAlgorithmException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void unzipFile() {
        if (!chosenFilePath.isEmpty()) {
            try {
                String noExtension = chosenFilePath;
                int slashIndex = noExtension.lastIndexOf('/');
                if (slashIndex > 0) {
                    noExtension = noExtension.substring(0,slashIndex);
                }
                System.out.println(noExtension);
                zipper.unzipFile(noExtension+"/unpacked", chosenFilePath);
                if (currentDirectory != null) {
                    listFiles(currentDirectory);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}