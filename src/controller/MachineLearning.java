package controller;

import controller.confusion.Confusion;
import controller.parser.Parser;
import controller.validation.CrossValidation;
import model.*;

import java.util.ArrayList;

/**
 * Created by Thomas on 20/09/2016.
 */
public class MachineLearning {
    private Dictionary dictionary = new Dictionary();
    private TrainingSet trainingSet = new TrainingSet();
    private TestingSet testingSet = new TestingSet();
    private ArrayList<String> languages = new ArrayList<>();
    private Model model;

    public MachineLearning() throws Exception{}
    public MachineLearning(ArgumentsML argumentsML) throws Exception{
        long debut;
        if(argumentsML.getDictFile() != null) {
            debut = System.currentTimeMillis();
            System.out.print("Loading dictionary "+argumentsML.getDictFile()+" ...");
            dictionary = DictionarySaveLoad.load(argumentsML.getDictFile());
            System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");

            if(argumentsML.getModelFile() !=  null) {
                debut = System.currentTimeMillis();
                System.out.print("Loading model "+argumentsML.getModelFile()+" ...");
                model = ModelSaveLoad.load(argumentsML.getModelFile());
                System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");
            }
            else{
                debut = System.currentTimeMillis();
                System.out.print("Building the training set...");
                parseTrainingSet(argumentsML.getFeatures(), argumentsML.getTrainingFile());
                System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");
                initModel();
            }
        }
        else{
            debut = System.currentTimeMillis();
            System.out.print("Building the dictionary...");
            parseDictionary(argumentsML.getFeatures());
            System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");
            debut = System.currentTimeMillis();
            System.out.print("Building the training set...");
            parseTrainingSet(argumentsML.getFeatures(),  argumentsML.getTrainingFile());
            System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");
            initModel();
        }
            // -- k-Cross Validation (computes error rates)
        if(argumentsML.getModelFile() ==  null && argumentsML.getKcrossValidation() != null) {
            //System.out.println(argumentsML.getKcrossValidation()+" validations running...");
            CrossValidation crossValidation = new CrossValidation(trainingSet, languages, argumentsML.getKcrossValidation(), dictionary.size());
            // Launch threads of k-Cross Validation
            crossValidation.start();
        }

        if(argumentsML.getModelFile() ==  null) {
            debut = System.currentTimeMillis();
            System.out.print("Learning ...");
            Integer[][] confusionMatrix = new Integer[languages.size()][languages.size()];
            for(int i=0;i<languages.size();i++){
                for(int j=0;j<languages.size();j++){
                    confusionMatrix[i][j] = 0;
                }
            }
            Computation learning = new Computation(model, languages, trainingSet.getTrainings()) {
                @Override
                protected void processIteration(Integer i, Integer t, Integer realLang, Integer result) throws Exception {
                    if (realLang != result) {
                        // -- Update matrix value
                        float value;
                        if (i == 0) value = 1;
                        else value = 1f / i;

                        // Adding 1/i to the real lang value
                        model.modify(realLang, trainings.get(t).getFeatures(), value);

                        // Sub 1/i to the prediction lang value
                        model.modify(result, trainings.get(t).getFeatures(), -1 * value);


                    }
                    confusionMatrix[realLang][result] += 1;
                }
            };

            learning.compute();
            System.out.println("done in "+(System.currentTimeMillis()-debut)+"ms");

            System.out.println("Confusion matrix :");
            System.out.print("\t");
            for(int i=0;i<languages.size();i++){
                System.out.print(languages.get(i)+"\t");
            }
            System.out.print("\n");
            for(int i=0;i<languages.size();i++){
                System.out.print(languages.get(i)+"\t");
                for(int j=0;j<languages.size();j++){
                    System.out.print(confusionMatrix[i][j]+"\t");
                }
                System.out.print("\n");
            }
        }


        if(argumentsML.getModelFileOut() != null){
            ModelSaveLoad.save(argumentsML.getModelFileOut(), model);
        }
        if(argumentsML.getDictFileOut() != null){
            DictionarySaveLoad.save(argumentsML.getDictFileOut(), dictionary);
        }



        if(argumentsML.getTestFile() != null){

            parseTestFile(argumentsML.getTestFile(), argumentsML.getFeatures());
            // Get the result
            processTestingSet(argumentsML.getTestFileOut());
        }


    }

    private void initModel(){
        model = new Model(languages.size(), dictionary.size(), languages);
    }

        /*// -- Data preparation
        // Parse the data to build the dictionary & build the training set
        parseData();

        // Init the model
        Model model = new Model(languages.size(), dictionary.size());

        System.out.println("Step 2&3 : k-Cross validation & Learning");
        // -- k-Cross Validation (computes error rates)
        CrossValidation crossValidation = new CrossValidation(trainingSet, languages, 5, dictionary.size());
        // Launch threads of k-Cross Validation
        crossValidation.launch();

        // -- Learning
        // --- Training the model
        Computation learning = new Computation(model, languages, trainingSet.getTrainings()){
            @Override
            protected void processIteration(Integer i, Integer t, Integer realLang, Integer result) throws Exception{
                if (realLang != result) {
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
        };
        learning.compute();

        // --- Confusion Matrix
        System.out.println("Step 4 : Confusion matrix");
        System.out.print("\tCompute confusion matrix...");
        Confusion confusion = new Confusion(trainingSet, learning.getModel(), languages);
        confusion.compute();
        System.out.println("done.");



        while (!crossValidation.isOver()){
            Thread.sleep(1000); // Sleep 1 second
        }

        System.out.println("Step 5 : Results");
        // Display confusion matrix
        confusion.display();

        //  -- Get cross validation result
        System.out.println("\n\tMax Error rate : "+crossValidation.getMaxErrorRate());
        System.out.println("\tMin Error rate : "+crossValidation.getMinErrorRate());


    }

*/
     //Build the dictionary, get the languages, build the training set

