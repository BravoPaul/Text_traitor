package model;

import java.util.ArrayList;
/**
 * Created by Thomas on 30/09/2016.
 */
public class Training {
    private String language;
    private ArrayList<Integer> features;
    public Training(){
        features = new ArrayList<>();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public ArrayList<Integer> getFeatures() {
        return features;
    }

    public void addFeature(Integer feature) {
        this.features.add(feature);
    }

    @Override
    public String toString() {
        return "Training{" +
                "language='" + language + '\'' +
                ", features=" + features +
                '}';
    }


}
