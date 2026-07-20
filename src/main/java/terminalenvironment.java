package src.main.java;

import org.jline.reader.*;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.impl.completer.StringsCompleter;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.reader.impl.DefaultParser;
import org.jline.reader.Completer;
import org.jline.reader.impl.completer.FileNameCompleter;
import org.jline.terminal.*;
import org.jline.reader.impl.completer.AggregateCompleter;
import org.jline.keymap.KeyMap;
import org.jline.reader.Widget;
import org.jline.reader.Reference;
import org.jline.reader.Parser;
import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

class terminalEnvironnment {

    public static History HIS = new DefaultHistory();

    public static LineReader setupTerminal() throws Exception {
        
        Terminal terminal = TerminalBuilder.builder().system(true).dumb(false).build();

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
                        String fileName = files.get(itr);
                        files.set(itr, fileName.substring(0, fileName.lastIndexOf('.')));
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
        reader.unsetOpt(LineReader.Option.AUTO_LIST);   // List all matching choices on double-tab
        reader.unsetOpt(LineReader.Option.AUTO_MENU);   // Show a menu of choices on double-tab
        reader.unsetOpt(LineReader.Option.INSERT_TAB);  // Disable inserting a tab character on tab key press

        final int[] tabPressCount = {0};
        final String[] lastBuffer = {""};

        Widget customTabWidget = () -> {
            String buffer = reader.getBuffer().toString();
            ParsedLine parsedLine = parser.parse(buffer, buffer.length(), Parser.ParseContext.COMPLETE);
            List<Candidate> candidates = new ArrayList<>();
            completer.complete(reader, parsedLine, candidates);
            candidates = candidates.stream()
                .collect(Collectors.toMap(Candidate::value, c -> c, (a, b) -> a, LinkedHashMap::new))
                .values().stream()
                .sorted(Comparator.comparing(Candidate::value))
                .collect(Collectors.toList());

            if (candidates.isEmpty()) {
                return true;
            }

            if (candidates.size() == 1) {
                String value = candidates.get(0).value();
                reader.getBuffer().write(value.substring(buffer.length()) + " ");
                tabPressCount[0] = 0;
                return true;
            }

            // longest common prefix among candidates
            String commonPrefix = candidates.get(0).value();
            for (Candidate c : candidates) {
                int i = 0;
                while (i < commonPrefix.length() && i < c.value().length()
                    && commonPrefix.charAt(i) == c.value().charAt(i)) {
                    i++;
                }
                commonPrefix = commonPrefix.substring(0, i);
            }

            if (commonPrefix.length() > buffer.length()) {
                reader.getBuffer().write(commonPrefix.substring(buffer.length()));
                tabPressCount[0] = 0;
                return true;
            }

            // truly ambiguous
            if (!buffer.equals(lastBuffer[0])) {
                tabPressCount[0] = 0;
            }
            lastBuffer[0] = buffer;
            tabPressCount[0]++;

            if (tabPressCount[0] == 1) {
                terminal.writer().write(7);
                terminal.writer().flush();
            } else {
                terminal.writer().println();
                for (Candidate c : candidates) {
                    terminal.writer().print(c.value() + "  ");
                }
                terminal.writer().println();
                terminal.writer().flush();
                reader.callWidget(LineReader.REDRAW_LINE);
                tabPressCount[0] = 0;
            }
            return true;
        };
        reader.getWidgets().put("custom-tab", customTabWidget);
        reader.getKeyMaps().get(LineReader.MAIN).bind(new Reference("custom-tab"), KeyMap.ctrl('I')); // Tab

        return reader;
    }
}