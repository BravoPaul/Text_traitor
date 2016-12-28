package model;

import exception.RoundOutOfBoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;

/**
 *
 */
public class TrainingSet implements Callable<Subset> {
    private ArrayList<Training> trainings = new ArrayList<>();
    private Training currentTest = new Training();
    private Integer numberLng;
    private static final Integer ratio = 10;
    private Integer iteratorSubSets = 0;
    protected static ArrayList<Subset> subsetsTab = new ArrayList<Subset>();

    public TrainingSet() {
        super();
        // TODO Auto-generated constructor stub
    }

    public TrainingSet(Integer iterator) {
        this.iteratorSubSets = iterator;
    }

    public ArrayList<Training> getTrainings() {
        return trainings;
    }

    public Integer getNumberLng() {
        return numberLng;
    }

    public void setNumberLng(Integer numberLng) {
        this.numberLng = numberLng;
    }

    public void newLearningTest(String lng) {
        currentTest = new Training();
        currentTest.setLanguage(lng);
    }

    public void addFeature(Integer feature) {
        currentTest.addFeature(feature);
    }

    public void endLearningTest() {
        this.trainings.add(currentTest);
    }

    public static ArrayList<Subset> getSubsetsTab() {
        return subsetsTab;
    }

    public static void setSubsetsTab(ArrayList<Subset> subsetsTab) {
        TrainingSet.subsetsTab = subsetsTab;
    }

    /**
     *
     * @param round
     *            iteration
     * @param ratio
     *            ex 10% test, 90% validation, ratio = 10
     * @return
     * @throws RoundOutOfBoundException
     */
    public Subset getSubset(Integer round, Integer ratio)
            throws RoundOutOfBoundException { // every round generate a
        // different validation
        Subset subset = new Subset();
        Integer size_part = trainings.size() / ratio; // get 10% of the
        // TrainingSet (trainins
        // array )
        if (round < 0 || round >= ratio)
            throw new RoundOutOfBoundException(
                    "Round out of bound, try again with bound between 0 and "
                            + (ratio - 1));
        // Set validation training
        subset.setValidation(trainings.subList(round * size_part, (round + 1)
                * size_part));

        // Set learning training
        List<Training> learning = (ArrayList<Training>) trainings.clone();
        learning.subList(round * size_part, (round + 1) * size_part).clear();

        subset.setLearning(learning);
        iteratorSubSets++;
        return subset;
    }

    @Override
    public Subset call() throws Exception {
        return this.getSubset(iteratorSubSets, ratio);

    }
}