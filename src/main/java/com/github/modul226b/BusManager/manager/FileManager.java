package com.github.modul226b.BusManager.manager;

import com.github.modul226b.BusManager.datahandeling.JsonDataHolder;
import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileManager {
    private final String fileName;

    public FileManager(String fileName) {
        this.fileName = fileName;
    }

    public JsonDataHolder load() throws IOException {
        Path path = Paths.get(fileName);
        File f = new File(path.toAbsolutePath().toString());
        if (!f.exists()) {
            System.out.println("file '" + path.toAbsolutePath().toString() + "' not found. Trying to create file...");
            if (!f.createNewFile()) {
                System.out.println("file '" + path.toAbsolutePath().toString() + "' could not be created.");
            } else {
                System.out.println("file successfully created");
            }
            return new JsonDataHolder();
        } else {
            JsonDataHolder jsonDataHolder = new Gson().fromJson(String.join(" ", Files.readAllLines(path.toAbsolutePath())), JsonDataHolder.class);

            if (jsonDataHolder == null) {
                System.out.println("date could not be loaded from json file...");
                jsonDataHolder = new JsonDataHolder();
            }

            return jsonDataHolder;
        }
    }
}
