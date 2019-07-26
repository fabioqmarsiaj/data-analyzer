package com.fabioqmarsiaj.service;

import java.io.IOException;
import java.nio.file.*;

public class WatcherService {

    public void fileWatcher() throws IOException, InterruptedException {

        String homepath = System.getProperty("user.home");

        DataOutService dataOutService = new DataOutService();

        DataInService dataInService = DataInService.getSingleton();
        dataInService.readFile();

        System.out.println(dataInService.getDataList());

        AnalysisService analysisService = new AnalysisService();
        analysisService.salesmanQuantityAnalyzer(dataInService.getDataList());
        analysisService.customerQuantityAnalyzer(dataInService.getDataList());
        String worse = analysisService.worseSalesmanAnalyzer(dataInService.getDataList());

        dataOutService.writeOutFile(dataInService.getSalesmanQuantity(), dataInService.getCustomerQuantity(), worse);

        System.out.println(dataInService.getSalesmanQuantity());
        System.out.println(dataInService.getCustomerQuantity());

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        Path path = Paths.get(homepath + "/data/in");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;

        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
                dataInService.readFile();
                analysisService.salesmanQuantityAnalyzer(dataInService.getDataList());
                analysisService.customerQuantityAnalyzer(dataInService.getDataList());
                System.out.println(analysisService.worseSalesmanAnalyzer(dataInService.getDataList()));

            }
            dataOutService.writeOutFile(dataInService.getSalesmanQuantity(), dataInService.getCustomerQuantity(), worse);
            key.reset();
            System.out.println(dataInService.getDataList());
            System.out.println(dataInService.getSalesmanQuantity());
            System.out.println(dataInService.getCustomerQuantity());

        }
    }
}
