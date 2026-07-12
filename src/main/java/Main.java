import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage\
        while(true) {
            System.out.print("$ ");
	        Scanner sc = new Scanner(System.in);
	        String cmd = sc.nextLine();
            if(cmd.equals("exit")) {
                break;
            }
	        System.out.println(cmd + ": command not found");
        }
        
    }
}
