package controller.validation;

import controller.Computation;
import model.Model;
import model.Subset;
import model.Training;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 19/10/2016.
 */
public class Validation implements Runnable {
    private Model model;
    private Subset subset;
    private float errorRate;
    private boolean over = false;
    private Integer iterationsMax;
    private ArrayList<String> languages;
    private Integer ID;
    private static Integer idCount = 0;

    public Validation(Subset subset, Model model, ArrayList<String> languages, Integer iterations_max){
        this.ID = idCount++;
        this.iterationsMax = iterations_max;
        this.languages = languages;
        this.model = model;
        this.subset = subset;
    }

    public float getErrorRate() {
        return errorRate;
    }

    @Override
    public void run() {
        /// -- Learning

        System.out.println("\tCrossValidation: Learning "+ID+" running...");
        Computation cLearn = new Computation(model, languages, subset.getLearning()){
            @Override
            protected void processIteration(Integer i, Integer t, Integer realLang, Integer result) throws Exception{
                if (realLang != result) {
                    // -- Increase number of errors
                    numberErrors++;

                    // -- Update matrix value
                    float value;
                    if (i == 0) value = 1;
                    else value = 1f / i;

                    // Adding 1/i to the real lang value
                    model.modify(realLang, trainings.get(t).getFeatures(), value);

                    // Sub 1/i to the prediction lang value
                    model.modify(result, trainings.get(t).getFeatures(), -1 * value);
                }
            }
            /*@Override
            protected void afterIteration(Integer i){
                System.out.println("CrossValidation: Learning "+ID+"\titeration: "+i+"\terror rate: "+numberErrors / (float) trainings.size()*100f);
            }*/
        };
        cLearn.compute();
        System.out.println("\tCrossValidation: Learning "+ID+" done.");

        // -- Validation
        System.out.println("\tCrossValidation: Validation "+ID+" running...");
        Computation cVal = new Computation(cLearn.getModel(), languages, subset.getValidation()){
            /*@Override
            protected void afterIteration(Integer i){
                System.out.println("CrossValidation: Validation "+ID+"\titeration: "+i+"\terror rate: "+numberErrors / (float) trainings.size()*100f);
            }*/
            @Override
            protected boolean stopCondition(Integer iteration){
                if(iteration > 0) return true;
                return false;
            }
        };
        cVal.compute();
        System.out.println("\tCrossValidation: Validation "+ID+" done.");
        errorRate = cVal.getNumberErrors()/(float) subset.getValidation().size()*100f;
    }

	@Override
	public String toString() {
		return "Validation [model=" + model + ", subset=" + subset
				+ ", errorRate=" + errorRate + ", over=" + over
				+ ", iterationsMax=" + iterationsMax + ", languages="
				+ languages + ", ID=" + ID + "]";
	}



}
