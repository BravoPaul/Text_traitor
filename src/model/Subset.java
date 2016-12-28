package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Thomas on 04/10/2016.
 */
public class Subset {
    List<Training> learning = new ArrayList<>();
    List<Training> validation = new ArrayList<>();
    public Subset(){}
    public Subset(ArrayList<Training> learning, ArrayList<Training> validation) {
        this.learning = learning;
        this.validation = validation;
    }

    public void setLearning(List<Training> learning) {
        this.learning = learning;
    }

    public void setValidation(List<Training> validation) {
        this.validation = validation;
    }

    public List<Training> getLearning() {
        return learning;
    }

    public List<Training> getValidation() {
        return validation;
    }
	@Override
	public String toString() {
		return "Subset [learning size=" + learning.size() + ", validation size=" + validation.size()
				+ "]";
	}


}
