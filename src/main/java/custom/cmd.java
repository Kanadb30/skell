package custom;

import java.util.ArrayList;

public class cmd{
    String cmd;
    boolean isBuiltin;
    char flag;
    ArrayList<String> Args;
    boolean hasPath;
    boolean isPathAbosulte;

    public cmd(String cmd, boolean isBuiltin, char flag, ArrayList<String> Args, boolean hasPath, boolean isPathAbosulte){
        this.cmd = cmd;
        this.isBuiltin = isBuiltin;
        this.flag = flag;
        this.Args = Args;
        this.hasPath = hasPath;
        this.isPathAbosulte = isPathAbosulte;
    }

    public cmd(String cmd, boolean isBuiltin, ArrayList<String> Args, boolean hasPath, boolean isPathAbosulte){
        this.cmd = cmd;
        this.isBuiltin = isBuiltin;
        this.Args = Args;
        this.hasPath = hasPath;
        this.isPathAbosulte = isPathAbosulte;
    }
}