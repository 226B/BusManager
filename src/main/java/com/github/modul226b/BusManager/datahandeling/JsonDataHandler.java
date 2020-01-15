package com.github.modul226b.BusManager.datahandeling;

import com.github.modul226b.BusManager.manager.FileManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonDataHandler extends BaseJsonDataHandler {
    @Getter
    private FileManager fileManager;

    public JsonDataHandler(String jsonFileName, boolean load) {
        super();
        this.fileManager = new FileManager(jsonFileName);
        if (load) {
            try {
                this.setDataHolder(fileManager.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String string = gson.toJson(this.getDataHolder());
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(jsonFileName));
                writer.append(string);
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }));
    }

    public JsonDataHandler(String jsonFileName) {
        this(jsonFileName, true);
    }
}
