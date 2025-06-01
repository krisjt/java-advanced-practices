package pl.edu.pwr.knowak;

import javax.swing.*;
import java.awt.*;

class Canvas extends JPanel {
    private int[][] matrix;
    private int cellSize = 5;
    private Algorithm algorithm = Algorithm.GOL;

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public void updateMatrix(int[][] newMatrix) {
        this.matrix = newMatrix;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (matrix == null) return;

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (algorithm == Algorithm.BZ) {
                    int color = matrix[i][j] * 8 + 100;
                    g.setColor(new Color(color % 255, color % 255, color % 255));
                } else if (algorithm == Algorithm.BRIAN){
                    if (matrix[i][j] == 0) {
                        g.setColor(Color.CYAN);
                    } else if (matrix[i][j] == 1) {
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLACK);
                    }
                } else {
                    if (matrix[i][j] == 0) {
                        g.setColor(Color.BLACK);
                    } else if (matrix[i][j] == 1) {
                        g.setColor(Color.WHITE);
                    }
                }

                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.BLACK);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        if (matrix == null || matrix.length == 0) {
            return new Dimension(0, 0);
        }
        return new Dimension(matrix[0].length * cellSize, matrix.length * cellSize);
    }
}
