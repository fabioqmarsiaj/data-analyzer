package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.analyzers.SalesAnalyzer;
import com.fabioqmarsiaj.analyzers.SalesmanAnalyzer;
import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.domain.Salesman;
import org.apache.commons.lang3.StringUtils;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class DataInService {
    private Set<String> dataList = new TreeSet<>();
    private SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
    private SalesAnalyzer salesAnalyzer = SalesAnalyzer.getSingleton();
    private String delimiter;
    private String homepath;
    
    private DataInService() {
    }

    private static class StaticHolder{ static final DataInService INSTANCE = new DataInService();}

    public Set<String> getDataList() { return dataList; }
    public String getDelimiter() { return delimiter; }
    public String getHomepath() { return homepath; }

    public static DataInService getSingleton(){ return StaticHolder.INSTANCE; }

    public void readFile() throws IOException {
        dataList.clear();

        homepath = System.getProperty("user.home");
        File dir = new File(homepath + "/data/in/");

        createDirectoriesIfDoesntExist(dir);
        inFileCreator(dir.toString());


        for (File file : Objects.requireNonNull(dir.listFiles())) {
            try(BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getAbsolutePath()))){
                String line;
                while((line = bufferedReader.readLine()) != null){
                    verifyLineAndAddToDataList(line);
                }
            } catch (FileNotFoundException e) {
                throw new FileNotFoundException("File: " + file.getAbsolutePath() + " not found.");
            } catch (IOException e) {
                e.getMessage();
            }
        }
    }

    public void readSalesmanFromFile(String line) {
        String[] split = line.split(delimiter);

        Salesman newSalesman = new Salesman(split[1], split[2], Double.parseDouble(split[3]));

        salesmanAnalyzer.getSalesmens().add(newSalesman);
    }

    public void readSalesFromFile(String line) {
        delimiterAnalyzer(line);

        String allItemsOnSale = StringUtils.substringBetween(line, "[", "]");
        String[] items = allItemsOnSale.split(",");

        List<Item> itemsList = new ArrayList<>();

        for (String item : items) {
            String itemId = StringUtils.substringBefore(item, "-");
            String itemQuantity = StringUtils.substringBetween(item, "-", "-");
            String itemPrice = StringUtils.substringAfterLast(item, "-");

            Item newItem = new Item(itemId, Integer.parseInt(itemQuantity), Double.parseDouble(itemPrice));
            itemsList.add(newItem);
        }
        String[] saleId = StringUtils.substringsBetween(line, delimiter, delimiter);
        String salesmanName = StringUtils.substringAfterLast(line, delimiter);

        Sale newSale = new Sale(saleId[0], itemsList, salesmanName);
        salesAnalyzer.getSales().add(newSale);
    }

    public void delimiterAnalyzer(String line){
        delimiter =  line.substring(3, 4);
    }

    private void verifyLineAndAddToDataList(String line) {
        if (line.contains(" ")) {
            dataList.add(line.replace(" ", ""));
        }else{
            dataList.add(line);
        }
    }

    private void createDirectoriesIfDoesntExist(File dir) throws IOException {
        if(!dir.exists()){
            createDirectories(homepath);
        }
    }

    private void createDirectories(String homepath) throws IOException {
        Path pathToIn = Paths.get(homepath + "/data/in");
        Path pathToOut = Paths.get(homepath + "/data/out");
        Files.createDirectories(pathToIn);
        Files.createDirectories(pathToOut);
    }

    private void inFileCreator(String dir) throws IOException {
        File inFile = new File(dir + "/data.dat");
        inFile.createNewFile();
    }
}
