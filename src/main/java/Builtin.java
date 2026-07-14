import java.io.*;
import java.util.*;

public class Builtin {
    public static void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    // Add item manually: reader.getHistory().add(Instant.now(), "my-command");
    // Clear all history: reader.getHistory().purge();

    public static void history(String n, LineReader reader) {
        int rows;
        if(n == null){
            rows = 0;
        } else {
            rows = reader.getHistory().size() - Integer.parseInt(n);
        }
        int itr = 0;
        for (History.Entry cmd : reader.getHistory()) {
            if(rows > 0){
                rows--;
                continue;
            }
            System.out.println(cmd.index() + " " + cmd);
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