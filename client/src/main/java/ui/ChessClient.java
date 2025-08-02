package ui;

import model.UserData;
import ui.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String userName = null;
    private State state = State.SIGNEDOUT;
    private String authToken;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public enum State {
        SIGNEDOUT,
        SIGNEDIN
    }

    public String eval(String input) {
        try {
            var tokens = input.toLowerCase().split(" ");
            var cmd = (tokens.length > 0) ? tokens[0] : "help";
            var params = Arrays.copyOfRange(tokens, 1, tokens.length);
            if (state == State.SIGNEDOUT) {
                return switch (cmd) {
                    case "login" -> login(params);
                    case "register" -> register(params);
                    case "quit" -> "quit";
                    default -> helpPrompt();
                };
            }
            else {
                return switch (cmd) {
                    case "logout" -> logout();
                    case "create" -> createGame(params);
                    case "list" -> listGames();
                    case "join" -> joinGame(params);
                    case "observe" -> observeGame(params);
                    case "quit" -> "quit";
                    default -> helpPrompt();
                };
            }
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws ResponseException {
        System.out.println("logging in... ");
        var userData = new UserData(params[0],params[1],"");
        var authData = server.login(userData);
        authToken = authData.authToken();
        state = State.SIGNEDIN;
        return "Successfully logged in";
    }

    public String helpPrompt() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
            System.out.println("login <USERNAME> <PASSWORD> - to log in");
            System.out.println("quit - quit program");
            System.out.println("help - list possible commands");
        }
        else {
            System.out.println("create <NAME> - create game with given name");
            System.out.println("list - list all current games");
            System.out.println("join <ID> [WHITE|BLACK] - join a game with given ID on given team");
            System.out.println("observer <ID> - observe game with given ID");
            System.out.println("logout - logout of program");
            System.out.println("quit - quit program");
            System.out.println("help - list possible commands");
        }
        return "What would you like to do?";
    }

    public String register(String... params) throws ResponseException {
        System.out.println("registering user...");
        var userData = new UserData(params[0],params[1],params[2]);
        var authData = server.register(userData);
        authToken = authData.authToken();
        state = State.SIGNEDIN;
        return "You are registered and logged in";
    }

    public String logout() throws ResponseException {
        System.out.println("logging out...");
        server.logout(authToken);
        state = State.SIGNEDOUT;
        return "Successfully logged out";
    }

    public String createGame(String... params) throws ResponseException {
        return "not implemented yet";
    }

    public String listGames() throws ResponseException {
        return "not implemented yet";
    }

    public String joinGame(String... params) throws ResponseException {
        return "not implemented yet";
    }

    public String observeGame(String... params) throws ResponseException {
        return "not implemented yet";
    }

}
