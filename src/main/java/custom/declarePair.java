package custom;

import java.util.HashMap;
import java.util.HashSet;

public class declarePair {
    public HashMap<String, String> declareMap;
    public HashSet<String> declareSet;

    public declarePair(HashMap<String, String> declareMap, HashSet<String> declareSet) {
        this.declareMap = declareMap;
        this.declareSet = declareSet;
    }

    public void put(String variable, String value){
        addVariable(variable, value);
    }

    public String getValue(String variable){
        return declareMap.get(variable);
    }

    public void addVariable(String variable, String value){
        declareMap.put(variable, value);
        declareSet.add(variable);
    }
}