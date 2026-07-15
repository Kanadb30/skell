package builtin;

import org.jline.reader.History;
import java.io.*;
import java.time.Instant;

public class history {
    public static void history_w(String filepath, History HIS){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            for (History.Entry cmd : HIS) {
                bw.write(cmd.line());
                bw.newLine();
            }
            HIS.purge();
        } catch (IOException e) {
            System.out.println("Error writing history file: " + e.getMessage());
        }
    }

    public static void history_a(String filepath, History HIS){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            for (History.Entry cmd : HIS) {
                bw.write(cmd.line());
                bw.newLine();
            }
            HIS.purge();
        } catch (IOException e) {
            System.out.println("Error appending to history file: " + e.getMessage());
        }
    }

    public static void history_r(String filePath, History HIS) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                HIS.add(Instant.now(), line);
            }
        } catch (IOException e) {
            System.out.println("Error reading history file: " + e.getMessage());
        }
    }

    public static void history(String n, History HIS) {
        int rows;
        if(n == ""){
            rows = 0;
        } else {
            rows = HIS.size() - Integer.parseInt(n);
        }
        int itr = 0;
        for (History.Entry cmd : HIS) {
            if(rows > 0){
                rows--;
                continue;
            }
            System.out.println(cmd.index()+1 + " " + cmd.line());
        }
    }
}