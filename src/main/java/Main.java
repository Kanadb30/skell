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
            ArrayList<String> ARGS = new ArrayList<>(List.of(cmd.split(" ")));
            if(ARGS.get(0).equals("exit")) {
                break;
                
            } else if(ARGS.get(0).equals("echo")) {
                System.out.println(String.join(" ", ARGS.subList(1, ARGS.size())));

            } else if (ARGS.get(0).equals("type ")) {
                String cmp_cmd = cmd.substring(5);
                System.out.println(typeOf(cmp_cmd));
            }else if (getAbsolutePath(ARGS.get(0)) != null) {

                ProcessBuilder pb = new ProcessBuilder(getAbsolutePath(ARGS.get(0),ARGS.get(1),ARGS.get(2)));
                pb.inheritIO();
                Process p = pb.start();
                p.waitFor();
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
        String absPath = getAbsolutePath(cmd);
        if(absPath != null){
            return cmd + " is " + absPath;
        }

        return cmd + ": not found";
    }

    private static String getAbsolutePath(String cmd){
        for(String toSearch : PATH_DIRS ){
            File f = new File(toSearch, cmd);
            if(f.exists() && f.canExecute()){
                return f.getAbsolutePath();
            }
            
        }
        return null;
    }
}
