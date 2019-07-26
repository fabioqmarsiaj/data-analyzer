package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.domain.Salesman;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataInService {

    private DataInService() {
    }

    private static class StaticHolder{ static final DataInService INSTANCE = new DataInService();}

    static DataInService getSingleton(){ return StaticHolder.INSTANCE; }

    private Set<String> dataList = new TreeSet<>();
    private long salesmanQuantity;
    private long customerQuantity;
    private List<Sale> sales = new ArrayList<>();
    private List<Salesman> salesmens = new ArrayList<>();
    private Map<Salesman, Double> salesmansSalesAmount = new HashMap<>();

    public void setSalesmanQuantity(long salesmanQuantity) { this.salesmanQuantity = salesmanQuantity; }
    public void setCustomerQuantity(long customerQuantity) { this.customerQuantity = customerQuantity; }
    public long getSalesmanQuantity() { return salesmanQuantity; }
    public long getCustomerQuantity() { return customerQuantity; }
    public Map<Salesman, Double> getSalesmansSalesAmount() { return salesmansSalesAmount; }
    public Set<String> getDataList() { return dataList; }

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

    public void addToSalesman(Set<String> data) {

        salesmens.clear();

        data.forEach(line -> {
            String delimiter = delimiterAnalyzer(line);
            if (line.contains("001" + delimiter)) {
                getSalesmanFromFile(line);
            }
        });
    }

    private void getSalesmanFromFile(String line) {
        String delimiter = delimiterAnalyzer(line);

        String[] split = line.split(delimiter);

        Salesman newSalesman = new Salesman(split[1], split[2], Double.parseDouble(split[3]));

        salesmens.add(newSalesman);
    }

    public void addToSales(Set<String> data) {
        salesmens.clear();
        sales.clear();

        for (String line : data) {
            String delimiter = delimiterAnalyzer(line);
            if (line.contains("003" + delimiter)) {
                getSaleFromFile(line);
            }
        }
    }

    private void getSaleFromFile(String line) {

        String delimiter = delimiterAnalyzer(line);

        String[] split = line.split(delimiter);
        String[] items = split[2].split(",");

        List<Item> itemsList = new ArrayList<>();
        for (String string : items) {

            String[] item = string.split("-");
            String itemId = item[0].replace("[", "");
            int itemIdToInt = Integer.parseInt(itemId);
            int quantityToInt = Integer.parseInt(item[1]);

            String price = item[2].replace("]", "");
            Double priceToDouble = Double.parseDouble(price);

            Item newItem = new Item(itemIdToInt, quantityToInt, priceToDouble);
            itemsList.add(newItem);
        }
        Sale newSale = new Sale(Integer.parseInt(split[1]), itemsList, split[3]);
        sales.add(newSale);
    }

    public void addToSalesmanSalesAmount(Set<String> data){
        salesmansSalesAmount.clear();

        addToSales(data);
        addToSalesman(data);

        for(Sale sale : sales){
            for(Salesman salesman : salesmens){
                if(salesman.getName().equals(sale.getSalesmanName())){
                    double amount = getAmountPerSalesman(salesman.getName());
                    for(Item item : sale.getItems()){
                        amount = amount + (item.getQuantity() * item.getPrice());
                        salesmansSalesAmount.put(salesman,amount);
                    }
                }
            }
        }
        for(Map.Entry<Salesman, Double> entry : salesmansSalesAmount.entrySet()){
            System.out.println(entry.getKey().getName() + " " + entry.getValue());
        }
    }

    public double getAmountPerSalesman(String salesmanName){
        double amount = 0.0;
        for(Map.Entry<Salesman, Double> entry : salesmansSalesAmount.entrySet()){
            if(entry.getKey().getName().equals(salesmanName)){
                amount += entry.getValue();
            }
        }
        return amount;
    }

    private String delimiterAnalyzer(String line){
        return line.substring(3, 4);
    }

    private void verifyLineAndAdd(String line) {

        if (line.contains(" ")) {
            dataList.add(line.replace(" ", ""));
        }else{
            dataList.add(line);
        }
    }
}
