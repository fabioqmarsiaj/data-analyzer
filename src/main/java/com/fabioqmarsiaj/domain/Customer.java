package com.fabioqmarsiaj.domain;

public class Customer {

    private String cnpj;
    private String name;
    private String bussinessArea;

    public Customer(String cnpj, String name, String bussinessArea) {
        this.cnpj = cnpj;
        this.name = name;
        this.bussinessArea = bussinessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public String getName() {
        return name;
    }

    public String getBussinessArea() {
        return bussinessArea;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cnpj='" + cnpj + '\'' +
                ", name='" + name + '\'' +
                ", bussinessArea='" + bussinessArea + '\'' +
                '}';
    }
}
