package com.fabioqmarsiaj.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class ReaderService {

    private Set<String> dataList = new TreeSet<>();

    public Set<String> getDataList() {
        return dataList;
    }

    public void readFile() {

        dataList.clear();

        File dir = new File("/home/ilegra0282/data/in/");

        for (File file : Objects.requireNonNull(dir.listFiles())) {

            try(BufferedReader bufferedReader = new BufferedReader(
                    new FileReader(file.getAbsolutePath()))){

                String line;
                while((line = bufferedReader.readLine()) != null){
                    verifyLineAndAdd(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void verifyLineAndAdd(String line) {

        if (line.contains(" ")) {
            dataList.add(line.replace(" ", ""));

        }else{
            dataList.add(line);
        }
    }
}
