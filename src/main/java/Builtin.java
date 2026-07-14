import java.io.*;
import java.util.*;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.terminal.*;
import java.time.Instant;

public class Builtin {
    public static void pwd() {
        System.out.println(System.getProperty("user.dir"));
    }

    // Add item manually: reader.getHistory().add(Instant.now(), "my-command");
    // Clear all history: reader.getHistory().purge();

    public static void history_w(String filepath, LineReader.History HIS){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for (LineReader.History.Entry cmd : HIS) {
                bw.write(cmd.line());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing history file: " + e.getMessage());
        }
    }

    public static void history_r(String filePath, LineReader.History HIS) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                HIS.add(Instant.now(), line);
            }
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
    }

    public static void history(String n, LineReader.History HIS) {
        int rows;
        if(n == null){
            rows = 0;
        } else {
            rows = HIS.size() - Integer.parseInt(n);
        }
        int itr = 0;
        for (LineReader.History.Entry cmd : HIS) {
            if(rows > 0){
                rows--;
                continue;
            }
            System.out.println(cmd.index()+1 + " " + cmd.line());
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