package com.fabioqmarsiaj.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {

    @Test
    public void shouldCreateACustomer(){
        Customer customer = new Customer("68040310000173", "Henrique", "Informatics");

        assertNotNull(customer);
        assertEquals("68040310000173", customer.getCnpj());
        assertEquals("Henrique", customer.getName());
        assertEquals("Informatics", customer.getBussinessArea());
    }
}
