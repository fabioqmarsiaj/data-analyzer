package com.fabioqmarsiaj.service;

import java.io.IOException;
import java.nio.file.*;

public class WatcherService {

    public void fileWatcher() throws IOException, InterruptedException {

        DataOutService dataOutService = DataOutService.getSingleton();
        dataOutService.writeOutFile();

        WatchService watchService
                = FileSystems.getDefault().newWatchService();

        DataInService dataInService = DataInService.getSingleton();
        Path path = Paths.get(dataInService.getHomepath() + "/data/in");

        path.register(
                watchService,
                StandardWatchEventKinds.ENTRY_CREATE,
                StandardWatchEventKinds.ENTRY_DELETE,
                StandardWatchEventKinds.ENTRY_MODIFY);

        WatchKey key;

        while ((key = watchService.take()) != null) {
            for (WatchEvent<?> event : key.pollEvents()) {
                System.out.println("Event kind:" + event.kind() + ". File affected: " + event.context() + ".");
            }
            dataOutService.writeOutFile();
            key.reset();
        }
    }
}
