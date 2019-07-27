package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.Analyzer;
import com.fabioqmarsiaj.domain.Item;
import com.fabioqmarsiaj.domain.Sale;
import com.fabioqmarsiaj.domain.Salesman;

import java.util.*;

public class SalesmanAnalyzer implements Analyzer {

    private DataInService dataInService = DataInService.getSingleton();
    private SalesAnalyzer salesAnalyzer = SalesAnalyzer.getSingleton();
    private String salesmanQuantity;
    private String worseSalesman;
    private List<Salesman> salesmens = new ArrayList<>();
    private Map<Salesman, Double> salesmansSalesAmount = new HashMap<>();


    public void setSalesmanQuantity(String salesmanQuantity) { this.salesmanQuantity = salesmanQuantity; }
    public String getSalesmanQuantity() { return salesmanQuantity; }
    public void setWorseSalesman(String worseSalesman) { this.worseSalesman = worseSalesman; }
    public String getWorseSalesman() { return worseSalesman; }
    public List<Salesman> getSalesmens() { return salesmens; }
    public Map<Salesman, Double> getSalesmansSalesAmount() { return salesmansSalesAmount; }

    private SalesmanAnalyzer() {
    }

    private static class StaticHolder{ static final SalesmanAnalyzer INSTANCE = new SalesmanAnalyzer();}

    static SalesmanAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    @Override
    public void analyze(Set<String> data) {
        salesmanQuantity(data);

        worseSalesman(data);
    }

    private void salesmanQuantity(Set<String> data) {
        setSalesmanQuantity(String.valueOf(data.stream().filter(salesman -> salesman.contains("001ç")).count()));
    }

    private void worseSalesman(Set<String> data) {

        addToSalesmanSalesAmount(data);

        double worseSalesAmount = Collections.min(getSalesmansSalesAmount().values());

        for(Map.Entry<Salesman, Double> entry : getSalesmansSalesAmount().entrySet()){
            if(entry.getValue().equals(worseSalesAmount)){
                setWorseSalesman(entry.getKey().getName());
            }
        }
    }

    public void addToSalesmanSalesAmount(Set<String> data){
        salesmansSalesAmount.clear();

        salesAnalyzer.addToSales(data);
        addToSalesman(data);

        for(Sale sale : salesAnalyzer.getSales()){
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

    public void addToSalesman(Set<String> data) {
        salesmens.clear();

        data.forEach(line -> {
            String delimiter = delimiterAnalyzer(line);
            if (line.contains("001" + delimiter)) {
                dataInService.readSalesmanFromFile(line);
            }
        });
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
}
