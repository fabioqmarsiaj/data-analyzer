package com.fabioqmarsiaj.domain;

public class Item {

    private String id;
    private int quantity;
    private Double price;

    public Item(String id, int quantity, Double price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
