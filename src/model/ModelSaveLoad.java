package model;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Thomas on 14/11/2016.
 */
public class ModelSaveLoad {

    public static void save(String fileName, Model model){
        FileOutputStream fos = null;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName, false);
            out = new ObjectOutputStream(fos);
            out.writeObject(model);
            out.close();
            System.out.println("Model saved: "+fileName);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static Model load(String fileName){
        Model model = null;
        FileInputStream fos;
        try {
            fos = new FileInputStream(fileName);
            ObjectInputStream oos = new ObjectInputStream(fos);
            model = (Model) oos.readObject();
            fos.close();
        }
        catch (Exception e){
        }
        return model;
    }
}
