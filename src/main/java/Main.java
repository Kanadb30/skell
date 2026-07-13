import java.util.*;
import java.nio.*;
import java.io.*;


public class Main {

    static final HashSet<String> BUILT_IN = new HashSet<>(List.of("echo", "exit", "type", "pwd", "cd"));

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
                
            } 

            else if(ARGS.get(0).equals("pwd")){
                System.out.println(System.getProperty("user.dir"));
            }

            else if(ARGS.get(0).equals("cd")){
                if(ARGS.get(1).startsWith("/")){
                    String errString = ARGS.get(1);
                    ARGS.set(1, ARGS.get(1).substring(1));
                }
                if(ARGS.size() > 1){
                    if(new File(ARGS.get(1)).isAbsolute()){
                        System.setProperty("user.dir", ARGS.get(1));
                    }else{
                        ArrayList<String> pathToFollow = new ArrayList<>(List.of(ARGS.get(1).split("/")));
                        String currentDir = System.getProperty("user.dir");
                        for(String segment : pathToFollow){
                            if(segment.equals("..")){
                                File parentDir = new File(currentDir).getParentFile();
                                if(parentDir != null){
                                    currentDir = parentDir.getAbsolutePath();
                                }else {
                                    System.out.println("cd: " + errString + ": No such file or directory");
                                }
                            }else if(segment.equals(".")){
                                continue;
                            }else{
                                File f = new File(currentDir, segment);
                                if(f.exists() && f.isDirectory()){
                                    currentDir = f.getAbsolutePath();
                                }else {
                                    System.out.println("cd: " + errString + ": No such file or directory");
                                    break;
                                }
                            }
                        }
                        System.setProperty("user.dir", currentDir);
                    }
                }
            }

            else if(ARGS.get(0).equals("echo")) {
                System.out.println(String.join(" ", ARGS.subList(1, ARGS.size())));

            } 
            
            else if (ARGS.get(0).equals("type")) {
                String cmp_cmd = cmd.substring(5);
                System.out.println(typeOf(cmp_cmd));
            }
            
            else if (getAbsolutePath(ARGS.get(0)) != null) {
                ProcessBuilder pb = new ProcessBuilder(ARGS);
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
        String path = System.getenv("PATH");
        ArrayList<String> PATH_DIRS = new ArrayList<>(List.of(path.split(File.pathSeparator)));
        for(String toSearch : PATH_DIRS ){
            File f = new File(toSearch, cmd);
            if(f.exists() && f.canExecute()){
                return f.getAbsolutePath();
            }
            
        }
        return null;
    }
}
