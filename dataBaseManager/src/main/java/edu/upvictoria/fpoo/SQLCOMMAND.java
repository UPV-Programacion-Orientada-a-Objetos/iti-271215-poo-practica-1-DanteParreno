package edu.upvictoria.fpoo;

import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class SQLCOMMAND {
    public void SQL(String sql) throws IOException {
        Matcher matcher;
        String path="/home/dante-yahir/iti-271215-poo-practica-1-DanteParreno/dataBaseManager/src/main/Tablas";

        if ((matcher=Pattern.compile("CREATE TABLE\\s+(\\w+) \\((.+)\\);?",Pattern.CASE_INSENSITIVE).matcher(sql)).find()){
            App.createTable(matcher.group(1), matcher.group(2).split(",\\s*"));
        }

        if((matcher=Pattern.compile("INSERT INTO\\s+(\\w+) \\((.+)\\)\\s+VALUES\\s+\\((.+)\\);?",Pattern.CASE_INSENSITIVE).matcher(sql)).find()){
            String[] columnas = matcher.group(2).split(",\\s*");
            String[] valores = matcher.group(3).split(",\\s*");
            App.insertinto(matcher.group(1), valores);
        }
        if((matcher= Pattern.compile("DROP TABLE\\s+(\\w+);?",Pattern.CASE_INSENSITIVE).matcher(sql)).find()){
            App.dropTable(matcher.group(1));
        }

/*
        if((matcher= Pattern.compile("DELETE\\s+FROM\\s+(\\w+)\\s+WHERE\\s+(.+);?",Pattern.CASE_INSENSITIVE).matcher(sql)).find()){
            App.deletefromwhere(matcher.group(1), matcher.group(2));
        }
        */
        if ((matcher = Pattern.compile("USE\\s+(.+?);?", Pattern.CASE_INSENSITIVE).matcher(sql)).find()) {
            String direccion = matcher.group(1).trim();
            App.use(direccion);
        }
    }
}
