package com.fabioqmarsiaj.service;

import java.io.FileWriter;
import java.io.IOException;

public class DataOutService {

    public void writeOutFile(long salesmanQuantity, long customerQuantity, String worseSalesman) throws IOException {
        String homepath = System.getProperty("user.home");
        try(FileWriter fileWriter = new FileWriter(homepath + "/data/in/dataout.done.dat")){

            fileWriter.write("SALESMAN QUANTITY " + salesmanQuantity + "\n" +
                                    "CUSTOMER QUANTITY " + customerQuantity + "\n" +
                                    "WORSE SALESMAN " + worseSalesman);
        }
    }
}
