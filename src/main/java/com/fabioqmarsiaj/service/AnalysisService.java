package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.domain.Salesman;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnalysisService {

    private long salesmanQuantity;
    private long customerQuantity;
    private String worseSalesman;
    private Set<String> salesmans = new TreeSet<>();
    private Set<Sale> sales = new TreeSet<>();
    private List<Item> itemsList = new ArrayList<>();


    public long getSalesmanQuantity() { return salesmanQuantity; }
    public long getCustomerQuantity() { return customerQuantity; }
    public String getWorseSalesman() { return worseSalesman; }
    public Set<String> getSalesmans() { return salesmans; }

    public long salesmanQuantityAnalyzer(Set<String> data) {
       salesmanQuantity = data.stream().filter(salesman -> salesman.contains("001ç")).count();
       return salesmanQuantity;
    }

    public long customerQuantityAnalyzer(Set<String> data){
        customerQuantity = data.stream().filter(customer -> customer.contains("002ç")).count();
        return customerQuantity;
    }

    public void addToSales(Set<String> data){

        sales.clear();

        for(String line : data){
            if(line.contains("003ç")){
                getItemFromFile(line);
            }
        }
    }

    private void getItemFromFile(String line) {

        String[] split = line.split("ç");
        String[] items = split[2].split(",");

        for(String string : items){
            String[] item = string.split("-");
            String itemId = item[0].replace("[", "");
            int itemIdToInt = Integer.parseInt(itemId);
            int quantityToInt = Integer.parseInt(item[1]);

            String price = item[2].replace("]", "");
            Double priceToDouble = Double.parseDouble(price);

            Item newItem = new Item(itemIdToInt, quantityToInt, priceToDouble);
            this.itemsList.add(newItem);
            Sale newSale = new Sale(Integer.parseInt(split[1]), itemsList, split[3]);

            sales.add(newSale);
        }
    }

    public void worseSalesmanAnalyzer(Set<String> data){

        addToSales(data);

        Map<String, List<Sale>> result =  sales.stream().collect(Collectors.groupingBy(Sale::getSalesmanName));

        for(Map.Entry<String, List<Sale>> entry : result.entrySet()){
            System.out.println(entry.getKey());
        }
    }
}