    private void parseDictionary(Features features){
        String txt =  "C:\\Users\\Thomas\\School\\Polytech\\Machine Learning\\tp\\workspace\\et5-projet\\train.txt";
        Parser parser = new Parser(txt, features) {

            @Override
            protected void parseWord(String word) {
                dictionary.checkWord(word);
            }
        };
        parser.parse();
        languages = parser.getLangs();

        // Set number of lang
        trainingSet.setNumberLng(parser.getLangs().size());
    }

    private void parseTrainingSet(Features features, String txt){
        Parser parser = new Parser(txt, features) {
            @Override
            protected void addLang(String lng) {
                if(!languages.contains(lng)) languages.add(lng);
                trainingSet.newLearningTest(lng);
            }

            @Override
            protected void parseWord(String word) {
                trainingSet.addFeature(dictionary.getIndex(word));
            }

            protected void endLine() {
                trainingSet.endLearningTest();
            }
        };
        parser.parse();

        // Set number of lang
        trainingSet.setNumberLng(languages.size());
    }

    private void parseTestFile(String fileName, Features features) {
        System.out.print("Loading testing file: "+fileName+" ...");
        Parser parser = new Parser(fileName, features) {
            @Override
            protected void addLang(String lng) {
                testingSet.newLearningTest();
            }

            @Override
            protected void parseWord(String word) {
                testingSet.addFeature(dictionary.getIndex(word));
            }

            protected void endLine() {
                testingSet.endLearningTest();
            }
        };
        parser.parse();
        System.out.println("done");
    }

    private void processTestingSet(String fileName){
        System.out.print("Testing...");
        Testing testing = new Testing(model, model.getLanguages(), testingSet);
        testing.lauch();
        System.out.println("done");
        if(fileName != null) testing.save(fileName);
    }

    public static void main(String [] args) throws Exception {
        ArgumentsML argumentsML = new ArgumentsML();
        Features features = new Features();
        boolean _continue = true;
        int v;
        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                // With import dictionary
                case "-d":
                    // With import model
                    v = i;
                    if(args[v + 1] != null) {
                        if (args[v+ 2].equals("-m") && args[v + 3] != null) {
                            argumentsML.setModelFile(args[v + 3]);
                            i+=2;
                        }
                        argumentsML.setDictFile(args[v + 1]);
                        i++;
                    }
                    break;

                case "m":
                    v = i;
                    if(args[v + 1] != null) {
                        if (args[v + 2].equals("-d") && args[v + 3] != null) {
                            argumentsML.setDictFile(args[v + 3]);
                            argumentsML.setModelFile(args[v + 2]);
                            i+=3;
                        }
                    }
                    break;

                // List available features
                case "-listfeatures":
                    System.out.println("List of features :");
                    System.out.println("-f1 : Unicar");
                    System.out.println("-f2 : Bicar");
                    System.out.println("-f3 : Tricar");
                    System.out.println("-f4 : Quadricar");
                    System.out.println("-f5 : Unigram");
                    System.out.println("-f6 : Bigram");
                    _continue = false;
                    break;

                case "-f1":
                    features.setUnicar(true);
                    break;
                case "-f2":
                    features.setBicar(true);
                    break;
                case "-f3":
                    features.setTricar(true);
                    break;
                case "-f4":
                    features.setQuadricar(true);
                    break;
                case "-f5":
                    features.setUnigram(true);
                    break;
                case "-f6":
                    features.setBigram(true);
                    break;

                case "-test":
                    if(args[i + 1] != null) {
                        argumentsML.setTestFile(args[i + 1]);
                        i++;
                    }
                break;

                case "-testout":
                    if(args[i + 1] != null) {
                        argumentsML.setTestFileOut(args[i + 1]);
                        i++;
                    }
                    break;

                case "-train":
                    if(args[i + 1] != null) {
                        argumentsML.setTrainingFile(args[i + 1]);
                        i++;
                    }
                    break;

                case "-kcrossval":
                    if(args[i + 1] != null) {
                        argumentsML.setKcrossValidation(Integer.parseInt(args[i + 1]));
                        i++;
                    }
                    break;

                case "-c":
                    argumentsML.setConfusionMatrix(true);
                    break;

                case "-mout":
                    if(args[i + 1] != null) {
                        argumentsML.setModelFileOut(args[i + 1]);
                        i++;
                    }
                    break;

                case "-dout":
                    if(args[i + 1] != null) {
                        argumentsML.setDictFileOut(args[i + 1]);
                        i++;
                    }
                    break;

                case "-help":
                    System.out.println("List of options :");
                    System.out.println("-d [FILE]: specify a dictionary file to import");
                    System.out.println("-m [FILE]: specify a model file to import");
                    System.out.println("Warning : a model must come with a dictionary");
                    System.out.println("-dout [FILE]: export the model in a specific file");
                    System.out.println("-mout [FILE]: export the dictionary in a specific file");
                    System.out.println("-listfeatures: list the available features");
                    System.out.println("-f(1-6): feature");
                    System.out.println("-kcrossval: run a K-cross validation to get the error rate");
                    System.out.println("-c: display the confusion matrix");
                    System.out.println("-help: give details about options");
                    _continue = false;
                    break;

                default:
                    System.out.println("This option doesn't exist");
                    System.out.println("Use -help for more details");
                    break;
            }


        }
        if(_continue) {
            if(argumentsML.getTrainingFile() != null) {
                argumentsML.setFeatures(features);
                new MachineLearning(argumentsML);
            }
            else{
                System.out.println("Please specify a training file with the option -train.");
            }
        }
    }

}
