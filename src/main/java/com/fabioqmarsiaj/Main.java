package com.fabioqmarsiaj;

import com.fabioqmarsiaj.service.WatcherService;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {

        WatcherService watcherService = new WatcherService();

        watcherService.fileWatcher();

    }
}
