package controller.validation;

import model.Model;
import model.Subset;
import model.TrainingSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 */
public class CrossValidation extends Thread{
	private Integer numberVal;
	private ArrayList<Validation> validations = new ArrayList<>();
	private ArrayList<Thread> threadsCreateValidation = new ArrayList<Thread>();
	private ArrayList<Thread> threadsValidation = new ArrayList<Thread>();
	private Integer numberFeatures;
	private static final Integer ratio = 10;
	private static final Integer itMax = 15;
	private ArrayList<String> languages;

	public CrossValidation(TrainingSet trainingSet, ArrayList<String> languages,
			Integer number_validation, Integer number_features) {
		this.numberVal = number_validation;
		this.numberFeatures = number_features;
		this.languages = languages;
		try {
			init(trainingSet, languages);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Init the validation thread
	 *
	 * @param trainingSet
	 * @throws Exception
	 */
	private void init(TrainingSet trainingSet, ArrayList<String> language) throws Exception{
//    	System.out.println("le training set = "+trainingSet.toString());
		ExecutorService execute = Executors.newSingleThreadExecutor();
        List<Future<Subset>> list = new ArrayList<Future<Subset>>();

		for (int i = 0; i < numberVal; i++) {
			Future<Subset> future = execute.submit(trainingSet);
			list.add(future);
		}

		for(Future<Subset> fut : list){
            try {
                //print the return value of Future, notice the output delay in console
                // because Future.get() waits for task to get completed
                trainingSet.getSubsetsTab().add(fut.get());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        //shut down the executor service now
		execute.shutdown();


		for (int i = 0; i < numberVal; i++) {
			Validation validation = new Validation(TrainingSet.getSubsetsTab().get(i), new Model(trainingSet.getNumberLng(),numberFeatures, languages), language, itMax);
			validations.add(validation);
			this.threadsValidation.add(new Thread(validation));
		}
	}

    @Override
    public synchronized void start() {
		long debut = System.currentTimeMillis();
        System.out.println(numberVal+"-cross validation running...");
        super.start();
        launch();
        try {
            while (!this.isOver()) {
                this.sleep(1000);
            }
            //  -- Get cross validation result
            System.out.println(numberVal+"-cross validation done in "+(System.currentTimeMillis()-debut)+"ms");
            System.out.println("\tMax Error rate : "+this.getMaxErrorRate());
			System.out.println("\tMoy Error rate : "+this.getMoyErrorRate());
            System.out.println("\tMin Error rate : "+this.getMinErrorRate());
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
	/**
	 * Launch the validation thread
	 */
	public void launch() {
		for (Thread thread : threadsValidation) {
			thread.start();
		}
	}

	/**
	 * Is the tread of validation is over
	 *
	 * @return
	 */
	public boolean isOver() {
		for (Thread thread : threadsValidation) {
			if (thread.isAlive())
				return false;
		}
		return true;
	}

	/**
	 * Get the max error rate
	 *
	 * @return
	 */
	public float getMaxErrorRate() {
		float errorRate = 0;
		for (Validation validation : validations) {
			if (validation.getErrorRate() > errorRate)
				errorRate = validation.getErrorRate();
		}
		return errorRate;
	}

	/**
	 * Get the min error rate
	 *
	 * @return
	 */
	public float getMinErrorRate() {
		float errorRate = 100;
		for (Validation validation : validations) {
			if (validation.getErrorRate() < errorRate)
				errorRate = validation.getErrorRate();
		}
		return errorRate;
	}

	public float getMoyErrorRate() {
		float errorRate = 0;
		for (Validation validation : validations) {
			errorRate += validation.getErrorRate();
		}
		return errorRate/validations.size();
	}

}
