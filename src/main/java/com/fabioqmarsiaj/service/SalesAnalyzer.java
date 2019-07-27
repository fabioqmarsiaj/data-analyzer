package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.domain.Sale;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SalesAnalyzer {

    private DataInService dataInService = DataInService.getSingleton();
    private SalesmanAnalyzer salesmanAnalyzer = SalesmanAnalyzer.getSingleton();
    private List<Sale> sales = new ArrayList<>();

    public List<Sale> getSales() { return sales; }

    private SalesAnalyzer() {
    }

    private static class StaticHolder{ static final SalesAnalyzer INSTANCE = new SalesAnalyzer();}

    static SalesAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    public void addToSales(Set<String> data) {
        salesmanAnalyzer.getSalesmens().clear();
        sales.clear();

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
