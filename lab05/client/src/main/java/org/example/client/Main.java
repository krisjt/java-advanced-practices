package org.example.client;

import org.example.api.AnalysisException;
import org.example.api.AnalysisService;
import org.example.api.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class Main {
    public static void main(String[] args) throws AnalysisException, InterruptedException {
//        List<AnalysisService> services = new ArrayList<>();
//        ServiceLoader<AnalysisService> loader = ServiceLoader.load(AnalysisService.class);
//        loader.forEach(services::add);
//        String[][] confusionMatrix = {
//                {"10", "2", "1"},
//                {"3", "8", "2"},
//                {"1", "2", "7"}
//        };
//
//        DataSet dataSet = new DataSet();
//        String[] strings = {"col1","col2","col3"};
//        dataSet.setHeader(strings);
//        dataSet.setData(confusionMatrix);
//        services.get(0).submit(dataSet);
//        Thread.sleep(4000);
//        services.get(0).retrieve(false);
//        System.out.println(loader);
//        System.out.println(services);

        CsvReader csvReader = new CsvReader("/Users/krystynanowak/Desktop/Studia/Semestr6/JavaTechnikiZaawansowane/272890_javatz_2025/lab05/file.csv");

//        String[][] file = csvReader.readFile();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

//                System.out.println(file[i][j] + " ");
            }
        }
    }
}