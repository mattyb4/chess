package ui;

import java.util.Scanner;

public class LoginConsole {

    public void run() {
        System.out.println("Welcome to Chess. Type 'help' to get started.");

        var scanner = new Scanner(System.in);
        var result = "";
        while(!result.equals("quit")) {
            String line = scanner.nextLine();
        }

    }
}
