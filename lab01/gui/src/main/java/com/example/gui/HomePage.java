package com.example.gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HomePage extends Application {
    private FileComparisionView fileComparisonView;
    private FileZipperView fileZipperView;

    @Override
    public void start(Stage primaryStage) {
        fileComparisonView = new FileComparisionView(this);
        fileZipperView = new FileZipperView(this);

        Button browseButton = new Button("Explore files");
        browseButton.setOnAction(e -> fileZipperView.showFileBrowserScene(primaryStage));

        Button compareButton = new Button("Compare files");
        compareButton.setOnAction(e -> fileComparisonView.compareFiles(primaryStage));

        VBox mainMenu = new VBox(10, browseButton, compareButton);
        Scene mainScene = new Scene(mainMenu, 400, 300);

        primaryStage.setTitle("Main menu");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}