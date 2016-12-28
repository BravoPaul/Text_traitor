package controller;

import exception.FeatureDoesntExistException;
import java.util.ArrayList;

/**
<<<<<<< HEAD
 * Created by Thomas on 20/09/2016.
=======
 * 
>>>>>>> 061a691126893d5eb224c3de8ae977c9384ee8b9
 */
public class Decoder {
    /**
     * Scalar product between the vector and the matrix model
     * Return the index of the highest score
     * @param vector
     * @param matrix
     * @return
     */
    public static Integer decode(ArrayList<Integer> vector, Float[][] matrix) throws FeatureDoesntExistException{
        ArrayList<Float> vector_score = new ArrayList<>();
        Float sum;


        if(matrix == null || matrix.length < 1 ) return  null;
        if(vector == null) return  null;

        // For each lang
        for(int index_lng = 0; index_lng < matrix[0].length; index_lng++) { // iterating on the language of the model
            // For each feature
            sum = 0f;
            for (Integer index_feature : vector) { //iterating on the features of the training
                try {
                    if(index_feature != null) sum += matrix[index_feature][index_lng]; // getting the appropriate score
                } catch (IndexOutOfBoundsException e) {
                    throw new FeatureDoesntExistException("This feature doesn't exist");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }
            vector_score.add(sum); // for every language we ll have a score depanding on its features
        }

        return indexMaxScore(vector_score); // Thus it returns the index of the language with the best score ( the more probable )

    }


    /**
     * Get the index of the maximum score.
     * @param vector
     * @return
     */
    public static Integer indexMaxScore(ArrayList<Float> vector){
        Float max = vector.get(0);
        Integer index=0;
        for(int i=1;i<vector.size();i++){
            if(vector.get(i) > max) {
                max = vector.get(i);
                index = i;
            }
        }
        return index;
    }

}
