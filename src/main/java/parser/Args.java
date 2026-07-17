package parser;

import java.util.*;
import static src.main.java.Main.DECLARE_PAIR;

public class Args{
    public static void ParseArgs(String input, ArrayList<String> Args){
        StringBuilder currentArg = new StringBuilder();
        StringBuilder currentVar = new StringBuilder();
        boolean inSingleQuotes = false;
        boolean inDoubleQuotes = false;
        boolean tokenStarted = false;
        boolean seenDollar = false;
        boolean seenDollarInBrackets = false;
        boolean seenBackslash = false;

        for( char token : input.toCharArray()){
            if(token == '\\' && !inSingleQuotes && !seenBackslash){
                seenBackslash = true;
                tokenStarted = true;
            }else if(token == ' ' && !inSingleQuotes && !inDoubleQuotes && !seenBackslash){
                if(tokenStarted && currentArg.length() > 0 && !seenDollar && !seenDollarInBrackets){
                    Args.add(currentArg.toString());
                    currentArg.setLength(0);
                    tokenStarted = false;
                    seenDollar = false;
                    seenDollarInBrackets = false;
                }else if(seenDollar){
                    String varValue = DECLARE_PAIR.getValue(currentVar.toString());
                    if(varValue != null){
                        currentArg.append(varValue);
                    }
                    Args.add(currentArg.toString());
                    currentArg.setLength(0);
                    currentVar.setLength(0);
                    tokenStarted = false;
                    seenDollar = false;
                    seenDollarInBrackets = false;
                }
            }else if(token == '\'' && !inDoubleQuotes && !seenBackslash){
                inSingleQuotes = !inSingleQuotes;
                tokenStarted = true;
            }else if(token == '\"' && !inSingleQuotes && !seenBackslash){
                inDoubleQuotes = !inDoubleQuotes;
                tokenStarted = true;
            }else if(token == '$' && !inSingleQuotes && !seenBackslash){
                seenDollar = true;
                tokenStarted = true;
            }else if(token == '{' && seenDollar && !inSingleQuotes && !seenBackslash){
                seenDollarInBrackets = true;
                tokenStarted = true;
            }else if(token == '}' && seenDollarInBrackets && !inSingleQuotes && !seenBackslash){
                String varValue = DECLARE_PAIR.getValue(currentVar.toString());
                if(varValue != null){
                    currentArg.append(varValue);
                }
                currentVar.setLength(0);
                seenDollarInBrackets = false;
                seenDollar = false;
                tokenStarted = true;
            }else{
                tokenStarted = true;
                if(seenDollar){
                    currentVar.append(token);
                }else{
                    currentArg.append(token);
                }
                if(seenBackslash){
                    seenBackslash = false;
                }
            }
        }
        if(tokenStarted && currentArg.length() > 0 && !seenDollar && !seenDollarInBrackets){
            Args.add(currentArg.toString());
            currentArg.setLength(0);
            tokenStarted = false;
            seenDollar = false;
            seenDollarInBrackets = false;
        }else if(seenDollar){
            String varValue = DECLARE_PAIR.getValue(currentVar.toString());
            if(varValue != null){
                currentArg.append(varValue);
            }
            Args.add(currentArg.toString());
            currentArg.setLength(0);
            currentVar.setLength(0);
            tokenStarted = false;
            seenDollar = false;
            seenDollarInBrackets = false;
        }

        Args.removeIf(arg -> arg.isEmpty());

    }
}