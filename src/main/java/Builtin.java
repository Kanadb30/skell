import java.io.*;
import java.util.*;

public class Builtin {
    public static void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    public static void history() {
        for (String cmd : Main.HIS) {
            System.out.println(cmd);
        }
    }

    public static void echo(String[] ARGS) {
        System.out.println(String.join(" ", Arrays.copyOfRange(ARGS, 1, ARGS.length)));
    }

    public static void cd(String path) {
        if (path.equals("~")) {
            System.setProperty("user.dir", System.getenv("HOME"));
        } else {
            File newDir = new File(path);
            if (newDir.isAbsolute() && newDir.isDirectory()) {
                System.setProperty("user.dir", newDir.getAbsolutePath());
            } else {
                String[] pathSegments = path.split("/");
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
}