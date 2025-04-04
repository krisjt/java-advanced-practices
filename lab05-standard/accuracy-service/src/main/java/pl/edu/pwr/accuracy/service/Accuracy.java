package pl.edu.pwr.accuracy.service;

import pl.edu.pwr.api.AnalysisException;
import pl.edu.pwr.api.AnalysisService;
import pl.edu.pwr.api.DataSet;

public class Accuracy implements AnalysisService {

    private DataSet results;
    private boolean isProcessing = false;

    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }
    @Override
    public String getName() {
        return "Accuracy factor";
    }

    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if (isProcessing) {
            throw new AnalysisException("Calculations running.");
        }

        isProcessing = true;
        Thread analyzing = new Thread(()-> {
            try{
                results = new DataSet();
                String[][] data = ds.getData();
                String[] header = {"Accuracy"};
                int operations = 0;
                int diagonal = 0;
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data.length; j++) {
                        operations += Integer.parseInt(data[i][j].trim());
                        if(i == j)diagonal+=Integer.parseInt(data[i][j].trim());
                    }
                }
                if(operations==0){
                    results.setData(null);
                }
                else {
                    double po = (double) diagonal / operations;
                    String[][] resultData = {{"accuracy", String.valueOf(po)}};
                    results.setData(resultData);
                    results.setHeader(header);
                }
            Thread.sleep(3000);
        } catch (Exception e) {
            results = null;
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error during analysis", e);
        } finally {
            isProcessing = false;
        }
    });
        analyzing.start();
    }

    @Override
    public DataSet retrieve(boolean clear) throws AnalysisException {
        if (isProcessing) {
            return null;
        }
        if (results == null) {
            throw new AnalysisException("No results of calculations.");
        }

        DataSet output = results;
        if (clear) {
            results = null;
        }
        return output;
    }
}
