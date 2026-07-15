package src.main.java;

import java.util.*;
import java.nio.*;
import java.io.*;
import org.jline.reader.*;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.*;
import builtin.*;
import custom.declarePair;
import parser.parse;


public class Main {

    //public static ArrayList<String> HIS = new ArrayList<>();

    public static declarePair DECLARE_PAIR = new declarePair(new HashMap<>(), new HashSet<>());

    public static final HashSet<String> BUILT_IN = new HashSet<>(List.of("echo", "exit", "type", "pwd", "cd", "history", "declare"));

    public static void main(String[] args) throws Exception {
        String historyFile = System.getenv("HISTFILE");
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .history(new DefaultHistory())
            .build();
        History HIS = reader.getHistory();
        if (historyFile != null){
            builtin.history.history_r(historyFile, HIS);
        }
        
        while(true) {
            String cmd;
            try {
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
            } else if(parsedCmd.isBuiltin){
                parsedCmd.Args = expandArgs(parsedCmd.Args);
                execute.execute(parsedCmd, HIS);
            }
            
            else if (parsedCmd.cmd.equals("type")) {
                String cmp_cmd = parsedCmd.Args.get(0);
                System.out.println(typeOf(cmp_cmd));
            }
            
            else if (getAbsolutePath(parsedCmd.cmd) != null) {
                ArrayList<String> expandedArgs = expandArgs(parsedCmd.Args);
                ArrayList<String> command = new ArrayList<>();
                command.add(getAbsolutePath(parsedCmd.cmd));
                command.addAll(expandedArgs);
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
            builtin.history.history_w(historyFile, HIS);
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

    private static ArrayList<String> expandArgs(ArrayList<String> args){
        ArrayList<String> expandedArgs = new ArrayList<>();
        for(String arg : args){
            String expandedArg = arg;
            int start = 0;
            while(start < expandedArg.length()){
                int dollarIndex = expandedArg.indexOf('$', start);
                if(dollarIndex == -1){
                    break;
                }
                int end = dollarIndex + 1;
                while(end < expandedArg.length() && (Character.isLetterOrDigit(expandedArg.charAt(end)) || expandedArg.charAt(end) == '_')){
                    end++;
                }
                String variableName = expandedArg.substring(dollarIndex + 1, end);
                String value = DECLARE_PAIR.getValue(variableName);
                if(value != null){
                    expandedArg = expandedArg.substring(0, dollarIndex) + value + expandedArg.substring(end);
                    start = dollarIndex + value.length();
                } else {
                    start = end;
                }
            }
            expandedArgs.add(expandedArg);
        }
        return expandedArgs;
    }
}
