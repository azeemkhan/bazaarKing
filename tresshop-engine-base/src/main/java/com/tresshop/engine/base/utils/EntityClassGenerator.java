package com.tresshop.engine.base.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.*;

public class EntityClassGenerator {

    public static List<String> listOfString = new ArrayList<>();
    public static Map<String, String> fieldAndDataTypes = new HashMap<>();
    public static void generateClassFile(String filePath, String packagePath){
        
        String fileNameSql = Paths.get(filePath).getFileName().toString();
        String fileName = fileNameSql.replaceFirst("[.][^.]+$", "");
        String [] splitFileName = fileName.split("_");
        StringBuilder className = new StringBuilder();

        for (int i = 0; i< splitFileName.length; i++){
            className.append(splitFileName[i].substring(0,1).toUpperCase() + splitFileName[i].substring(1));
        }

        readLines(filePath);

        StringBuilder generatedClass = new StringBuilder();
        generatedClass.append("/**----------------------------------------\n");
        generatedClass.append("* GENERATED CODE\n");
        generatedClass.append("* TIME:" + LocalDate.now()+"\n");
        generatedClass.append("* -----------------------------------------\n");
        generatedClass.append("*/\n");
        generatedClass.append("package com.tresshop.engine.storage.entity;");
        generatedClass.append("\n\n");

        String primaryKey = listOfString.get(listOfString.size()-1);
        boolean compositePrimary = false;
        if (primaryKey != null && primaryKey.contains("PRIMARY")) {
            listOfString.remove(listOfString.size() - 1);
            if (primaryKey.split(",").length > 1) compositePrimary = true;
        }
        else
            primaryKey = "";
        generatedClass.append(importsString(compositePrimary));
        generatedClass.append(anotation(className.toString(), fileName, compositePrimary));
        for (int j = 0; j< listOfString.size(); j++) {
            generatedClass.append("\t" + toEntityAnotation(listOfString.get(j), primaryKey));
            generatedClass.append("\t" + toJavaField(listOfString.get(j)));
            generatedClass.append("\n\n");
        }
        if (compositePrimary) generatedClass.append(toCompositeClass(primaryKey));
        generatedClass.append("\n}");
        File javaFile = new File(packagePath + "/" + className + "Entity.java");
        if (javaFile.exists()) javaFile.delete();
        writeToPackage(generatedClass.toString(), packagePath+"/"+className+"Entity.java");
        //System.out.println(generatedClass.toString());
    }
    public static void readLines(String filePath){

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            int i = 0;
            String line = reader.readLine();
            while (line != null) {
                if ("".equals(line.trim())) continue;
                if(i >= 2) {
                    listOfString.add(line.trim());
                }
                line = reader.readLine();
                if(line.trim().equals(");")) break;
                if (line.contains("PRIMARY")){
                    listOfString.add(line.trim());
                    break;
                }
                i++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static String anotation(String className, String tableName, boolean compositeKey){
        StringBuilder builder = new StringBuilder();
        builder.append("@Entity\n" +
                "@Table(name = " +"\""+ tableName + "\" "+")\n");
        if (compositeKey) builder.append("@IdClass(" + className + "Entity.CompositeKeys.class)\n");
        builder.append("@Getter\n" +
                "@Setter\n" +
                "@NoArgsConstructor\n" +
                "public class " + className+"Entity" + " implements Serializable {\n");
        return builder.toString();
    }
    public static String importsString(boolean composite){
        StringBuilder builder = new StringBuilder();
        builder.append("import lombok.Getter;\n" +
                "import lombok.NoArgsConstructor;\n" +
                "import lombok.Setter;\n" +
                "\n" +
                "import javax.persistence.Entity;\n" +
                "import javax.persistence.Table;\n" +
                "import javax.persistence.Id;\n" +
                "import javax.persistence.Column;\n");
        if (composite) builder.append("import javax.persistence.IdClass;\n");
        builder.append("import java.io.Serializable;\n" +
                "import java.sql.Timestamp;\n");
        if (composite) builder.append("import java.util.Objects;\n\n");
        return builder.toString();
    }
    public static String toJavaField(String line){

        StringBuilder javaField = new StringBuilder();
        String field ="";
        String dataTypes = null;
        javaField.append("private ");
        if (!"".equals(line.trim())){
            String [] splits = line.split(" ");
            field = splits[0].trim();
            if (splits[1].contains("VARCHAR") || splits[1].contains("BLOB")) {
                dataTypes = "String";
            }
            else if (splits[1].contains("INT")) {
                dataTypes = "int";
            }
            else if (splits[1].contains("BIGINT") || splits[1].contains("MEDIUMINT")) {
                dataTypes = "long";
            }
            else if (splits[1].contains("DECIMAL") || splits[1].contains("DOUBLE")) {
                dataTypes = "double";
            }
            else if (splits[1].contains("BOOLEAN")) {
                dataTypes = "boolean";
            }
            else {
                dataTypes = "Timestamp";
            }
        }
        javaField.append(dataTypes + " ");
        javaField.append(toCamelCase(field) + ";");
        fieldAndDataTypes.put(field, dataTypes);
       return javaField.toString();
    }

    public static String toEntityAnotation(String line, String primary){
        StringBuilder builder = new StringBuilder();
        if (!"".equals(line.trim())){
            String [] split = line.split(" ");
            String column = split[0].trim();
            if (primary.contains(column)) {
                builder.append("@Id\n");
                builder.append("\t@Column(name = ");
                builder.append("\"" + column + "\"" + ", nullable = false, unique = true)\n" );
            }
            else if (line.contains("NOT NULL")){
                builder.append("@Column(name = ");
                builder.append("\"" + column + "\"" + ", nullable = false)\n" );
            }
            else {
                builder.append("@Column(name = ");
                builder.append("\"" + column + "\")\n");
            }

        }
        return builder.toString();
    }

    public static String toCompositeClass(String primaryKey){
        String primaryKeys = primaryKey.substring(primaryKey.indexOf('(')+1);
        String [] allPrimaryKey = primaryKeys.substring(0, primaryKeys.indexOf(')')-1).split(",");

        StringBuilder compositKeyClassMethods = new StringBuilder();
        compositKeyClassMethods.append("\t//In Case of composite keys we have to create a IdClass\n" +
                "    static class CompositeKeys implements Serializable {\n");

        StringBuilder fields = new StringBuilder();
        StringBuilder ids = new StringBuilder();
        StringBuilder keys = new StringBuilder();
        for (int i = 0; i< allPrimaryKey.length; i++){
            String s = allPrimaryKey[i].trim();
            fields.append("\t\tprivate " + fieldAndDataTypes.get(s) + " " + toCamelCase(s) + ";\n");
            ids.append("Objects.equals(" + toCamelCase(s)+", ids."+toCamelCase(s)+") && ");
            keys.append(toCamelCase(s) + ", ");
        }
        compositKeyClassMethods.append(fields);
        compositKeyClassMethods.append("\n");
        compositKeyClassMethods.append("        @Override\n" +
                "        public boolean equals(Object o) {\n" +
                "            if (this == o) return true;\n" +
                "            if (o == null || getClass() != o.getClass()) return false;\n" +
                "            CompositeKeys ids = (CompositeKeys) o;\n");
        String equalsMethodReturn = ids.toString();
        equalsMethodReturn = "\t\t\treturn "+ equalsMethodReturn.substring(0, equalsMethodReturn.lastIndexOf("&&") -1) + ";\n";
        compositKeyClassMethods.append(equalsMethodReturn);
        compositKeyClassMethods.append("\t\t}\n");
        String hashCodeMethodReturn = keys.toString();
        String hashMethod = "\t\t@Override\n" +
                "\t\tpublic int hashCode() { return Objects.hash(" + hashCodeMethodReturn.substring(0, hashCodeMethodReturn.lastIndexOf(",")) + "); }";
        compositKeyClassMethods.append(hashMethod);
        compositKeyClassMethods.append("\n");
        compositKeyClassMethods.append("}\n");
        return compositKeyClassMethods.toString();
    }

    public static String toCamelCase(String string){
        String [] splits = string.split("_");
        StringBuilder camelCase = new StringBuilder();
        camelCase.append(splits[0]);
        for (int i = 1; i< splits.length; i++){
            camelCase.append(splits[i].substring(0,1).toUpperCase() + splits[i].substring(1));
        }
       return camelCase.toString();
    }
    public static void writeToPackage(String data, String packagePath){
        try {
            Files.write(Paths.get(packagePath),data.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String sqlDirPath = System.getProperty("user.dir") + "/db/mysql/ddls";
        String entityPath = System.getProperty("user.dir") + "/tresshop-engine-storage/src/main/java/com/tresshop/engine/storage/entity";
        File folder = new File(sqlDirPath);
        File [] listOfFiles = folder.listFiles();
        for (int i =0; i < listOfFiles.length; i++){
            generateClassFile(listOfFiles[i].toString(), entityPath);
            listOfString.clear();
            fieldAndDataTypes.clear();
        }
    }
}
