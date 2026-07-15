package builtin;

import java.util.*;
import custom.cmd;
import static src.main.java.Main.DECLARE_PAIR;

public class declare{
    public static void declare_p(String variable){
        String value = DECLARE_PAIR.getValue(variable);
        if (value != null) {
            System.out.println("declare -- " + variable + "=\"" + value + "\"");
        } else {
            System.out.println("declare: " + variable + ": not found");
        }
    }

    public static void declare(String variable){
        String[] parts = variable.split("=", 2);
        if (parts.length == 2) {
            String name = parts[0];
            String value = parts[1];
            if(!name.matches("[a-zA-Z_][a-zA-Z0-9_]*")){
                System.out.println("declare: `" + variable + "': not a valid identifier");
                return;
            }
            DECLARE_PAIR.put(name, value);
        } else {
            System.out.println("declare: " + variable + ": invalid format");
        }
    }
}