package src.main.java;

import org.jline.reader.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.Completer;
// import org.jline.reader.impl.completer.ArgumentCompleter;
import org.jline.reader.impl.completer.FileNameCompleter;
import org.jline.terminal.*;
import org.jline.reader.impl.completer.AggregateCompleter;
import java.util.*;
import java.io.*;

class terminalEnvironnment {

    public static History HIS = new DefaultHistory();

    public static LineReader setupTerminal() throws Exception {
        
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        Completer fileCompleter = new FileNameCompleter();
        ArrayList<String> cmdsToComplete = new ArrayList<>(Main.BUILT_IN);

        String path = System.getenv("PATH");
        ArrayList<String> PATH_DIRS = new ArrayList<>(List.of(path.split(File.pathSeparator)));
        for(String dir : PATH_DIRS){
            File folder = new File(dir);
            if(folder.isDirectory()){
                ArrayList<String> files = new ArrayList<>(List.of(folder.list()));
                for(int itr = 0;itr < files.size();itr++){
                    if(files.get(itr).contains(".")){
                        files.set(itr, files.get(itr).reverse().substring(files.get(itr).indexOf(".")+1).reverse());
                    }
                }
                cmdsToComplete.addAll(files);
            }
        }

        StringsCompleter customCompleter = new StringsCompleter(cmdsToComplete);

        Completer completer = new AggregateCompleter(customCompleter, fileCompleter);

        DefaultParser parser = new DefaultParser();
        parser.setEscapeChars(null);
        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .history(HIS)
            .parser(parser)
            .completer(completer)
            .build();
        reader.unsetOpt(LineReader.Option.HISTORY_IGNORE_DUPS);
        reader.setOpt(LineReader.Option.AUTO_LIST);   // List all matching choices on double-tab
        reader.setOpt(LineReader.Option.MENU_COMPLETE); // Cycle through options with the tab key
        reader.unsetOpt(LineReader.Option.INSERT_TAB);  // Disable inserting a tab character on tab key press
        return reader;
    }
}