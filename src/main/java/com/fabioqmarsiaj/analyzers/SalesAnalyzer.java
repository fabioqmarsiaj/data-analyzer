package com.fabioqmarsiaj.analyzers;

import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.service.DataInService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SalesAnalyzer {

    private List<Sale> sales = new ArrayList<>();

    public List<Sale> getSales() { return sales; }

    private SalesAnalyzer() {
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
