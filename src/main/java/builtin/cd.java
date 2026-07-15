package builtin;

import java.io.*;
import java.util.ArrayList;
import custom.cmd;

public class cd{

    public static void cd(custom.cmd parsedCmd) {
        ArrayList<String> path = parsedCmd.Args;
        if(path.size() == 0 || (path.size() == 1 && path.get(0).equals("~"))) {
            System.setProperty("user.dir", System.getenv("HOME"));
        }else if(path.size() == 1 && parsedCmd.isPathAbsolute && new File(path.get(0)).isDirectory()) {
            System.setProperty("user.dir", path.get(0));
        }else if(path.size() == 1){
            String[] pathSegments = path.get(0).split("/");
            String currentDir = System.getProperty("user.dir");
            for (String segment : pathSegments) {
                if (segment.equals("..")) {
                    File parentDir = new File(currentDir).getParentFile();
                    if (parentDir != null) {
                        currentDir = parentDir.getAbsolutePath();
                    } else {
                        System.out.println("cd: " + path + ": No such file or directory");
                        break;
                    }
                } else if (segment.equals(".")) {
                    continue;
                } else {
                    File f = new File(currentDir, segment);
                    if (f.exists() && f.isDirectory()) {
                        currentDir = f.getAbsolutePath();
                    } else {
                        System.out.println("cd: " + path + ": No such file or directory");
                        break;
                    }
                }
            }
            System.setProperty("user.dir", currentDir);
        }
    }
}