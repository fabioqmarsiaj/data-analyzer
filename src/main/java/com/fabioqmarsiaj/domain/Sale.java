package com.fabioqmarsiaj.domain;

import java.util.ArrayList;
import java.util.List;

public class Sale implements Comparable<Sale>{

    private int saleId;
    private List<Item> items;
    private String salesmanName;

    public Sale(int saleId, List<Item> items, String salesmanName) {
        this.saleId = saleId;
        this.items = items;
        this.salesmanName = salesmanName;
    }

    public int getSaleId() {
        return saleId;
    }

    public List<Item> getItems() {
        return items;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    @Override
    public String toString() {
        return "Sale{" +
                "saleId=" + saleId +
                ", items=" + items +
                ", salesmanName='" + salesmanName + '\'' +
                '}';
    }

    @Override
    public int compareTo(Sale sale) {
        return this.salesmanName.compareTo(sale.getSalesmanName());
    }
}
