package com.example.gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.MessageDigestAlgorithm;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class FileComparisionView {

    private MessageDigestAlgorithm messageDigestAlgorithm = new MessageDigestAlgorithm();
    private HomePage homePage;
    private String firstComparedPath = "";
    private String secondComparedPath = "";

    private enum SelectionType {
        FILE, DIRECTORY
    }

    public FileComparisionView(HomePage homePage) {
        this.homePage = homePage;
    }

    void compareFiles(Stage stage) {
        Button backButton = new Button("Back");
        backButton.setOnAction(e -> homePage.start(stage));

        Label firstLabel = new Label("First element:");
        ComboBox<SelectionType> firstTypeSelector = new ComboBox<>();
        firstTypeSelector.getItems().addAll(SelectionType.FILE, SelectionType.DIRECTORY);
        firstTypeSelector.setValue(SelectionType.FILE);
        Button chooseFirstButton = new Button("Choose");
        Label firstPathLabel = new Label("Nothing chosen");

        Label secondLabel = new Label("Second element:");
        ComboBox<SelectionType> secondTypeSelector = new ComboBox<>();
        secondTypeSelector.getItems().addAll(SelectionType.FILE, SelectionType.DIRECTORY);
        secondTypeSelector.setValue(SelectionType.FILE);
        Button chooseSecondButton = new Button("Choose");
        Label secondPathLabel = new Label("Nothing chosen");

        Button compareButton = new Button("Compare");
        compareButton.setDisable(true);

        Label resultLabel = new Label();

        chooseFirstButton.setOnAction(e -> {
            if (firstTypeSelector.getValue() == SelectionType.FILE) {
                firstComparedPath = chooseFile(stage);
            } else {
                firstComparedPath = chooseDirectory(stage);
            }

            firstPathLabel.setText(firstComparedPath.isEmpty() ? "Nothing chosen" : firstComparedPath);
            checkIfPathsSelected(compareButton);
        });

        chooseSecondButton.setOnAction(e -> {
            if (secondTypeSelector.getValue() == SelectionType.FILE) {
                secondComparedPath = chooseFile(stage);
            } else {
                secondComparedPath = chooseDirectory(stage);
            }

            secondPathLabel.setText(secondComparedPath.isEmpty() ? "Nothing chosen" : secondComparedPath);
            checkIfPathsSelected(compareButton);
        });

        compareButton.setOnAction(e -> {
            if (!firstComparedPath.isEmpty() && !secondComparedPath.isEmpty()) {
                try {
                    String result = performComparison(firstComparedPath, secondComparedPath);
                    resultLabel.setText(result);
                } catch (NoSuchAlgorithmException | IOException ex) {
                    resultLabel.setText("Error while comparing: " + ex.getMessage());
                }
            } else {
                resultLabel.setText("You haven't chosen two elements to compare.");
            }
        });

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10));
        grid.setHgap(10);
        grid.add(firstLabel, 0, 0);

        HBox firstSelectionBox = new HBox(10, firstTypeSelector, chooseFirstButton);
        grid.add(firstSelectionBox, 1, 0);

        grid.add(firstPathLabel, 0, 1, 2, 1);

        grid.add(secondLabel, 0, 2);

        HBox secondSelectionBox = new HBox(10, secondTypeSelector, chooseSecondButton);
        grid.add(secondSelectionBox, 1, 2);

        grid.add(secondPathLabel, 0, 3, 2, 1);

        HBox actionsBox = new HBox(10, backButton, compareButton);
        grid.add(actionsBox, 0, 4, 2, 1);

        grid.add(resultLabel, 0, 5, 2, 1);

        Scene compareScene = new Scene(grid, 500, 300);
        stage.setScene(compareScene);
        stage.setTitle("File/directory comparison");
    }

    private String chooseFile(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose file");
        File selectedFile = fileChooser.showOpenDialog(stage);
        return (selectedFile != null) ? selectedFile.getAbsolutePath() : "";
    }

    private String chooseDirectory(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose directory");
        File selectedDirectory = directoryChooser.showDialog(stage);
        return (selectedDirectory != null) ? selectedDirectory.getAbsolutePath() : "";
    }

    private void checkIfPathsSelected(Button compareButton) {
        compareButton.setDisable(firstComparedPath.isEmpty() || secondComparedPath.isEmpty());
    }

    private String performComparison(String path1, String path2) throws NoSuchAlgorithmException, IOException {
        if (messageDigestAlgorithm.isEqual(path1, path2)) {
            return "Elements are equal.";
        }
        return "Elements differ";
    }
}