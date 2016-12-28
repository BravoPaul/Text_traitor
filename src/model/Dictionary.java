package model;

/**
 * Created by Yassine on 20/09/2016.
 */
import java.util.HashMap;

public class Dictionary {
    private HashMap<String,Integer> dictionary  ;
    public Dictionary() {

        dictionary = new HashMap<String,Integer>();
    }
    public Integer size(){
        return dictionary.size();
    }

    public Integer checkWord(String wordToCheck )
    {
        if (dictionary.get(wordToCheck) == null)
        {
            dictionary.put(wordToCheck, dictionary.size());
            return dictionary.size()-1 ;
        }
        else
            return dictionary.get(wordToCheck);
    }
    public void addDictionnary(String s , Integer i) {
        dictionary.put(s,i);
    }

    public Integer getIndex(String word){
        return dictionary.get(word);
    }

    public HashMap<String, Integer> getDictionnary() {
        return dictionary;
    }
    @Override
    public String toString(){
        String str = "key\tvalue\n";
        for(String key : dictionary.keySet()){
            str+=key+"\t"+dictionary.get(key)+"\n";
        }
        return str;
    }


}
