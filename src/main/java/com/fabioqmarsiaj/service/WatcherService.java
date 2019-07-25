package com.fabioqmarsiaj.service;

import java.io.IOException;
import java.nio.file.*;

public class WatcherService {


    public void fileWatcher() throws IOException, InterruptedException {

        ReaderService readerService = new ReaderService();
        readerService.readFile();
        System.out.println(readerService.getDataList());

        AnalysisService analysisService = new AnalysisService();
        analysisService.salesmanQuantityAnalyzer(readerService.getDataList());
        analysisService.customerQuantityAnalyzer(readerService.getDataList());
        analysisService.worseSalesmanAnalyzer(readerService.getDataList());

        System.out.println(analysisService.getSalesmanQuantity());
        System.out.println(analysisService.getCustomerQuantity());

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = Paths.get("/home/ilegra0282/data/in");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;

        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println(
                        "Event kind:" + event.kind()
                                + ". File affected: " + event.context() + ".");
                readerService.readFile();
                analysisService.salesmanQuantityAnalyzer(readerService.getDataList());
                analysisService.customerQuantityAnalyzer(readerService.getDataList());
                analysisService.worseSalesmanAnalyzer(readerService.getDataList());
            }
            key.reset();
            System.out.println(readerService.getDataList());
            System.out.println(analysisService.getSalesmanQuantity());
            System.out.println(analysisService.getCustomerQuantity());

        }
    }
}
