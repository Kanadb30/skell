package builtin;

import custom.cmd;
import java.util.ArrayList;
import org.jline.reader.History;

public class execute{
    public void execute(custom.cmd parsedCmd, History HIS){
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
                switch(parsedCmd.flag){
                    case 'r':
                        history.history_r(parsedCmd.Args.get(0), HIS);
                        break;
                    case 'w':
                        history.history_w(parsedCmd.Args.get(0), HIS);
                        break;
                    case 'a':
                        history.history_a(parsedCmd.Args.get(0), HIS);
                        break;
                    default:
                        if(parsedCmd.Args.size() == 0){
                            history.history(null, HIS);
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