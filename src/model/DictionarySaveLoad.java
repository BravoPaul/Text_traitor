package model;

import java.io.*;
import java.util.Map;

/**
 * Created by Thomas on 14/11/2016.
 */
public class DictionarySaveLoad {

    public static void save(String fileName, Dictionary dictionary) {
        BufferedWriter writer = null;
        try {
            // creation d'un fichier
            File logFile = new File(fileName);

            writer = new BufferedWriter(new FileWriter(logFile));

            for (Map.Entry<String, Integer> entry : dictionary.getDictionnary().entrySet()) {
                writer.write(entry.getKey() + " " + entry.getValue() + "\r\n");
            }

        } catch (Exception e) {
            System.out.println("problème d'export");
        } finally {
            try {
                // Close the writer
                writer.close();
                System.out.println("Dictionary saved: "+fileName);
            } catch (Exception e) {
            }
        }
    }




    public static Dictionary load(String fileName) {
        Dictionary dictionary = new Dictionary();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            String line = null;
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            while (line != null) {
                String[] parts = line.split(" ");
                dictionary.addDictionnary(parts[0],Integer.parseInt(parts[1]));
                try {
                    line = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dictionary;
    }
}
