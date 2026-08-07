/**
 * Welcome banner for sKell.
 * Strictly two-tone Kali-blue theme (no gradient): a bold blue for the
 * frame and wordmark, a lighter blue for the mountain logo and subtext.
 *
 * Wire this into your shell's startup (e.g. call SKellWelcome.printWelcome()
 * right after your REPL/JLine terminal is initialized, before the first prompt).
 */

package src.main.java;

public class welcome {

    private static final String RESET = "\u001B[0m";

    // Kali's signature blue, two shades only
    private static final String PRIMARY   = "\u001B[1;38;5;33m"; // bold deep blue - frame + wordmark
    private static final String SECONDARY = "\u001B[38;5;39m";   // lighter blue  - mountain + subtext

    // Wavy chain-link border, split into a top and bottom half
    private static final String[] TOP_BORDER = {
        "   _.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._.-=-._",
        ".-'---      - ---     --     ---   -----   - --       ----  ----   -     ---`-."
    };

    private static final String[] BOTTOM_BORDER = {
        "(___       _       _       _       _       _       _       _       _       ___)",
        "    `-._.-' (___ _) `-._.-' `-._.-' )     ( `-._.-' `-._.-' (__ _ ) `-._.-'",
        "            ( _ __)                (_     _)                (_ ___)",
        "            (__  _)                 `-._.-'                 (___ _)",
        "            `-._.-'                                         `-._.-'"
    };

    // Mountain-peak logo
    private static final String[] LOGO = {
        "                      .                              ",
        "                     /:`.                            ",
        "              _     /!:  `.                          ",
        "             : \"\"-./!!!\\   `.                        ",
        "            :     /!!!!!:    `.                      ",
        "            :    /!!!!!!:      `.                    ",
        "            :   /!!!!!!!!\\       `.                  ",
        "           :   /!!!!!!!!!!:       :\"\"-._             ",
        "           :  /!!!!!!!!!!!:       :     \"\"-._        ",
        "           : /!!!!!!!!!!!!!\\     :           \"\"-.    ",
        "           :/!!!!!!!!!!!!!!!:    :               ``..",
        "           /!!!!!!!!!!!!!!!!:   :              _.-@@/",
        "          /!!!!!!!!!!!!!!!!!!\\  :          _.-@@@@@/ ",
        "         :  `\"-!!!!!!!!!!!!!!!: :      _.-@@@@@@@@/  ",
        "        /:         `\"-!!!!!!!!: :  _.-@@@@@@@@@@@/   ",
        "       /!:               `\"-!!!\\.-@@@@@@@@@@@@@@/    ",
        "      /!:                  _.-@/:`@@@@@@@@@@@@@/     ",
        "     /!!:              _.-@@@@/!:  `@@@@@@@@@@/      ",
        "    /!!!:          _.-@@@@@@@/!!!\\.  `@@@@@@@/       ",
        "   /!!!:       _.-@@@@@@@@@@/!!!!!:    `@@@@/        ",
        "  /!!!!:   _.-@@@@@@@@@@@@@/!!!!!!:      `@/         ",
        " /!!!!!:.-@@@@@@@@@@@@@@@@/!!!!!!!!\\       :         ",
        "/!!!!!!!\"-@@@@@@@@@@@@@@@/!!!!!!!!!!:      :         ",
        "`\"-!!!!!!!!\"-@@@@@@@@@@@/!!!!!!!!!!!:     :          ",
        "      `\"-!!!!!!\"-@@@@@@/!!!!!!!!!!!!!\\    :          ",
        "            `\"-!!!\"-@@/!!!!!!!!!!!!!!!:   :          ",
        "                  `\"\"/!!!!!!!!!!!!!!!!:  :           ",
        "                      `-@\"-!!!!!!!!!!!!\\ :           ",
        "                         `-@@@`\"-!!!!!!!::           ",
        "                            `-@@@@/ `\"-!:            ",
        "                               `-\"        "
    };

    // Hand-built 7x5 block font for the SKELL wordmark
    private static final String[] S = {" ████", "█    ", "█    ", " ███ ", "    █", "    █", "████ "};
    private static final String[] K = {"█   █", "█  █ ", "█ █  ", "██   ", "█ █  ", "█  █ ", "█   █"};
    private static final String[] E = {"█████", "█    ", "█    ", "████ ", "█    ", "█    ", "█████"};
    private static final String[] L = {"█    ", "█    ", "█    ", "█    ", "█    ", "█    ", "█████"};

    private static final String[] WORDMARK = buildWordmark();

    private static String[] buildWordmark() {
        String[] rows = new String[7];
        for (int r = 0; r < 7; r++) {
            rows[r] = S[r] + "  " + K[r] + "  " + E[r] + "  " + L[r] + "  " + L[r];
        }
        return rows;
    }

    public static void main(String[] args) {
        printWelcome();
    }

    public static void printWelcome() {
        System.out.println();

        for (String line : TOP_BORDER) {
            System.out.println(PRIMARY + line + RESET);
        }

        System.out.println();
        printLogoWithWordmark();

        System.out.println();
        System.out.println(SECONDARY + "a custom shell for the terminal-native" + RESET);
        System.out.println(SECONDARY + "type 'help' to get started" + RESET);
        System.out.println();

        for (String line : BOTTOM_BORDER) {
            System.out.println(PRIMARY + line + RESET);
        }

        System.out.println();
    }

    // Prints the mountain logo with the SKELL wordmark placed to its right,
    // vertically centered against the logo's height.
    private static void printLogoWithWordmark() {
        int logoWidth = 0;
        for (String line : LOGO) {
            logoWidth = Math.max(logoWidth, line.length());
        }

        int startRow = Math.max(0, (LOGO.length - WORDMARK.length) / 2);

        for (int row = 0; row < LOGO.length; row++) {
            String logoLine = LOGO[row];
            StringBuilder sb = new StringBuilder();

            sb.append(SECONDARY).append(logoLine);
            for (int p = logoLine.length(); p < logoWidth; p++) {
                sb.append(' ');
            }
            sb.append(RESET);

            int wRow = row - startRow;
            if (wRow >= 0 && wRow < WORDMARK.length) {
                sb.append("    ").append(PRIMARY).append(WORDMARK[wRow]).append(RESET);
            }

            System.out.println(sb);
        }
    }
}