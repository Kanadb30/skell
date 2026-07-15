package custom;

import java.util.HashMap;
import java.util.HashSet;

public class declarePair {
    public HashMap<String, String> declareMap;
    public HashSet<String> declareSet;

    public declarePair(HashMap<String, String> declareMap){
        this.declareMap = declareMap;
        this.declareSet = new HashSet<>();
    }

    public void addVariable(String variable, String value){
        declareMap.put(variable, value);
        declareSet.add(variable);
    }
}