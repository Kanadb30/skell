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

        for(String arg : Args){
            if(arg.contains("$")){
                for(int i = 0;i < arg.length(); i++){
                    if(arg.charAt(i) == '$'){
                        String varName = "";
                        i++;
                        while(i < arg.length() && (Character.isLetterOrDigit(arg.charAt(i)) || arg.charAt(i) == '_')){
                            varName += arg.charAt(i);
                            String value = DECLARE_PAIR.getValue(varName);
                            if(value != null){
                                arg = arg.replace("$" + varName, value);
                            }
                            i++;
                        }
                        
                    }
                }
            }
        }

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
        while(i < input.length() && input.charAt(i) != '\''){
            toAdd += input.charAt(i);
            i++;
        }
        if(i == input.length()){
            //System.out.println("Error: Unmatched single quote");
            return errRet;
        }
        Args.add(toAdd);
        return i;
    }


}