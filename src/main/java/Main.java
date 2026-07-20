package src.main.java;

import java.util.*;
import java.nio.*;
import java.io.*;
import org.jline.reader.*;
import org.jline.reader.LineReader;
import builtin.*;
import custom.declarePair;
import parser.parse;


public class Main {

    //public static ArrayList<String> HIS = new ArrayList<>();

    public static declarePair DECLARE_PAIR = new declarePair(new HashMap<>(), new HashSet<>());

    public static final HashSet<String> BUILT_IN = new HashSet<>(List.of("echo", "exit", "type", "pwd", "cd", "history", "declare"));

    public static void main(String[] args) throws Exception {
        String historyFile = System.getenv("HISTFILE");

        if(historyFile != null){
            builtin.history.history_r(historyFile, terminalEnvironnment.HIS);
        }

        LineReader reader = terminalEnvironnment.setupTerminal();
        
        
        while(true) {
            String cmd;
            try {
                //cmd = reader.readLine(System.getProperty("user.dir") +"$ ");
                cmd = reader.readLine("$ ");
            } catch (EndOfFileException e) {
                break;
            } catch (UserInterruptException e) {
                continue;
            }
            //HIS.add(cmd);
            custom.cmd parsedCmd = parse.parse(cmd);
            if(parsedCmd == null) {
                continue;
            }else if(parsedCmd.cmd.equals("exit")) {
                break;
            } 
            else if(parsedCmd.isBuiltin && !parsedCmd.cmd.equals("type")) {
                execute.execute(parsedCmd, terminalEnvironnment.HIS);
            }
            
            else if (parsedCmd.cmd.equals("type")) {
                String cmp_cmd = parsedCmd.Args.get(0);
                System.out.println(typeOf(cmp_cmd));
            }
            
            else if (getAbsolutePath(parsedCmd.cmd) != null) {
                ArrayList<String> command = new ArrayList<>();
                command.add(parsedCmd.cmd);
                command.addAll(parsedCmd.Args);
                ProcessBuilder pb = new ProcessBuilder(command);
                pb.inheritIO();
                Process p = pb.start();
                p.waitFor();
            }
            
            else {
                System.out.println(cmd + ": command not found");
            }

	        
        }
        if (historyFile != null){
            builtin.history.history_w(historyFile, terminalEnvironnment.HIS);
        }
        
        
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
