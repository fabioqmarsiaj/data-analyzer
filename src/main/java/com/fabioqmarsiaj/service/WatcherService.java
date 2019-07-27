package com.fabioqmarsiaj.service;

import java.io.IOException;
import java.nio.file.*;

public class WatcherService {

    public void fileWatcher() throws IOException, InterruptedException {

        String homepath = System.getProperty("user.home");

        DataOutService dataOutService = DataOutService.getSingleton();
        dataOutService.writeOutFile();

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
            }
            dataOutService.writeOutFile();
            key.reset();
        }
    }
}
