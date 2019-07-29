package com.fabioqmarsiaj.analyzers;

import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.service.DataInService;
import java.util.*;

public class SalesAnalyzer implements Analyzer{

    private Set<Sale> sales = new TreeSet<>();
    private Map<Sale, Double> totalItemsPricePerSale = new HashMap<>();
    private Double expansiveSalePrice;
    private String expansiveSaleId;

    public String getExpansiveSaleId() { return expansiveSaleId; }
    public void setExpansiveSaleId(String expansiveSaleId) { this.expansiveSaleId = expansiveSaleId; }
    public Set<Sale> getSales() { return sales; }

    private SalesAnalyzer() {
    }

    @Override
    public void analyze(Set<String> data) {
        mostExpansiveSaleId();
    }

    private void mostExpansiveSaleId() {
        itemsPriceAmountPerSale();

        totalItemsPricePerSale.forEach((sale, saleAmount) -> {
            if(saleAmount.equals(expansiveSalePrice)){
                setExpansiveSaleId(sale.getSaleId());
            }
        });
    }

    private void itemsPriceAmountPerSale(){
        totalItemsPricePerSale.clear();

        sales.forEach(sale -> {
            double itemsPriceAmountOnSale = sale.getItems().stream().mapToDouble(item ->
                    (item.getPrice() * item.getQuantity())).sum();
            totalItemsPricePerSale.put(sale, itemsPriceAmountOnSale);
        });
        getExpansiveSalePrice();
    }

    private void getExpansiveSalePrice() {
        expansiveSalePrice = Collections.max(totalItemsPricePerSale.values());
    }

    private static class StaticHolder{ static final SalesAnalyzer INSTANCE = new SalesAnalyzer();}

    public static SalesAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    public void addToSales(Set<String> data) {
        SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
        salesmanAnalyzer.getSalesmens().clear();
        sales.clear();

        DataInService dataInService = DataInService.getSingleton();
        data.forEach(line -> {
            dataInService.delimiterAnalyzer(line);
            if (line.contains("003" + dataInService.getDelimiter())) {
                dataInService.readSalesFromFile(line);
            }
        });
    }
}
