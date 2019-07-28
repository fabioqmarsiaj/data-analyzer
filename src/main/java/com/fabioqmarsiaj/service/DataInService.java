package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.analyzers.SalesAnalyzer;
import com.fabioqmarsiaj.analyzers.SalesmanAnalyzer;
import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.domain.Salesman;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataInService {

    private Set<String> dataList = new TreeSet<>();
    private SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
    private SalesAnalyzer salesAnalyzer = SalesAnalyzer.getSingleton();

    public Set<String> getDataList() { return dataList; }

    private DataInService() {
    }

    private static class StaticHolder{ static final DataInService INSTANCE = new DataInService();}

    public static DataInService getSingleton(){ return StaticHolder.INSTANCE; }

    public void readFile() {
        dataList.clear();

        String homepath = System.getProperty("user.home");
        File dir = new File(homepath + "/data/in/");

        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try(BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getAbsolutePath()))){
                String line;
                while((line = bufferedReader.readLine()) != null){
                    verifyLineAndAdd(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void readSalesmanFromFile(String line) {
        String delimiter = delimiterAnalyzer(line);

        String[] split = line.split(delimiter);

        Salesman newSalesman = new Salesman(split[1], split[2], Double.parseDouble(split[3]));

        salesmanAnalyzer.getSalesmens().add(newSalesman);
    }

    public void readSalesFromFile(String line) {
        String delimiter = delimiterAnalyzer(line);

        String[] saleAttributes = line.split(delimiter);
        String[] item = saleAttributes[2].split(",");

        List<Item> itemsList = new ArrayList<>();
        for (String itemAttribute : item) {

            String[] itemAttributes = itemAttribute.split("-");
            String itemId = itemAttributes[0].replace("[", "");
            int itemIdToInt = Integer.parseInt(itemId);
            int quantityToInt = Integer.parseInt(itemAttributes[1]);

            String price = itemAttributes[2].replace("]", "");
            Double priceToDouble = Double.parseDouble(price);

            Item newItem = new Item(itemIdToInt, quantityToInt, priceToDouble);
            itemsList.add(newItem);
        }
        Sale newSale = new Sale(saleAttributes[1], itemsList, saleAttributes[3]);
        salesAnalyzer.getSales().add(newSale);
    }

    private void verifyLineAndAdd(String line) {

        if (line.contains(" ")) {
            dataList.add(line.replace(" ", ""));
        }else{
            dataList.add(line);
        }
    }

    private String delimiterAnalyzer(String line){
        return line.substring(3, 4);
    }
}
