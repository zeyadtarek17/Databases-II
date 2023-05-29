package main.java;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.Vector;

public class Table implements Serializable {
    Vector<page> pages;
    String strTableName;
    String strClusteringKeyColumn;
    Hashtable<String, String> htblColNameType;
    Hashtable<String, String> htblColNameMin;
    Hashtable<String, String> htblColNameMax;
    ArrayList<String> Indexes = new ArrayList<String>();

    public Table(String strTableName, String strClusteringKeyColumn, Hashtable<String, String> htblColNameType,
            Hashtable<String, String> htblColNameMin, Hashtable<String, String> htblColNameMax) throws IOException, DBAppException {
        super();
        this.pages = new Vector<page>();
        this.strTableName = strTableName;
        this.strClusteringKeyColumn = strClusteringKeyColumn;
        this.htblColNameType = htblColNameType;
        this.htblColNameMin = htblColNameMin;
        this.htblColNameMax = htblColNameMax;
        if(checkTableExists(strTableName)){
            return;
        }
        FileWriter fw = new FileWriter("src/main/resources/MetaData.csv", true);
        for (String key : Collections.list(htblColNameType.keys())) {
            StringBuilder sb = new StringBuilder();
            String metadata_string = this.strTableName;
            metadata_string += (',' + key);
            metadata_string += (',' + htblColNameType.get(key));
            if (key == strClusteringKeyColumn) {
                metadata_string += (',' + "true");
            } else {
                metadata_string += (',' + "false");
            }
            metadata_string += (',' + "null");
            metadata_string += (',' + "null");
            metadata_string += (',' + htblColNameMin.get(key));
            metadata_string += (',' + htblColNameMax.get(key));
            metadata_string += "\n";
            sb.append(metadata_string);
            fw.write(sb.toString());
        }
        fw.flush();
        fw.close();

    }

    private boolean checkTableExists(String strTableName) throws DBAppException {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/MetaData.csv"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts[0].equals(strTableName)) {
                    return true;
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {  
            throw new DBAppException();
        }
        catch (IOException e) {
            throw new DBAppException();
        }
        return false;
    }

    public void updateTable() {
        for (int i = 0; i < pages.size(); i++) {
            page p = pages.get(i);
            p.setNumOfElem(p.getRecords().size());
            if (p.getNumOfElem() == 0) {
                p = null;
            }
            p.setMin((Comparable) p.getRecords().get(0).getValues().get(strClusteringKeyColumn));
            p.setMax((Comparable) p.getRecords().get(p.getRecords().size() - 1).getValues().get(strClusteringKeyColumn));
        }
    }

    public Vector<page> getPages() {
        return pages;
    }

    public void addToPages(page page) {
        this.pages.add(page);
    }

    // get and set table name
    public String getTable_name() {
        return strTableName;
    }

    public void setTable_name(String table_name) {
        this.strTableName = table_name;
    }

    // print all pages in the table in a tostring method
    public String toString() {
        String s = "";
        for (int i = 0; i < pages.size(); i++) {
            s += pages.get(i).toString();
        }
        return s;
    }

    public ArrayList<String> getIndexes() {
        return Indexes;
    }

}
