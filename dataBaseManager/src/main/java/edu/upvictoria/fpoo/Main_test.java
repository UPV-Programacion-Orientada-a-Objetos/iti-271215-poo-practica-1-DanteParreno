package edu.upvictoria.fpoo;

import java.io.File;
import java.io.IOException;

public class Main_test {
    private FileManager fileManager;

    public Main_test(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    public boolean use(String direccion) {
        return fileManager.use(direccion);
    }

    public boolean createTable(String nombre, String[] columna) throws IOException {
        return fileManager.createTable(nombre, columna);
    }

    public boolean insertInto(String nombre, String[] valores) throws IOException {
        return fileManager.insertInto(nombre, valores);
    }

    public boolean dropTable(String nombre) throws IOException {
        return fileManager.dropTable(nombre);
    }
}