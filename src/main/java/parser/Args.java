package parser;

import java.util.*;
import static src.main.java.Main.DECLARE_PAIR;

public class Args{
    public static ArrayList<String> ParseArgs(String input, ArrayList<String> Args){
        StringBuilder currentArg = new StringBuilder();
        StringBuilder currentVar = new StringBuilder();
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
        boolean tokenStarted = false;
        boolean seenDollar = false;
        boolean seenDollarInBrackets = false;

        for( char token : input.toCharArray()){
            if(token == ' ' && !inSingleQuotes && !inDoubleQuotes){
                if(tokenStarted && currentArg.length() > 0 && !seenDollar && !seenDollarInBrackets){
                    Args.add(currentArg.toString());
                    currentArg.setLength(0);
                    tokenStarted = false;
                    seenDollar = false;
                    seenDollarInBrackets = false;
                }else if(seenDollar){
                    String varValue = Main.DECLARE_PAIR.get(currentVar.toString());
                    if(varValue != null){
                        currentArg.append(varValue);
                    }
                    currentVar.setLength(0);
                    tokenStarted = false;
                    seenDollar = false;
                    seenDollarInBrackets = false;
                }
            }else if(token == '\'' && !inDoubleQuotes){
                inSingleQuotes = !inSingleQuotes;
                tokenStarted = true;
            }else if(token == '\"' && !inSingleQuotes){
                inDoubleQuotes = !inDoubleQuotes;
                tokenStarted = true;
            }else if(token == '$' && !inSingleQuotes){
                seenDollar = true;
                tokenStarted = true;
            }else if(token == '{' && seenDollar && !inSingleQuotes){
                seenDollarInBrackets = true;
                tokenStarted = true;
            }else if(token == '}' && seenDollarInBrackets && !inSingleQuotes){
                String varValue = Main.DECLARE_PAIR.get(currentVar.toString());
                if(varValue != null){
                    currentArg.append(varValue);
                }
                currentVar.setLength(0);
                seenDollarInBrackets = false;
                seenDollar = false;
                tokenStarted = true;
            }else{
                if(seenDollar){
                    currentVar.append(token);
                }else{
                    currentArg.append(token);
                }
            }
        }
    }
}