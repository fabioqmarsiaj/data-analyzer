package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.domain.Salesman;

import java.util.*;

public class AnalysisService {

    private DataInService dataInService = DataInService.getSingleton();

    public long salesmanQuantityAnalyzer(Set<String> data) {
        dataInService.setSalesmanQuantity(data.stream().filter(salesman -> salesman.contains("001ç")).count());
        return dataInService.getSalesmanQuantity();
    }

    public long customerQuantityAnalyzer(Set<String> data) {
        dataInService.setCustomerQuantity(data.stream().filter(customer -> customer.contains("002ç")).count());
        return dataInService.getCustomerQuantity();
    }

    public String worseSalesmanAnalyzer(Set<String> data){

        dataInService.addToSalesmanSalesAmount(data);

        double worseSalesAmount = Collections.min(dataInService.getSalesmansSalesAmount().values());

        for(Map.Entry<Salesman, Double> entry : dataInService.getSalesmansSalesAmount().entrySet()){
            if(entry.getValue().equals(worseSalesAmount)){
                return entry.getKey().getName() + " is the worse salesman";
            }
        }
        return "DEFAULT";
    }
}
