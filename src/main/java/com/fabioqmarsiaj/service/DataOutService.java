package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.DataRequirements;

import java.io.FileWriter;
import java.io.IOException;

public class DataOutService {

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
