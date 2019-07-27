package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.DataRequirements;
import com.fabioqmarsiaj.analyzers.CustomerAnalyzer;
import com.fabioqmarsiaj.analyzers.SalesmanAnalyzer;

import java.io.FileWriter;
import java.io.IOException;

public class DataOutService {

    private DataOutService() {
    }

    private static class StaticHolder{ static final DataOutService INSTANCE = new DataOutService();}

    static DataOutService getSingleton(){ return StaticHolder.INSTANCE; }

    public void writeOutFile() throws IOException {
        String homepath = System.getProperty("user.home");

        DataInService dataInService = DataInService.getSingleton();
        dataInService.readFile();

        SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
        salesmanAnalyzer.analyze(dataInService.getDataList());

        CustomerAnalyzer customerAnalyzer = CustomerAnalyzer.getSingleton();
        customerAnalyzer.analyze(dataInService.getDataList());

        try(FileWriter fileWriter = new FileWriter(homepath + "/data/out/data.done.dat")){

            fileWriter.write(DataRequirements.CUSTOMERS.toString() + ": " + customerAnalyzer.getCustomerQuantity() + "\n" +
                    DataRequirements.SALESMEN.toString() + ": " + salesmanAnalyzer.getSalesmanQuantity() + "\n" +
                    DataRequirements.WORSE_SALESMAN.toString() + ": " + salesmanAnalyzer.getWorseSalesman());
        }
    }
}
