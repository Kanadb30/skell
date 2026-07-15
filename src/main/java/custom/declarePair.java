package custom;

public class declarePair {
    HashMap<String, String> declareMap;
    HashSet<String> declareSet;

    public declarePair(HashMap<String, String> declareMap){
        this.declareMap = declareMap;
        this.declareSet = new HashSet<>();
    }

    public void addVariable(String variable, String value){
        declareMap.put(variable, value);
        declareSet.add(variable);
    }
}