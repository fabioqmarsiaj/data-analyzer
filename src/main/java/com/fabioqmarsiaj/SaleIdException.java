package com.fabioqmarsiaj;

public class SaleIdException extends RuntimeException {
    public SaleIdException(){
        super("Sale ID already registered.");
    }
}
