import java.util.Scanner;
import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage\
        HashSet<String> built_in = new HashSet<>();
        built_in.add("echo");
        built_in.add("exit");

        while(true) {
            System.out.print("$ ");
	        Scanner sc = new Scanner(System.in);
	        String cmd = sc.nextLine();
            if(cmd.equals("exit")) {
                break;
            } else if(cmd.startsWith("echo ")) {
                System.out.println(cmd.substring(5));
            } else if (cmd.startsWith("type ")) {
                String cmp_cmd = cmd.substring(5);
                if(built_in.contains(cmp_cmd)){
                    System.out.println(cmp_cmd + " is a shell builtin");
                }else{
                    System.out.println(cmp_cmd +": not found");
                }
            } 
            
            else {
                System.out.println(cmd + ": command not found");
            }

	        
        }
        
    }
}
