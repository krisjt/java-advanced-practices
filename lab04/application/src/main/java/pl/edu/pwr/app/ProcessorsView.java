package pl.edu.pwr.app;

import pl.edu.pwr.app.modules.ClassManager;
import pl.edu.pwr.service.CustomStatusListener;
import pl.edu.pwr.service.ProcessManager;
import pl.edu.pwr.service.Processor;
import pl.edu.pwr.service.Status;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ProcessorsView {
    private JFrame frame;
    private CustomClassLoader customClassLoader;
    private ClassManager classManager;
    private List<Processor> processorInstances;
    private JList<Processor> objectsList;
    private JLabel infoLabel;
    private JLabel idLabel;
    private JLabel statusLabel;
    private JLabel resultLabel;
    private JButton newTaskButton;
    private CustomStatusListener statusListener = new CustomStatusListener();
    private HashMap<Integer, String> statusMap = new HashMap<>();
    private File selectedDirectory;
    private String packageName;
    private JTextField taskField;

    public ProcessorsView(File selectedDirectory, String packageName) {
        this.selectedDirectory = selectedDirectory;
        this.packageName = packageName;
//        this.customClassLoader = new CustomClassLoader(packageName, selectedDirectory.toPath());
        this.classManager = new ClassManager(packageName, selectedDirectory.toPath());
        this.processorInstances = new ArrayList<>();

        initializeFrame(selectedDirectory.getName());
        initializeUI();
        loadProcessors();
    }

    private void initializeFrame(String title) {
        frame = new JFrame("Przegląd obiektów - " + title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
    }

    private void initializeUI() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(createLeftPanel(), BorderLayout.WEST);
        panel.add(createRightPanel(), BorderLayout.CENTER);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void loadProcessors() {
        try {
            try {
//                List<Class<Processor>> processorClasses = customClassLoader.loadProcessorsFromFile();
                List<Class<Processor>> processorClasses = classManager.getProcessors();
                processorInstances.clear();
                statusListener.clearStatusMap();

                for (Class<Processor> processorClass : processorClasses) {
                    Processor instance = processorClass.getDeclaredConstructor().newInstance();
                    processorInstances.add(instance);
                }
                refreshListModel();
            }catch(NoClassDefFoundError e){
                openMainFrame("Error: couldn't find package  '" + packageName + "' in directory.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Failed to load processors: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel createLeftPanel() {
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createTitledBorder("Lista obiektów"));
        leftPanel.setPreferredSize(new Dimension(300, 500));

        DefaultListModel<Processor> listModel = new DefaultListModel<>();
        objectsList = new JList<>(listModel);
        objectsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        objectsList.addListSelectionListener(this::handleListSelection);

        JScrollPane listScrollPane = new JScrollPane(objectsList);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton reloadButton = new JButton("Reload");
        reloadButton.addActionListener(e -> reloadProcessors());

        JButton unloadButton = new JButton("Unload");
        unloadButton.addActionListener(e -> unloadProcessors());

        buttonPanel.add(reloadButton);
        buttonPanel.add(unloadButton);

        leftPanel.add(buttonPanel, BorderLayout.NORTH);
        leftPanel.add(listScrollPane, BorderLayout.CENTER);

        return leftPanel;
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(BorderFactory.createTitledBorder("Szczegóły obiektu"));

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel taskLabel = new JLabel("Enter task:");
        taskField = new JTextField();

        newTaskButton = new JButton("New task");
        newTaskButton.addActionListener(e ->
                startNewTask()
        );
        idLabel = createDetailLabel("ID: ");
        infoLabel = createDetailLabel("Informacje: ");
        statusLabel = createDetailLabel("Status: ");
        resultLabel = createDetailLabel("Wynik: ");

        detailsPanel.add(newTaskButton);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(taskLabel);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(taskField);
        detailsPanel.add(Box.createVerticalStrut(10));
        detailsPanel.add(idLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(infoLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(statusLabel);
        detailsPanel.add(Box.createVerticalStrut(5));
        detailsPanel.add(resultLabel);

        rightPanel.add(detailsPanel, BorderLayout.NORTH);
        return rightPanel;
    }

    private JLabel createDetailLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Times New Roman", Font.BOLD, 14));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private void handleListSelection(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            updateObjectDetails();
        }
    }

    private void updateObjectDetails() {
        Processor selectedProcessor = objectsList.getSelectedValue();
        if (selectedProcessor != null) {
            if(!statusMap.containsKey(ProcessManager.getInstance().getId(selectedProcessor)))clearObjectDetail(selectedProcessor);
            else{
                getCurrentStatus(selectedProcessor);
            }
        }
    }

    private void getCurrentStatus(Processor processor){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            String result = null;
            while (true) {
                try {
                    int id = ProcessManager.getInstance().getId(processor);
                    idLabel.setText("ID: " + id);
                    infoLabel.setText("Informacje: " + processor.getInfo());

                    Status currentStatus = statusListener.getStatusMap().get(processor);
                    if (currentStatus != null) {
                        statusLabel.setText("Status: " + currentStatus.getProgress() + "%");
                    } else {
                        statusLabel.setText("Status: Nieznany");
                    }

                    resultLabel.setText("Wynik: ...");
                    Thread.sleep(800);
                    result = processor.getResult();
                } catch (InterruptedException | IllegalArgumentException e) {
                    e.printStackTrace();
                }
                if (result != null) {
                    idLabel.setText("ID: " + ProcessManager.getInstance().getId(processor));
                    infoLabel.setText("Informacje: " + processor.getInfo());
                    statusLabel.setText("Status: Proces zakończony");
                    resultLabel.setText("Wynik: " + result);
                    statusMap.put(ProcessManager.getInstance().getId(processor), "Finished.");
                    executor.shutdown();
                    break;
                }
            }
        });
    }

    private void clearObjectDetail(Processor selectedProcessor){
        idLabel.setText("ID: " + ProcessManager.getInstance().getId(selectedProcessor));
        infoLabel.setText("Informacje: " + selectedProcessor.getInfo());
        statusLabel.setText("Status: Proces nie rozpoczęty.");
        resultLabel.setText("Wynik: Brak");
    }

    private void startNewTask() {
        if(taskField.getText()!=null) {
            Processor processor = objectsList.getSelectedValue();
            processor.submitTask(taskField.getText(), statusListener);
            statusMap.put(ProcessManager.getInstance().getId(processor), "RUNNING");
            getCurrentStatus(processor);
        }
        else{
            System.out.println("Enter task");
        }
    }

    private void refreshListModel() {
        DefaultListModel<Processor> model = (DefaultListModel<Processor>) objectsList.getModel();
        model.clear();
        for (Processor processor : processorInstances) {
            model.addElement(processor);
        }
    }

    private void reloadProcessors() {
//        this.customClassLoader = new CustomClassLoader(
//                packageName,
//                selectedDirectory.toPath()
//        );
        this.classManager = new ClassManager(
                packageName,
                selectedDirectory.toPath()
        );
        statusListener.clearStatusMap();
        ProcessManager.getInstance().clearRunningProcessors();
        loadProcessors();
        statusMap.clear();
    }

    private void openMainFrame(String text){
        frame.dispose();
        new DirectorySelectionView(text);
    }
    private void unloadProcessors() {
        statusListener.clearStatusMap();
        ProcessManager.getInstance().clearRunningProcessors();
        loadProcessors();
        statusMap.clear();
        classManager = null;
        System.gc();
        openMainFrame("You've unloaded classes.");
    }
}