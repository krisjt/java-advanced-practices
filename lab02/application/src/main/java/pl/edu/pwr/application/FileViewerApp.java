package pl.edu.pwr.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.edu.pwr.algorithms.AverageAlgorithm;
import pl.edu.pwr.algorithms.IAlgorithm;
import pl.edu.pwr.models.Measurement;
import pl.edu.pwr.providers.AtmosphericCSVProvider;
import pl.edu.pwr.providers.IProvider;

import java.io.File;
import java.util.HashMap;

public class FileViewerApp extends Application {

    private TextArea fileContentArea;
    private TextArea statsArea;
    private TextField firstRecord;
    private TextField lastRecord;
    private Label isInMemoryLabel;
    IAlgorithm algorithm;
    IProvider<Measurement> provider;
    private static final String DIRECTORY = "/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab02/lab02-data/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("File Viewer Application");

        TreeView<File> fileTreeView = createFileTreeView();

        provider = new AtmosphericCSVProvider(DIRECTORY);
        fileContentArea = new TextArea();
        fileContentArea.setEditable(false);
        statsArea = new TextArea();
        statsArea.setEditable(false);

        isInMemoryLabel = new Label();

        firstRecord = new TextField();
        firstRecord.setPromptText("Enter first record number");

        lastRecord = new TextField();
        lastRecord.setPromptText("Enter last record number");

        Button showRecordsButton = new Button("Show Records");
        showRecordsButton.setOnAction(e -> {
            TreeItem<File> selectedItem = fileTreeView.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.getValue().isFile()) {
                displayFileContent(selectedItem.getValue());
            }
        });

        VBox rightPanel = new VBox(10,
                new Label("Is In Memory:"), isInMemoryLabel,
                new Label("First Record:"), firstRecord,
                new Label("Last Record:"), lastRecord,
                showRecordsButton,
                new Label("File Content:"), fileContentArea,
                new Label("Statistics:"), statsArea
        );

        BorderPane root = new BorderPane();
        root.setLeft(fileTreeView);
        root.setCenter(rightPanel);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private TreeView<File> createFileTreeView() {
        File rootFile = new File(DIRECTORY);

        TreeItem<File> rootItem = new TreeItem<>(rootFile);
        rootItem.setExpanded(true);
        createTree(rootItem);

        TreeView<File> treeView = new TreeView<>(rootItem);
        treeView.setCellFactory(param -> new TreeCell<File>() {
            @Override
            protected void updateItem(File item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        treeView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.getValue().isFile()) {
                displayFileContent(newValue.getValue());
            }
        });

        return treeView;
    }

    private void createTree(TreeItem<File> item) {
        if (item.getValue().isDirectory()) {
            File[] files = item.getValue().listFiles();
            if (files != null) {
                for (File file : files) {
                    TreeItem<File> newItem = new TreeItem<>(file);
                    item.getChildren().add(newItem);
                    if (file.isDirectory()) {
                        createTree(newItem);
                    }
                }
            }
        }
    }

    private void displayFileContent(File file) {
        try {
            provider.setFile(file);
            updateInMemoryStatusDisplay();
            displayFileRecords();
            displayStatistics();
        } catch (NumberFormatException e) {
            handleNumberFormatException(e);
        } catch (Exception e) {
            handleGenericException(e);
        }
    }

    private void updateInMemoryStatusDisplay() {
        if (provider.isInMemory()) {
            isInMemoryLabel.setText("File was in memory.");
            isInMemoryLabel.setStyle("-fx-background-color: lightgreen; -fx-padding: 5px; -fx-border-radius: 5px;");
        } else {
            isInMemoryLabel.setText("File wasn't in memory. It was recovered from csv file.");
            isInMemoryLabel.setStyle("-fx-background-color: lightcoral; -fx-padding: 5px; -fx-border-radius: 5px;");
        }
    }

    private void displayFileRecords() {
        HashMap<String, Measurement> map = provider.getData();
        int first = getFirstRecordNumber();
        int last = getLastRecordNumber(map.size());

        String content = provider.getDataString(first, last);
        fileContentArea.setText(content);

        algorithm = new AverageAlgorithm(provider.getData(), first, last);
    }

    private int getFirstRecordNumber() {
        return !firstRecord.getText().isEmpty() ? Integer.parseInt(firstRecord.getText()) : 1;
    }

    private int getLastRecordNumber(int defaultValue) {
        return !lastRecord.getText().isEmpty() ? Integer.parseInt(lastRecord.getText()) : defaultValue;
    }

    private void displayStatistics() {
        StringBuilder stats = new StringBuilder();

        String algorithmResult = algorithm.getResults();
        stats.append(algorithmResult);

        appendMemoryStatistics(stats);

        statsArea.setText(stats.toString());
    }

    private void appendMemoryStatistics(StringBuilder stats) {
        Runtime rt = Runtime.getRuntime();
        long totalMemory = rt.totalMemory() / (1024 * 1024);
        long freeMemory = rt.freeMemory() / (1024 * 1024);
        long usedMemory = totalMemory - freeMemory;

        stats.append(String.format("%nMemory Stats:%n"));
        stats.append(String.format("Total Memory: %d MB%n", totalMemory));
        stats.append(String.format("Used Memory: %d MB%n", usedMemory));
        stats.append(String.format("Free Memory: %d MB%n", freeMemory));
    }

    private void handleNumberFormatException(NumberFormatException e) {
        fileContentArea.setText("Invalid record numbers. Please enter valid integers.");
        e.printStackTrace();
    }

    private void handleGenericException(Exception e) {
        fileContentArea.setText("Error reading data.");
        e.printStackTrace();
    }
}