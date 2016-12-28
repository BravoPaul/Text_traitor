package controller.parser;

import model.Features;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thomas on 20/09/2016.
 */
public class Parser {
    private ArrayList<String> langs = new ArrayList<>();

    private String path;
    private Features features;

    /**
     * Parse the file
     */
    public Parser(String path){
        this.path = path;
    }
    public Parser(String path, Features features){
        this.features = features;
        this.path = path;
    }

    public void parse(){

        BufferedReader br = null;

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));

            while ((sCurrentLine = br.readLine()) != null) {
                parseLine(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
    /**
     * parse a line
     * @param line
     */
    protected void parseLine(String line){
        // Add the language
        addLang(formatLang(line));

        // Parse the sentence
        parseEssay(formatEssay(line));

        endLine();

    }

    protected void endLine(){}

    protected void addLang(String lng){
        if(!langs.contains(lng)) langs.add(lng);
    }

    /**
     * format the essay
     * @param line
     * @return
     */
    private String formatEssay(String line){
        return line.split("\\) ")[1];
    }

    /**
     * format the lang
     * @param line
     * @return
     */
    private String formatLang(String line){
        return line.split(",")[0].substring(1);
    }

    /**
     * Parse the essay, execute the features
     * @param essay
     */
    protected void parseEssay(String essay){
        // Word unigram & bigram
        if(features.isUnigram() || features.isBigram()) {
            String[] words = essay.split(" ");
            if(features.isUnigram()) parseWord(words[0]);
            for (int i = 1; i < words.length; i++) {
                // Unigram
                if(features.isUnigram()) parseWord(words[i]);
                // Bigram
                if(features.isBigram())  parseWord(essay.split(" ")[i - 1] + " " + essay.split(" ")[i]);
            }
        }

        // Char uni-bi tri
        if(features.isUnicar()) {
            parseWord("" + essay.charAt(0));
            parseWord("" + essay.charAt(1));
            parseWord("" + essay.charAt(2));
        }
        if(features.isBicar()) {
            parseWord("" + essay.charAt(0) + essay.charAt(1));
            parseWord("" + essay.charAt(1) + essay.charAt(2));
        }
        if(features.isTricar()) parseWord(""+essay.charAt(0)+essay.charAt(1)+essay.charAt(2));


        for(int i=3;i<essay.length();i++) {
            //Quadri
            if(features.isQuadricar()) parseWord(""+essay.charAt(i-3)+essay.charAt(i-2)+essay.charAt(i-1)+essay.charAt(i)); // -- 29%-34% errors
            // Tri
            if(features.isTricar()) parseWord(""+essay.charAt(i-2)+essay.charAt(i-1)+essay.charAt(i));
            // Bi
            if(features.isBicar()) parseWord(""+essay.charAt(i-1)+essay.charAt(i));
            // Uni
            if(features.isUnicar()) parseWord(""+essay.charAt(i));
        }

    }

    /**
     * parse the word
     * @param word
     */
    protected void parseWord(String word){}

    public ArrayList<String> getLangs() {
        return langs;
    }
}
