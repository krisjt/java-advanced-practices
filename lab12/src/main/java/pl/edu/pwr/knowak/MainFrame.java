package pl.edu.pwr.knowak;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.util.Random;

public class MainFrame extends JFrame {
    private Canvas canvas;
    private JPanel controlPanel;
    private JTextField nField, gField, delayField;
    private JButton startButton;
    private JButton stopButton;
    private JButton selectScriptButton;
    private File selectedScript;
    private ScriptReader scriptReader;
    private Thread simulationThread;
    private boolean running = false;


    public MainFrame() {
        setTitle("Automaton Visualizer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        scriptReader = new ScriptReader();

        createControlPanel();
        canvas = new Canvas();

        add(controlPanel, BorderLayout.WEST);
        add(canvas, BorderLayout.CENTER);

        setLocationRelativeTo(null);
    }

    private void createControlPanel() {
        controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel scriptPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        selectScriptButton = new JButton("Select Script");
        selectScriptButton.addActionListener(e -> selectScript());
        scriptPanel.add(selectScriptButton);
        controlPanel.add(scriptPanel);

        JPanel paramPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        paramPanel.setBorder(BorderFactory.createTitledBorder("Parameters"));

        nField = new JTextField("200");
        gField = new JTextField("50");
        delayField = new JTextField("50");

        paramPanel.add(new JLabel("n:"));
        paramPanel.add(nField);
        paramPanel.add(new JLabel("g:"));
        paramPanel.add(gField);
        paramPanel.add(new JLabel("Delay (ms):"));
        paramPanel.add(delayField);

        controlPanel.add(paramPanel);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        startButton = new JButton("Start");
        startButton.addActionListener(e -> startSimulation());

        stopButton = new JButton("Stop");
        stopButton.addActionListener(e -> stopSimulation());
        stopButton.setEnabled(false);

        buttonPanel.add(startButton);
        buttonPanel.add(stopButton);
        controlPanel.add(buttonPanel);
    }

    private void selectScript() {
        JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir") + "/src");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JavaScript Files", "js"));
        fileChooser.showOpenDialog(this);
        selectedScript = fileChooser.getSelectedFile();
    }

    private int[][] generateArray(int n, int init) {
        int[][] javaMatrix = new int[n][n];
        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                javaMatrix[i][j] = rand.nextInt(init);
            }
        }

        return javaMatrix;
    }

    private void startSimulation() {
        int n = 200;
        if (selectedScript == null) {
            JOptionPane.showMessageDialog(this, "Please select a script first", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (running) {
            return;
        }

        String scriptName = selectedScript.getName().toLowerCase();
        Algorithm algorithm;
        int init;

        if (scriptName.contains("belousov") || scriptName.contains("zhabotinsky") || scriptName.contains("bz")) {
            algorithm = Algorithm.BZ;
            init = 30;
        } else if (scriptName.contains("brian") || scriptName.contains("brain") || scriptName.contains("bb")) {
            algorithm = Algorithm.BRIAN;
            init = 3;
        } else if (scriptName.contains("life") || scriptName.contains("gol")) {
            algorithm = Algorithm.GOL;
            init = 2;
        } else {
            JOptionPane.showMessageDialog(this, "Could not determine algorithm from script name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            n = Integer.parseInt(nField.getText());
            int g = Integer.parseInt(gField.getText());
            int delay = Integer.parseInt(delayField.getText());

            int[][][] matrixHolder = new int[1][][];
            matrixHolder[0] = generateArray(n, init);
            canvas.setAlgorithm(algorithm);
            canvas.updateMatrix(matrixHolder[0]);

            running = true;
            startButton.setEnabled(false);
            stopButton.setEnabled(true);

            simulationThread = new Thread(() -> {
                try {
                    while (running) {
                        matrixHolder[0] = scriptReader.runScript(matrixHolder[0], selectedScript.getPath(), init, g);
                        SwingUtilities.invokeLater(() -> canvas.updateMatrix(matrixHolder[0]));
                        Thread.sleep(delay);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    SwingUtilities.invokeLater(() -> {
                        startButton.setEnabled(true);
                        stopButton.setEnabled(false);
                    });
                }
            });
            simulationThread.start();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid numeric input.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void stopSimulation() {
        running = false;
        if (simulationThread != null) {
            simulationThread.interrupt();
            simulationThread = null;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}
