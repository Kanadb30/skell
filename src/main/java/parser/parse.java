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
        ArrayList<String> args = new ArrayList<>();
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
        
        Args.ParseArgs(input, args);
        
        return new custom.cmd(cmd, isBuiltin, flag, args, hasPath, isPathAbsolute);
    }
}