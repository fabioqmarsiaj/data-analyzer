package com.fabioqmarsiaj.analyzers;

import com.fabioqmarsiaj.DataFileEmptyException;
import com.fabioqmarsiaj.domain.Salesman;
import com.fabioqmarsiaj.service.DataInService;
import java.util.*;

public class SalesmanAnalyzer implements Analyzer {

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

    private SalesmanAnalyzer() {
    }

    private static class StaticHolder{ static final SalesmanAnalyzer INSTANCE = new SalesmanAnalyzer();}

    public static SalesmanAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    @Override
    public void analyze(Set<String> data) {
        if(data.isEmpty()){
            throw new DataFileEmptyException();
        }else{
            salesmanQuantity(data);
            worseSalesman();
        }
    }

    private void salesmanQuantity(Set<String> data) {
        setSalesmanQuantity(String.valueOf(data.stream().filter(salesman -> salesman.contains("001ç")).count()));
    }

    private void worseSalesman() {
        addToSalesmanSalesAmount();

        double worseSalesAmount = Collections.min(salesmansSalesAmount.values());

        salesmansSalesAmount.forEach((s, d) -> {
            if(d.equals(worseSalesAmount)){
                setWorseSalesman(s.getName());
            }
        });
    }

    public void addToSalesmanSalesAmount(){
        salesmansSalesAmount.clear();

        salesAnalyzer.getSales().forEach(sale -> salesmens.forEach(salesman -> {
            if(salesman.getName().equals(sale.getSalesmanName())){
                double amount = getAmountPerSalesman(salesman.getName());
                sale.getItems().forEach(item -> salesmansSalesAmount.put(salesman,amount));
            }
        }));
    }

    public void addToSalesman(Set<String> data) {
        salesmens.clear();

        DataInService dataInService = DataInService.getSingleton();
        data.forEach(line -> {
            dataInService.delimiterAnalyzer(line);
            if (line.contains("001" + dataInService.getDelimiter())) {
                dataInService.readSalesmanFromFile(line);
            }
        });
    }

    public double getAmountPerSalesman(String salesmanName){
        final double[] amount = {0.0};

        salesmansSalesAmount.forEach((salesman, amountPerSalesman) -> {
            if(salesman.getName().equals(salesmanName)){
                amount[0] += amountPerSalesman;
            }
        });
        return amount[0];
    }
}
