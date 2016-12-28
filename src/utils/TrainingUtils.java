package utils;

import model.Training;

import java.util.List;
import java.util.Random;

/**
 * Created by Thomas on 24/10/2016.
 */
public class TrainingUtils {
    /**
     * Mix a list of trainings
     * @param trainingsToMix mixed list
     * @return
     */
    public static List<Training> mix(List<Training> trainingsToMix){
        Random random = new Random();
        List<Training> trainings = trainingsToMix;
        for(int i = 0; i< trainings.size(); i++){
            int index1 = random.nextInt(trainings.size()-1);

            int index2 = index1;
            do{
                index2 = random.nextInt(trainings.size()-1);
            }while (index1!=index2);
            Training value1 = trainings.get(index1);
            trainings.set(index1, trainings.get(index2));
            trainings.set(index2, value1);
        }
        return trainings;
    }
}
