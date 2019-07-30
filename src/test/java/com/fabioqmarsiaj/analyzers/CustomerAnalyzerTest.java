package com.fabioqmarsiaj.analyzers;

import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class CustomerAnalyzerTest {

    @Test
    public void shouldAnalyzeCustomerQuantity(){

        Set<String> dataList = new TreeSet<>();
        dataList.add("002ç2345675434544345çJose da SilvaçRural");
        dataList.add("002ç2345675433444345çEduardoPereiraçRural");

        CustomerAnalyzer customerAnalyzer = CustomerAnalyzer.getSingleton();
        customerAnalyzer.analyze(dataList);

        assertEquals("2", customerAnalyzer.getCustomerQuantity());
    }
}
