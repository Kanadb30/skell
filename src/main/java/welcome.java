
package src.main.java;

public class welcome {

    private static final String RESET = "\u001B[0m";

    // Kali's signature blue tones
    private static final String LOGO_COLOR = "\u001B[1;38;5;33m";   // bold deep blue
    private static final String TEXT_COLOR = "\u001B[38;5;39m";     // lighter blue/cyan
    private static final String DIM_COLOR  = "\u001B[38;5;245m";    // muted grey

    private static final char PIXEL = '█';

    // 7-row x 5-col block font (higher detail than a flat hash grid)
    private static final String[] S = {
        " ████", "█    ", "█    ", " ███ ", "    █", "    █", "████ "
    };
    private static final String[] K = {
        "█   █", "█  █ ", "█ █  ", "██   ", "█ █  ", "█  █ ", "█   █"
    };
    private static final String[] E = {
        "█████", "█    ", "█    ", "████ ", "█    ", "█    ", "█████"
    };
    private static final String[] L = {
        "█    ", "█    ", "█    ", "█    ", "█    ", "█    ", "█████"
    };

    private static final String[] LOGO = buildLogo();

    private static String[] buildLogo() {
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

        for (String line : LOGO) {
            System.out.println(LOGO_COLOR + line + RESET);
        }

        System.out.println();
        System.out.println(TEXT_COLOR + "a custom shell for the terminal-native" + RESET);
        System.out.println();
        System.out.println(DIM_COLOR + "  type 'help' to get started" + RESET);
        System.out.println();
    }
}