package ui;

import java.util.Scanner;
import static ui.EscapeSequences.*;

public class LoginConsole {
    private final ChessClient client;

    public LoginConsole(String serverUrl) {
        client = new ChessClient(serverUrl);
    }
    public void run() {
        System.out.println("Welcome to Chess. Type 'help' to get started.");

        var scanner = new Scanner(System.in);
        var result = "";
        while(!result.equals("quit")) {//keep program going until quit command is used
            String line = scanner.nextLine();
            try {
                result = client.eval(line);
                System.out.println(result);
            } catch (Throwable e) {
                System.out.println(e);
            }
        }
        System.out.println();
    }
}
