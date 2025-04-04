package org.example.recall.provider;

import org.example.api.AnalysisException;
import org.example.api.AnalysisService;
import org.example.api.DataSet;

public class Recall implements AnalysisService {
    private DataSet results;
    private boolean isProcessing = false;

    @Override
    public void setOptions(String[] options) throws AnalysisException {
    }

    @Override
    public String getName() {
        return "Recall";
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
                String[] header = new String[ds.getHeader().length+1];
                header[0] = "Recall";
                for(int i = 0; i < ds.getHeader().length; i++){
                    header[i+1] = ds.getHeader()[i];
                }

                int[] accurateClassification = new int[data.length];
                int[] sum = new int[data.length];
                for(int i = 0; i < data.length; i++){
                    for(int j = 0; j < data.length; j++){
                        if(i==j)accurateClassification[i] = Integer.parseInt(data[i][j].trim());
                        sum[i] += Integer.parseInt(data[i][j].trim());
                    }
                }
                String[][] resultData = new String[1][data.length+1];
                resultData[0][0]="Recall";
                for(int i = 0; i < data.length; i++){
                    resultData[0][i+1]=String.valueOf((double)accurateClassification[i]/(sum[i]));
                }
                results.setData(resultData);
                results.setHeader(header);
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
