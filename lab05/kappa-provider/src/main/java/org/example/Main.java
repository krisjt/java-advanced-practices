package org.example;

import org.example.api.AnalysisException;
import org.example.api.DataSet;
import org.example.kappa.provider.KappaFactor;

public class Main {
    public static void main(String[] args) throws AnalysisException {
        String[][] confusionMatrix = {
                {"10", "2", "1"},
                {"3", "8", "2"},
                {"1", "2", "7"}
        };

        KappaFactor kappaFactor = new KappaFactor();
        DataSet dataSet = new DataSet();
        String[] strings = {"col1","col2","col3"};
        dataSet.setHeader(strings);
        dataSet.setData(confusionMatrix);
        kappaFactor.submit(dataSet);
        if(kappaFactor.retrieve(false)== null) System.out.println("NULL");
//        System.out.println(kappaFactor.retrieve(true).getData()[0][0] + " " + kappaFactor.retrieve(true).getData()[0][1]);
    }
}