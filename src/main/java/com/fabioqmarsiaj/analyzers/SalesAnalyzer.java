package com.fabioqmarsiaj.analyzers;

import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.service.DataInService;

import java.util.*;

public class SalesAnalyzer implements Analyzer{

    private List<Sale> sales = new ArrayList<>();
    private Map<Sale, Double> totalItemsPricePerSale = new HashMap<>();
    private Double expansiveSalePrice;
    private String expansiveSaleId;

    public String getExpansiveSaleId() { return expansiveSaleId; }
    public void setExpansiveSaleId(String expansiveSaleId) { this.expansiveSaleId = expansiveSaleId; }

    public List<Sale> getSales() { return sales; }

    private SalesAnalyzer() {
    }

    @Override
    public void analyze(Set<String> data) {

        addToSales(data);

        mostExpansiveSaleId();

    }

    private void mostExpansiveSaleId() {

        itemsPriceAmountPerSale();

        for(Map.Entry<Sale, Double> entry : totalItemsPricePerSale.entrySet()){
            if(entry.getValue().equals(expansiveSalePrice)){
                setExpansiveSaleId(entry.getKey().getSaleId());
            }
        }
    }

    private void itemsPriceAmountPerSale(){
        totalItemsPricePerSale.clear();

        for(Sale sale : sales){
            double itemsPriceAmountOnSale = 0.0;
            for(Item item : sale.getItems()){
                itemsPriceAmountOnSale += (item.getPrice() * item.getQuantity());
            }
            totalItemsPricePerSale.put(sale, itemsPriceAmountOnSale);
        }

        expansiveSalePrice = Collections.max(totalItemsPricePerSale.values());
    }

    private static class StaticHolder{ static final SalesAnalyzer INSTANCE = new SalesAnalyzer();}

    public static SalesAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    public void addToSales(Set<String> data) {
        SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
        salesmanAnalyzer.getSalesmens().clear();
        sales.clear();

        DataInService dataInService = DataInService.getSingleton();

        for (String line : data) {
            String delimiter = delimiterAnalyzer(line);
            if (line.contains("003" + delimiter)) {
                dataInService.readSalesFromFile(line);
            }
        }
    }

    private String delimiterAnalyzer(String line){
        return line.substring(3, 4);
    }
}
