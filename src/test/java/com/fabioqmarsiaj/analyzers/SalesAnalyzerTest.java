package com.fabioqmarsiaj.analyzers;

import org.junit.Test;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;

public class SalesAnalyzerTest {

    @Test
    public void shouldAnalyzeMostExpansiveSaleId(){

        SalesAnalyzer salesAnalyzer = SalesAnalyzer.getSingleton();

        Set<String> dataList = new TreeSet<>();
        dataList.add("003ç08ç[1-34-10,2-33-1.50,3-40-0.10]çRenato");
        dataList.add("003ç10ç[1-10-100,2-30-2.50,3-40-3.10]çDiego");

        salesAnalyzer.addToSales(dataList);
        salesAnalyzer.analyze(dataList);

        assertEquals("10", salesAnalyzer.getExpansiveSaleId());
    }
}
