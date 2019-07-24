package com.fabioqmarsiaj.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SalesmanTest {

    @Test
    public void shouldCreateASalesman(){

        Salesman salesman = new Salesman("02983423085", "Fabio", 2500.0);

        assertNotNull(salesman);

    }
}
