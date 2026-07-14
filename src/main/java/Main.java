import java.util.*;
import java.nio.*;
import java.io.*;
import org.jline.reader.*;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.*;


public class Main {

    //public static ArrayList<String> HIS = new ArrayList<>();

    static final HashSet<String> BUILT_IN = new HashSet<>(List.of("echo", "exit", "type", "pwd", "cd", "history"));

    public static void main(String[] args) throws Exception {
        File historyFile = new File(System.getenv("HISTFILE"));
        Terminal terminal = TerminalBuilder.builder().system(true).build();
        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .history(new DefaultHistory())
            .build();
        History HIS = reader.getHistory();
        if (historyFile != null){
            Builtin.history_r(historyFile.getAbsolutePath(), HIS);
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
            ArrayList<String> ARGS = new ArrayList<>(List.of(cmd.split(" ")));

            if(ARGS.get(0).equals("exit")) {
                break;
                
            } 

            else if(ARGS.get(0).equals("history")) {
                if(ARGS.size() == 1){
                    Builtin.history(null, HIS);
                } else if(ARGS.get(1).equals("-r") && ARGS.size() == 3){
                    Builtin.history_r(ARGS.get(2), HIS);
                }else if(ARGS.get(1).equals("-w") && ARGS.size() == 3){
                    Builtin.history_w(ARGS.get(2), HIS);
                }else if(ARGS.get(1).equals("-a") && ARGS.size() == 3){
                    Builtin.history_a(ARGS.get(2), HIS);
                }else{
                    Builtin.history(ARGS.get(1), HIS);
                }
            }

            else if(ARGS.get(0).equals("pwd")) Builtin.pwd();

            else if(ARGS.get(0).equals("cd")) {
                if(ARGS.size() == 1){
                    Builtin.cd("~");
                } else {
                    Builtin.cd(ARGS.get(1));
                }
            }

            else if(ARGS.get(0).equals("echo")) {
                Builtin.echo(ARGS.toArray(new String[0]));
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
        if (historyFile != null){
            Builtin.history_w(historyFile.getAbsolutePath(), HIS);
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
