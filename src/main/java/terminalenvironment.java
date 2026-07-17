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
import parser.CommandCompleter;
import org.jline.terminal.*;
import org.jline.reader.impl.completer.AggregateCompleter;

class terminalEnvironnment {
    public static LineReader setupTerminal() throws Exception {
        Terminal terminal = TerminalBuilder.builder().system(true).build();

        Completer fileCompleter = new FileNameCompleter();
        StringsCompleter customCompleter = new StringsCompleter(Main.BUILT_IN);

        Completer completer = new AggregateCompleter(customCompleter, fileCompleter);

        DefaultParser parser = new DefaultParser();
        parser.setEscapeChars(null);
        LineReader reader = LineReaderBuilder.builder()
            .terminal(terminal)
            .history(new DefaultHistory())
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