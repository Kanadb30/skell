package builtin;

import custom.cmd;
import java.util.ArrayList;
import org.jline.reader.History;

public class execute{
    public static void execute(custom.cmd parsedCmd, History HIS){
        switch(parsedCmd.cmd){
            case "break":
            case "exit":
                break;
            case "echo":
                echo.echo(parsedCmd.Args.toArray(new String[0]));
                break;
            case "pwd":
                pwd.pwd();
                break;
            case "cd":
                cd.cd(parsedCmd);
                break;
            case "declare":
                if(parsedCmd.flag == 'p' && parsedCmd.Args.size() == 1){
                    declare.declare_p(parsedCmd.Args.get(0));
                } else if(parsedCmd.Args.size() == 1 && parsedCmd.Args.get(0).contains("=")){
                    declare.declare(parsedCmd.Args.get(0));
                } else {
                    System.out.println("declare: invalid usage");
                }
                break;
            case "history":
                boolean hasUserArg = parsedCmd.Args.size() > 0 && !parsedCmd.Args.get(0).equals(parsedCmd.cmd);
                switch(parsedCmd.flag){
                    case 'r':
                        if(hasUserArg){
                            history.history_r(parsedCmd.Args.get(0), HIS);
                        }
                        break;
                    case 'w':
                        if(hasUserArg){
                            history.history_w(parsedCmd.Args.get(0), HIS);
                        }
                        break;
                    case 'a':
                        if(hasUserArg){
                            history.history_a(parsedCmd.Args.get(0), HIS);
                        }
                        break;
                    default:
                        if(!hasUserArg){
                            history.history("0", HIS);
                        } else {
                            history.history(parsedCmd.Args.get(0), HIS);
                        }
                }
            default:
                //System.out.println("Command not found: " + parsedCmd.cmd);
                break;
        }
    }
}