package edu.upvictoria.fpoo;

import java.io.*;

public class FileManager {
    private String basePath;

    public FileManager() {
        // Set the path to the directory where the files are managed
        this.basePath = "C:\\Users\\mumus\\iti-271215-poo-practica-1-DanteParreno\\dataBaseManager\\src\\main\\Tablas\\";
    }

    public String getBasePath() {
        return basePath;
    }

    public boolean use(String fileName) {
        File file = new File(basePath + fileName);
        return file.exists() && file.canWrite();
    }
    public boolean createTable(String tableName, String[] columns) {
        File file = new File(basePath + tableName + ".csv");
        if (file.exists()) {
            return false; // Table already exists
        }
        try {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            for (String column : columns) {
                writer.write(column + ",");
            }
            writer.write("\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean insertInto(String tableName, String[] values) {
        File file = new File(basePath + tableName + ".csv");
        if (!file.exists()) {
            return false; // Table does not exist
        }
        try {
            FileWriter writer = new FileWriter(file, true);
            for (String value : values) {
                writer.write(value + ",");
            }
            writer.write("\n");
            writer.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean dropTable(String tableName) {
        File file = new File(basePath + tableName + ".csv");
        return file.delete();
    }
}