package com.example.gui;

import javafx.application.Application;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import org.example.MessageDigestAlgorithm;
import org.example.Zipper;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class FileBrowserApp extends Application {

    private final Zipper zipper = new Zipper();
    private MessageDigestAlgorithm messageDigestAlgorithm = new MessageDigestAlgorithm();
    private File currentDirectory;
    private String chosenFilePath = "";
    private String firstComparedFile = "";
    private String secondComparedFile = "";
    private ListView<String> listView = new ListView<>();
    private Map<String, String> filePathMap = new HashMap<>();

    @Override
    public void start(Stage primaryStage) {
        Button browseButton = new Button("Przeglądaj pliki");
        browseButton.setOnAction(e -> showFileBrowserScene(primaryStage));

        Button compareButton = new Button("Compare files");
        compareButton.setOnAction(e -> compareFiles(primaryStage));

        VBox mainMenu = new VBox(10, browseButton, compareButton);
        Scene mainScene = new Scene(mainMenu, 400, 300);

        primaryStage.setTitle("Menu Główne");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private void showFileBrowserScene(Stage stage) {
        Button backButton = new Button("Powrót");
        Button zipButton = new Button("Zip file");
        Button unzipButton = new Button("Unzip file");
        backButton.setOnAction(e -> start(stage));

        zipButton.setOnAction(e -> {
            try {
                if(!Objects.equals(chosenFilePath, "")){
                    zipper.zip(new File(chosenFilePath), chosenFilePath);
                    if (currentDirectory != null) {
                        listFiles(currentDirectory);
                    }
                }
                else{
                    System.out.println("No file has been chosen.");
                }
            } catch (IOException | NoSuchAlgorithmException ex) {
                System.out.println("There has been an issue while zipping a file.");
                ex.printStackTrace();
            }
        });

        unzipButton.setOnAction((e -> {
            if(!Objects.equals(chosenFilePath, "")) {
                try {
                    String noExtension = chosenFilePath;
                    int slashIndex = noExtension.lastIndexOf('/');
                    if (slashIndex > 0) {
                        noExtension = noExtension.substring(0,slashIndex);
                    }

                    System.out.println(noExtension);
                    zipper.unzipFile(noExtension + "/unpacked", chosenFilePath);
                    if (currentDirectory != null) {
                        listFiles(currentDirectory);
                    }
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            else{
                System.out.println("No file has been chosen.");
            }
        }));

        Button chooseDirButton = new Button("Wybierz katalog");
        chooseDirButton.setOnAction(e -> chooseDirectory(stage));

        listView.setOnMouseClicked(event -> {
            String selectedFile = listView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                handleFileSelection(filePathMap.get(selectedFile));
            }
        });

        VBox root = new VBox(10, chooseDirButton, listView, backButton, zipButton, unzipButton);
        Scene fileBrowserScene = new Scene(root, 400, 300);

        stage.setScene(fileBrowserScene);
    }

    private void compareFiles(Stage stage) {
        Button backButton = new Button("Powrót");
        backButton.setOnAction(e -> start(stage));

        Button chooseFirstFileButton = new Button("Wybierz pierwszy plik");
        Button chooseSecondFileButton = new Button("Wybierz drugi plik");
        Button compareButton = new Button("Porównaj");
        compareButton.setDisable(true);

        Label resultLabel = new Label();
        Label firstFileLabel = new Label();
        Label secondFileLabel = new Label();

        chooseFirstFileButton.setOnAction(e -> {
            firstComparedFile = chooseFile(stage);
            checkIfFilesSelected(compareButton);
            firstFileLabel.setText(firstComparedFile);
        });
        chooseSecondFileButton.setOnAction(e -> {
            secondComparedFile = chooseFile(stage);
            checkIfFilesSelected(compareButton);
            secondFileLabel.setText(secondComparedFile);
        });

        compareButton.setOnAction(e -> {
            if (!firstComparedFile.isEmpty() && !secondComparedFile.isEmpty()) {
                String result = null;
                try {
                    result = performComparison(firstComparedFile, secondComparedFile);
                } catch (NoSuchAlgorithmException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                resultLabel.setText(result);
            } else {
                resultLabel.setText("Nie wybrano obu plików.");
            }
        });

        VBox root = new VBox(10.0, backButton, chooseFirstFileButton, chooseSecondFileButton, compareButton, resultLabel, firstFileLabel, secondFileLabel);
        Scene compareScene = new Scene(root, 400, 300);
        stage.setScene(compareScene);
    }

    private String chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik");
        File selectedFile = fileChooser.showOpenDialog(stage);
        return (selectedFile != null) ? selectedFile.getAbsolutePath() : "";
    }

    private void checkIfFilesSelected(Button compareButton) {
        if (!firstComparedFile.isEmpty() && !secondComparedFile.isEmpty()) {
            compareButton.setDisable(false);
        }
    }

    private String performComparison(String file1, String file2) throws NoSuchAlgorithmException, IOException {
        if(messageDigestAlgorithm.isEqual(file1,file2))return "Files are egual";
        return "Files aren't equal";
    }


    private void chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Wybierz katalog");

        File selectedDirectory = directoryChooser.showDialog(stage);

        if (selectedDirectory != null) {
            listFiles(selectedDirectory);
            this.currentDirectory = selectedDirectory;
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
