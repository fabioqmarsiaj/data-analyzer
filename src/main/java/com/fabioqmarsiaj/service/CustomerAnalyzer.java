package com.fabioqmarsiaj.service;

import com.fabioqmarsiaj.Analyzer;

import java.util.Set;

public class CustomerAnalyzer implements Analyzer {

    private String customerQuantity;

    public void setCustomerQuantity(String customerQuantity) { this.customerQuantity = customerQuantity; }
    public String getCustomerQuantity() { return customerQuantity; }

    private CustomerAnalyzer() {
    }

    private static class StaticHolder{ static final CustomerAnalyzer INSTANCE = new CustomerAnalyzer();}

    static CustomerAnalyzer getSingleton(){ return StaticHolder.INSTANCE; }

    @Override
    public void analyze(Set<String> data) {
        customerQuantity(data);
    }

    private void customerQuantity(Set<String> data) {
        setCustomerQuantity(String.valueOf(data.stream().filter(customer -> customer.contains("002ç")).count()));
    }
}
