package custom;

import java.util.ArrayList;

public class cmd{
    public String cmd;
    public boolean isBuiltin;
    public char flag;
    public ArrayList<String> Args;
    public boolean hasPath;
    public boolean isPathAbsolute;

    public cmd(String cmd, boolean isBuiltin, char flag, ArrayList<String> Args, boolean hasPath, boolean isPathAbsolute){
        this.cmd = cmd;
        this.isBuiltin = isBuiltin;
        this.flag = flag;
        this.Args = Args;
        this.hasPath = hasPath;
        this.isPathAbsolute = isPathAbsolute;
    }

    public cmd(String cmd, boolean isBuiltin, ArrayList<String> Args, boolean hasPath, boolean isPathAbsolute){
        this.cmd = cmd;
        this.isBuiltin = isBuiltin;
        this.Args = Args;
        this.hasPath = hasPath;
        this.isPathAbsolute = isPathAbsolute;
    }
}