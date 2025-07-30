package ui;

import ui.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String userName = null;
    private State state = State.SIGNEDOUT;

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
            if (state == State.SIGNEDIN) {
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
        return null;
    }

    public String login(String... params) throws ResponseException {
        if(params.length >= 1) {
            state = State.SIGNEDIN;
            userName = String.join("-", params);
            return String.format("You signed in as %s.", userName);
        }
        throw new ResponseException(400, "Expected: <yourname>");
    }

    public String helpPrompt() throws ResponseException {
        //if state is signed out
        return "help prompt stuff";
        //if state is signed in - other stuff
    }

    public String register(String... params) throws ResponseException {
        return "not implemented yet";
    }

    public String logout() throws ResponseException {
        return "not implemented yet";
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
