package model;

import exception.RoundOutOfBoundException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 14/11/2016.
 */
public class TestingSet {
    private ArrayList<Training> trainings = new ArrayList<>();
    private Training currentTest = new Training();


    public ArrayList<Training> getTrainings() {
        return trainings;
    }


    public void newLearningTest() {
        currentTest = new Training();
    }
    public void addFeature(Integer feature) {
        currentTest.addFeature(feature);
    }

    public void endLearningTest() {
        this.trainings.add(currentTest);
    }

}
