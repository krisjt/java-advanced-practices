package pl.edu.pwr.client;

import pl.edu.pwr.api.AnalysisException;
import pl.edu.pwr.api.AnalysisService;
import pl.edu.pwr.api.DataSet;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.*;

public class ClientFrame extends JFrame {

    private List<AnalysisService> services;
    private JList<AnalysisService> serviceJList;
    private DefaultListModel<AnalysisService> listModel;
    private JTextArea resultArea;
    private JButton processButton;
    private JButton retriveButton;
    private JButton retriveDeleteButton;
    private JButton chooseFileButton;
    private File selectedFile;
    private DataSet dataSet;

    public ClientFrame() {
        initializeServices();

        setTitle("Processor GUI");
        setSize(1000, 700);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel leftPanel = createLeftPanel();
        JPanel rightPanel = createRightPanel();

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void initializeServices() {
        services = new ArrayList<>();
        ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
        loader.forEach(services::add);
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setPreferredSize(new Dimension(300, getHeight()));

        listModel = new DefaultListModel<>();
        for (AnalysisService service : services) {
            listModel.addElement(service);
        }

        serviceJList = new JList<>(listModel);
        serviceJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        serviceJList.setCellRenderer(new ProcessorListRenderer());

        JScrollPane listScrollPane = new JScrollPane(serviceJList);
        panel.add(listScrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));

        chooseFileButton = new JButton("Choose File");
        chooseFileButton.addActionListener(e -> chooseFile());

        processButton = new JButton("Process");
        processButton.addActionListener(e -> {
            try {
                serviceSelected();
            } catch (AnalysisException | InterruptedException ex) {
                System.out.println("Exception: " + ex.getMessage());
                Thread.currentThread().interrupt();
            }
        });

        retriveDeleteButton = new JButton("Retrieve delete");
        retriveDeleteButton.addActionListener(e ->{
            try {
                retrieveSelected(true);
            } catch (AnalysisException ex) {
                resultArea.append("\nAnalysisError: An error occurred during analysis. \nMaybe the process haven't started?\n");
            }
        });

        retriveButton = new JButton("Retrieve");
        retriveButton.addActionListener(e ->{
            try {
                retrieveSelected(false);
            } catch (AnalysisException ex) {
                resultArea.append("\nAnalysisError: An error occurred during analysis. \nMaybe the process haven't started?\n");
            }
        });


        buttonPanel.add(chooseFileButton);
        buttonPanel.add(processButton);
        buttonPanel.add(retriveButton);
        buttonPanel.add(retriveDeleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(resultArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Etykieta statusu
        JLabel statusLabel = new JLabel("Select a processor and file, then click Process");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fileChooser.setDialogTitle("Select File to Process");

        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CSV Files", "csv"));

        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            CsvReader csvReader = new CsvReader(selectedFile.getName());
            csvReader.readFile();
            String[][]data = csvReader.getData();
            String[] header = csvReader.getHeader();
                    Iterator<String[]> iterator = Arrays.stream(data).iterator();
            resultArea.append("Selected file: " + "\n");
            while(iterator.hasNext()){
                resultArea.append(Arrays.toString(iterator.next()) + "\n");
            }
            dataSet = new DataSet();
            dataSet.setData(data);
            dataSet.setHeader(header);

            System.out.println(dataSet.getData()[0][0]);
        }
    }

    private void serviceSelected() throws AnalysisException, InterruptedException {
        resultArea.setText(null);
        AnalysisService selectedService = serviceJList.getSelectedValue();
        if (selectedService == null) {
            JOptionPane.showMessageDialog(this, "Please select a processor first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedFile == null) {
            JOptionPane.showMessageDialog(this, "Please select a file first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (dataSet == null) {
            JOptionPane.showMessageDialog(this, "Please select a file first, data set is null", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultArea.append("\nProcessing with " + selectedService.getName() + "\n");
        resultArea.append("Processing file: " + selectedFile.getName() + "\n");

        selectedService.submit(dataSet);

    }

    private void retrieveSelected(boolean delete) throws AnalysisException {
        AnalysisService selectedService = serviceJList.getSelectedValue();
        DataSet dataSet1 = selectedService.retrieve(delete);

        if(dataSet1 == null)resultArea.append("\nInformation is either still being processed or you haven't passed any data.");
        else{
            Iterator<String[]> iterator = Arrays.stream(dataSet1.getData()).iterator();
            Iterator<String[]> iterator1 = Arrays.stream(dataSet.getData()).iterator();
            resultArea.append("\n\nResult: " + "\n");
            resultArea.append("For array: \n");
            resultArea.append(Arrays.toString(dataSet.getHeader())+"\n");
            while(iterator1.hasNext()){
                resultArea.append(Arrays.toString(iterator1.next()) + "\n");
            }

            resultArea.append("\n\nResults is: \n");
            resultArea.append(Arrays.toString(dataSet1.getHeader())+"\n");
            while(iterator.hasNext()){
                resultArea.append(Arrays.toString(iterator.next()) + "\n");
            }
        }
    }

    private static class ProcessorListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof AnalysisService processor) {
                setText("<html><b>" + processor.getName() + "</b><br></html>");
            }
            return this;
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientFrame().setVisible(true));
    }
}