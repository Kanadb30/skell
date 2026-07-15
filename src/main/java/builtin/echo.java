package builtin;

import java.util.Arrays;

public class echo {
    public static void echo(String[] ARGS) {
        System.out.println(String.join(" ", Arrays.copyOfRange(ARGS, 1, ARGS.length)));
    }
}