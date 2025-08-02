import chess.*;
import ui.LoginConsole;
import ui.ServerFacade;

public class Main {
    public static void main(String[] args) {
        System.out.println("♕ 240 Chess Client:");
        var console = new LoginConsole("http://localhost:8080");
        console.run();
    }
}