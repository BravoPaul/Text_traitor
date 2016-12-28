package model;

/**
 * Created by Thomas on 14/11/2016.
 */
public class ArgumentsML {
    private Features features = new Features();
    private String trainingFile = null;
    private String modelFile = null;
    private String dictFile = null;
    private String modelFileOut = null;
    private String dictFileOut = null;
    private String testFile = null;
    private String testFileOut = null;
    private Integer kcrossValidation=null;
    private boolean confusionMatrix = false;


    public String getTrainingFile() {
        return trainingFile;
    }

    public void setTrainingFile(String trainingFile) {
        this.trainingFile = trainingFile;
    }

    public boolean isConfusionMatrix() {
        return confusionMatrix;
    }

    public void setConfusionMatrix(boolean confusionMatrix) {
        this.confusionMatrix = confusionMatrix;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getModelFile() {
        return modelFile;
    }

    public void setModelFile(String modelFile) {
        this.modelFile = modelFile;
    }

    public String getDictFile() {
        return dictFile;
    }

    public void setDictFile(String dictFile) {
        this.dictFile = dictFile;
    }

    public String getModelFileOut() {
        return modelFileOut;
    }

    public void setModelFileOut(String modelFileOut) {
        this.modelFileOut = modelFileOut;
    }

    public String getDictFileOut() {
        return dictFileOut;
    }

    public void setDictFileOut(String dictFileOut) {
        this.dictFileOut = dictFileOut;
    }

    public String getTestFile() {
        return testFile;
    }

    public void setTestFile(String testFile) {
        this.testFile = testFile;
    }

    public Integer getKcrossValidation() {
        return kcrossValidation;
    }

    public void setKcrossValidation(Integer kcrossValidation) {
        this.kcrossValidation = kcrossValidation;
    }

    public String getTestFileOut() {
        return testFileOut;
    }

    public void setTestFileOut(String testFileOut) {
        this.testFileOut = testFileOut;
    }
}
