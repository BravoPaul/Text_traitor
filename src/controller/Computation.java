package controller;

import model.Model;
import model.Training;
import utils.TrainingUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 24/10/2016.
 */
public class Computation {
    private Model model;
    protected List<Training> trainings;
    protected Integer numberErrors=0;
    private Integer numberErrors_prev;
    private ArrayList<String> languages;


    public Computation(Model model,  ArrayList<String> languages, List<Training> trainingSet) {
        this.model = model;
        this.trainings = trainingSet;
        this.languages = languages;
    }

    /**
     * Compute : get the real lang, predicted lang and process it
     */
    public void compute(){
        int i=0;
        Integer real_lang =-1;
        while(!stopCondition(i)){
            numberErrors_prev = numberErrors;
            for (int j = 0; j < trainings.size(); j++) {
                try {
                    // Get the index of the languages
                    // -- Real lang
                    if(trainings.get(j).getLanguage() != null) real_lang = languages.indexOf(trainings.get(j).getLanguage());

                    // -- Prediction lang
                    Integer result = Decoder.decode(trainings.get(j).getFeatures(), model.getMatrix());

                    // Process of the iteration
                    processIteration(i, j, real_lang, result);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            afterIteration(i);

            // Mix the training set
            trainings = TrainingUtils.mix(trainings);
            i++;
        }
    }


    /**
     * Condition to stop the loop on mixing the subset
     * @param iteration number of the iteration
     * @return
     */
    protected boolean stopCondition(Integer iteration){
        if(iteration > 5 && (float)numberErrors_prev-(float)numberErrors < 0.01f) return true;
        else if(iteration > 50) return true;
        return false;
    }

    /**
     * Instructions on the current iteration
     * @param iteration number of the iteration
     * @param training index of the training processed
     * @param realLang index of real lang of the essay
     * @param result index of predicted lang of the essay
     * @throws Exception
     */
    protected void processIteration(Integer iteration, Integer training, Integer realLang, Integer result) throws Exception{
        if (realLang != result) {
            // Increase number of errors
            numberErrors++;
        }
    }

    /**
     * Instructions after an iteration on the subset
     * @param iteration number of the iteration executed
     */
    protected void afterIteration(Integer iteration){}

    public Model getModel() {
        return model;
    }

    public Integer getNumberErrors() {
        return numberErrors;
    }
}
