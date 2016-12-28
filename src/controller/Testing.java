package controller;

import model.Model;
import model.TestingSet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Thomas on 14/11/2016.
 */
public class Testing{
    private Model model;
    private TestingSet testingSet;
    private ArrayList<String> languages;
    private ArrayList<String> languagesTest;

    public Testing(Model model, ArrayList<String> languages, TestingSet testingSet) {
        this.model = model;
        this.testingSet = testingSet;
        this.languages = languages;
        this.languagesTest = new ArrayList<>();
    }


    public void lauch() {
        Computation cVal = new Computation(model, languages, testingSet.getTrainings()){

            @Override
            protected boolean stopCondition(Integer iteration){
                if(iteration > 0) return true;
                return false;
            }

            @Override
            protected void processIteration(Integer iteration, Integer training, Integer realLang, Integer result) throws Exception{
                languagesTest.add(languages.get(result));
            }
        };
        cVal.compute();
    }

    public ArrayList<String> getLanguagesTest() {
        return languagesTest;
    }

    public void save(String fileName) {
        BufferedWriter writer = null;
        try {
            // creation d'un fichier
            File logFile = new File(fileName);

            writer = new BufferedWriter(new FileWriter(logFile));

            for (String s: languagesTest) {
                writer.write(s + "\r\n");
            }

        } catch (Exception e) {
            System.out.println("problème d'export");
        } finally {
            try {
                // Close the writer
                writer.close();
                System.out.println("Results saved: "+fileName);
            } catch (Exception e) {
            }
        }
    }
}
