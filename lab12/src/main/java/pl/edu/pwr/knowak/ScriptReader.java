package pl.edu.pwr.knowak;

import org.openjdk.nashorn.api.scripting.ScriptObjectMirror;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ScriptReader {

    private final ScriptEngineManager mgr = new ScriptEngineManager();
    private final ScriptEngine jsEngine = mgr.getEngineByName("nashorn");
    public int[][] runScript(int[][] javaMatrix, String scriptPath, int init, int g) {
        int n = javaMatrix.length;

        Object[][] jsMatrix = new Object[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                jsMatrix[i][j] = javaMatrix[i][j];
            }
        }

        try {
            String script = new String(Files.readAllBytes(Paths.get(scriptPath)));
            jsEngine.eval(script);

            Invocable inv = (Invocable) jsEngine;
            Object result = inv.invokeFunction("generation", (Object) jsMatrix, init, g);

            return convertJSToJavaMatrix(result, n, n);

        } catch (ScriptException ex) {
            throw new RuntimeException(ex);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }

    }

    private int[][] convertJSToJavaMatrix(Object resultMatrix, int rows, int cols) {
        int[][] matrix = new int[rows][cols];

        if (resultMatrix instanceof ScriptObjectMirror som) {
            int i = 0;
            for (Object rowObj : som.values()) {
                ScriptObjectMirror row = (ScriptObjectMirror) rowObj;
                int j = 0;
                for (Object val : row.values()) {
                    matrix[i][j] = ((Number) val).intValue();
                    j++;
                }
                i++;
            }
        }
        return matrix;
    }

}
