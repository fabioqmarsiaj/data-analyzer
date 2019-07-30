package com.fabioqmarsiaj.domain;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SaleTest {

    @Test
    public void shouldCreateASale(){

        Salesman salesman = new Salesman("02983423085", "Fabio", 1500.0);

        Item item1 = new Item("1", 10, 20.0);
        Item item2 = new Item("2", 15, 25.0);
        Item item3 = new Item("3", 20, 30.0);
        List<Item> items = new ArrayList<>();
        items.add(item1);
        items.add(item2);
        items.add(item3);

        Sale sale = new Sale("1", items, salesman.getName());

        assertNotNull(sale);
        assertEquals("1", sale.getSaleId());
        assertEquals(3, sale.getItems().size());
        assertEquals("Fabio", sale.getSalesmanName());
    }
}
