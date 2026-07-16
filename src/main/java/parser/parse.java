package parser;

import custom.cmd;
import java.io.File;
import java.util.ArrayList;
import static src.main.java.Main.BUILT_IN;
import static src.main.java.Main.DECLARE_PAIR;

public class parse{
    public static custom.cmd parse(String input){

        String cmd;
        boolean isBuiltin = false;
        char flag = ' ';
        ArrayList<String> Args = new ArrayList<>();
        boolean hasPath = false;
        boolean isPathAbsolute = false;

        int breakPoint = 0;

        if(input == null || input.trim().isEmpty()){
            return null;
        }
        cmd = "";
        for(int i = 0;i < input.length(); i++){
            if(input.charAt(i) == ' '){
                breakPoint = i;
                break;
            } else{
                cmd += input.charAt(i);
            }
        }

        if(breakPoint == 0){
            breakPoint = input.length();
        }

        input = input.substring(breakPoint).trim();

        if(BUILT_IN.contains(cmd)){
            isBuiltin = true;

            if(input.length() > 0 && input.charAt(0) == '-'){
                flag = input.charAt(1);
                input = input.substring(2).trim();
            }
        }else{
            isBuiltin = false;
            flag = ' ';
        }
        String toAdd = "";
        for(int i = 0;i < input.length(); i++){
            if(input.charAt(i) == '\''){
                i = checkSingleQuote(input, i, toAdd, Args);
            }else if(input.charAt(i) == ' '){
                if(toAdd.length() > 0){
                    Args.add(toAdd);
                    toAdd = "";
                }
            }else{
                toAdd += input.charAt(i);
            }
        }
        if(toAdd.length() > 0){
            Args.add(toAdd);
        }
        int argIndex = 0;
        for(String arg : Args){
            if(arg.contains("$")){
                for(int i = 0;i < arg.length(); i++){
                    if(arg.charAt(i) == '$' && i+3 < arg.length() && arg.charAt(i+1) == '{'){
                        int endIndex = -1;
                        int itr = i+2;
                        while(itr < arg.length()){
                            if(arg.charAt(itr) == '}'){
                                endIndex = itr;
                                break;
                            }
                            itr++;
                        }
                        if(endIndex != -1 && endIndex > i+2){
                            String varName = arg.substring(i+2, endIndex);
                            String value = DECLARE_PAIR.getValue(varName);
                            if(value != null){
                                arg = arg.substring(0, i) + value + arg.substring(endIndex+1);
                                i += value.length() - 1;
                            }else{
                                arg = arg.substring(0, i) + arg.substring(endIndex+1);
                                i--;
                            }
                        }
                    }else if(arg.charAt(i) == '$'){
                        String varName = "";
                        int itr = i + 1;
                        while(itr < arg.length() && (Character.isLetterOrDigit(arg.charAt(itr)) || arg.charAt(itr) == '_')){
                            varName += arg.charAt(itr);
                            String value = DECLARE_PAIR.getValue(varName);
                            if(value != null){
                                arg = arg.substring(0, i) + value + arg.substring(itr+1);
                                i += value.length() - 1;
                                break;
                            }
                            itr++;
                        }
                        if(arg.charAt(i) == '$' && DECLARE_PAIR.getValue(arg.substring(i+1, arg.length())) == null){
                            arg = arg.substring(0, i);
                            i--;
                        }
                    }
                }
            }
            if(!Args.get(argIndex).equals(arg)){
                Args.set(argIndex, arg);
            }
            argIndex++;
        }

        Args.removeIf(String::isEmpty);

        if(Args.size() == 1 && cmd.equals("cd")){
            if(new File(Args.get(0)).isAbsolute()){
                isPathAbsolute = true;
            }
            hasPath = true;
        }

        

        return new custom.cmd(cmd, isBuiltin, flag, Args, hasPath, isPathAbsolute);
    }

    private static int checkSingleQuote(String input, int i, String toAdd, ArrayList<String> Args){
        int errRet = i;
        i++;
        int k = i;
        boolean flag = false;
        while(i < input.length() && input.charAt(i) != '\''){
            toAdd += input.charAt(i);
            i++;
        }
        if(i < input.length() && input.charAt(i) != ' '){
            for(int j = i+1; j < input.length(); j++){
                if(input.charAt(j) == '\''){
                    flag = true;
                    k = checkSingleQuote(input, j, toAdd, Args);

                }else if(input.charAt(j) == ' '){
                    i = j;
                    break;
                }else{
                    toAdd += input.charAt(j);
                }
            }
        }
        if(i == input.length()){
            //System.out.println("Error: Unmatched single quote");
            return errRet;
        }
        if(!flag){
            Args.add(toAdd);
            return i; 
        }
        return k;
        
        
    }


}