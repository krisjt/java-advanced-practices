package pl.edu.pwr.kappa.provider;


import pl.edu.pwr.api.AnalysisException;
import pl.edu.pwr.api.AnalysisService;
import pl.edu.pwr.api.DataSet;

public class KappaFactor implements AnalysisService {

    private DataSet results;
    private boolean isProcessing = false;

    // metoda ustawiająca opcje algorytmu (jeśli takowe są potrzebne)
    @Override
    public void setOptions(String[] options) throws AnalysisException {

    }

    // metoda zwracająca nazwę algorytmu
    @Override
    public String getName() {
        return "Kappa factor";
    }

    // metoda przekazująca dane do analizy, wyrzucająca wyjątek jeśli aktualnie trwa przetwarzanie danych
    @Override
    public void submit(DataSet ds) throws AnalysisException {
        if (isProcessing) {
            throw new AnalysisException("Calculations running.");
        }

        isProcessing = true;
        Thread analyzing = new Thread(()-> {
            try{
                results = new DataSet();
                results.setHeader(ds.getHeader());
                String[][] data = ds.getData();
                String[] header = {"Kappa Factor"};

                int operations = 0;
                int diagonal = 0;
                for (int i = 0; i < data.length; i++) {
                    for (int j = 0; j < data.length; j++) {
                        operations += Integer.parseInt(data[i][j].trim());
                        if (i == j) diagonal += Integer.parseInt(data[i][j].trim());
                    }
                }
                if (operations == 0) {
                    results.setData(null);
                } else {
                    double po = (double) diagonal / operations;
                    double pe = calculatePe(data, operations);

                    double kappa = (po - pe) / (1 - pe);

                    String[][] resultData = {{"kappa", String.valueOf(kappa)}};
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

    private double calculatePe(String[][] confusionMatrix, int operations){
        int k = confusionMatrix.length;

        int[] rowSums = new int[k];
        int[] colSums = new int[k];

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                rowSums[i] += Integer.parseInt(confusionMatrix[i][j].trim());
                colSums[j] += Integer.parseInt(confusionMatrix[i][j].trim());
            }
        }

        double pe = 0;
        for (int i = 0; i < k; i++) {
            pe += (double) rowSums[i] / operations * (double) colSums[i] / operations;
        }
        return pe;
    }

    // metoda pobierająca wynik analizy, zwracająca null jeśli trwa jeszcze przetwarzanie lub nie przekazano danych do analizy
    // wyrzucająca wyjątek - jeśli podczas przetwarzania doszło do jakichś błędów
    // clear = true - jeśli wyniki po pobraniu mają zniknąć z serwisu
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
