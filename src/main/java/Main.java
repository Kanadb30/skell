import java.util.*;
import java.nio.*;
import java.io.*;

HashSet<String> BUILT_IN = new HashSet<>();
        BUILT_IN.add("echo");
        BUILT_IN.add("exit");
        BUILT_IN.add("type");
String path = System.getenv("PATH");
ArrayList<String> PATH_DIRS = new ArrayList<>(List.of(path.split(File.pathSeperator)));

public class Main {
    public static void main(String[] args) throws Exception {
        while(true) {
            System.out.print("$ ");
	        Scanner sc = new Scanner(System.in);
	        String cmd = sc.nextLine();
            if(cmd.equals("exit")) {
                break;
                
            } else if(cmd.startsWith("echo ")) {
                System.out.println(cmd.substring(5));

            } else if (cmd.startsWith("type ")) {
                String cmp_cmd = cmd.substring(5);
                System.out.println(typeOf(cmp_cmd));
            } 
            
            else {
                System.out.println(cmd + ": command not found");
            }

	        
        }
        
    }

    private static String typeOf(String cmd){
        if(BUILT_IN.contains(cmd)){
            return cmd + " is a shell builtin";
        }
        for(String toSearch : PATH_DIRS ){
            File f = new File(toSearch, cmd);
            if(f.exists() && f.isExecutable()){
                return cmd + " is " + f.getAbsolutePath();
            }
            
        }
        return cmd + ": not found";
    }
}
