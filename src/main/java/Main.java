import java.util.*;
import java.nio.*;
import java.io.*;


public class Main {

    static final HashSet<String> BUILT_IN = new HashSet<>(List.of("echo", "exit", "type"));
    static final String path = System.getenv("PATH");
    static final ArrayList<String> PATH_DIRS = new ArrayList<>(List.of(path.split(File.pathSeparator)));

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.print("$ ");
            if(!sc.hasNextLine()) {
                break;
            }
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
        sc.close();
        
    }

    private static String typeOf(String cmd){
        if(BUILT_IN.contains(cmd)){
            return cmd + " is a shell builtin";
        }
        for(String toSearch : PATH_DIRS ){
            File f = new File(toSearch, cmd);
            if(f.exists() && f.canExecute()){
                return cmd + " is " + f.getAbsolutePath();
            }
            
        }
        return cmd + ": not found";
    }
}
