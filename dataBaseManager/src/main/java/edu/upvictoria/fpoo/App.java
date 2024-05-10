package edu.upvictoria.fpoo;

import java.io.*;
import java.io.FileWriter;
import java.io.File;
import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App 
{
    static String path="";

    public static void main( String[] args ) throws IOException {
        SQLCOMMAND sql = new SQLCOMMAND();
        boolean pathValido = false;

        while (!pathValido) {
            pathValido = validarYUsarPath();
            if (!pathValido) {
                System.out.println("Inténtalo de nuevo.");
            }
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String sentenciaSQL;

        System.out.println("escribe tus sentencias SQL o escribe SALIR  para terminar,RECUERDA ESCRIBIR EN MAYUSCULAS TUS INSTRUCCIONES SQL");
        while (true) {
            sentenciaSQL = br.readLine();
            sql.SQL(sentenciaSQL);

        }

    }

    public static boolean validarYUsarPath() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    String input;
    System.out.println("Introduce el comando USE y despues el path:");

    try {
        input = br.readLine();
        if (input.trim().toUpperCase().startsWith("USE ")) {
            String direccion = input.trim().substring(4); // va a fallar si hay 2 espaciosxd
            use(direccion);
            return new File(path).isDirectory();
        } else {
            System.out.println("Comando invalido.Usa el formato correcto:USE <path>");
            return false;
        }
    } catch (IOException e) {
        System.out.println("Se ha producido un error con la entrada de datos:" + e.getMessage());
        return false;
    }
}
    public static boolean insertinto(String nombre, String[] valores) throws IOException {
        File file = new File(path + "/" + nombre + ".csv");
        if (!file.exists()) {
            System.out.println("La tabla" + nombre + " no existe.");
            return false;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {//1era
            String primeraLinea = br.readLine();
            String[] nombreColumnas = primeraLinea.split(",");

            if (valores.length > nombreColumnas.length) {
                System.out.println("Hay mas valores que columnas,no se puede ejecutar la insercion.");
                return false;
            }

            String[] valoresCompletos = new String[nombreColumnas.length];
            Arrays.fill(valoresCompletos, "null");
            System.arraycopy(valores, 0, valoresCompletos, 0, valores.length);

            try (FileWriter fw = new FileWriter(file, true);
                 BufferedWriter bw = new BufferedWriter(fw);
                 PrintWriter out = new PrintWriter(bw)) {
                StringBuilder sb = new StringBuilder();
                for (String valor : valoresCompletos) {
                   // sb.append(valor);
                    sb.append(valor.trim()).append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                out.println(sb.toString());
                System.out.println("Insertado correctamente");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Error al leer la tabla: " + e.getMessage());
            return false;
        }
    }


    public static void use(String direccion) {
        File dir = new File(direccion);

        if (dir.exists() && dir.isDirectory() && dir.canWrite()) {
            path = direccion;
            System.out.println("Usando el path con permisos de escritura: " + path);
        } else {
            if (!dir.canWrite()) {
                System.out.println("No se puede escribir en esta direccion,intenta de nuevo");
            } else {
                System.out.println("No es un directorio o es incorrecto");
            }
        }
    }


    public static boolean createTable(String nombre, String[] columna) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(path + "/" + nombre + ".csv");
            StringBuilder sb = new StringBuilder();
            for (String c : columna) {
                sb.append(c.trim()).append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            fw.write(sb.toString() + "\n");
            System.out.println("Se creo correctamente");
            return true;
        } catch (Exception e) {
            System.out.println("Error creando la tabla:" + e.getMessage());
            return false;
        } finally {

            if (fw != null) {
                try {

                    fw.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }
    }




    public static boolean dropTable(String nombre) {
        String answer = "";
        try {
            System.out.println("¿Estás seguro que quieres borrar la tabla? si/no,(si pones algo diferente a NO/no,se entendera como si");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            answer = br.readLine();
            if (answer.toLowerCase().equals("no")) {
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        try {
            File file = new File(path + "/" + nombre + ".csv");
            if (file.delete()) {
                System.out.println("Tabla borrada correctamente" );
                return true;
            } else {
                System.out.println("No se pudo borrar la tabla") ;
                return false;

            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
       //
        }
    }

}
