package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.DataRequirements;
import com.fabioqmarsiaj.analyzers.CustomerAnalyzer;
import com.fabioqmarsiaj.analyzers.SalesAnalyzer;
import com.fabioqmarsiaj.analyzers.SalesmanAnalyzer;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class DataOutService {

    private DataOutService() {
    }

    private static class StaticHolder{ static final DataOutService INSTANCE = new DataOutService();}

    static DataOutService getSingleton(){ return StaticHolder.INSTANCE; }

    public void writeOutFile() throws IOException {
        DataInService dataInService = DataInService.getSingleton();
        SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
        CustomerAnalyzer customerAnalyzer = CustomerAnalyzer.getSingleton();
        SalesAnalyzer salesAnalyzer = SalesAnalyzer.getSingleton();

        dataInService.readFile();
        salesmanAnalyzer.analyze(dataInService.getDataList());
        customerAnalyzer.analyze(dataInService.getDataList());
        salesAnalyzer.analyze(dataInService.getDataList());

        try(FileWriter fileWriter = new FileWriter(dataInService.getHomepath() + "/data/out/data.done.dat")){
            fileWriter.write(DataRequirements.CUSTOMERS.toString() + ": " + customerAnalyzer.getCustomerQuantity() + "\n" +
                    DataRequirements.SALESMEN.toString() + ": " + salesmanAnalyzer.getSalesmanQuantity() + "\n" +
                    DataRequirements.EXPANSIVE_SALE_ID + ": " + salesAnalyzer.getExpansiveSaleId() + "\n" +
                    DataRequirements.WORSE_SALESMAN.toString() + ": " + salesmanAnalyzer.getWorseSalesman());
        }
    }
}
