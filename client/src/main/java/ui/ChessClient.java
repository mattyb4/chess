package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import model.JoinRequest;
import model.UserData;
import ui.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String userName = null;
    private State state = State.SIGNEDOUT;
    private String authToken;
    private ChessGame game;

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
                    case "help" -> helpPrompt();
                    default -> "Invalid input - type 'help' to see list of valid inputs";
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
                    case "help" -> helpPrompt();
                    default -> "Invalid input - type 'help' to see list of valid inputs";
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
        System.out.println("Successfully logged in as " + userData.username());
        System.out.println();
        return helpPrompt();
    }

    public String helpPrompt() throws ResponseException {
        if (state == State.SIGNEDOUT) {
            System.out.println("Here is a list of available commands:");
            System.out.println("register <USERNAME> <PASSWORD> <EMAIL> - to create an account");
            System.out.println("login <USERNAME> <PASSWORD> - to log in");
            System.out.println("quit - quit program");
            System.out.println("help - list possible commands");
        }
        else {
            System.out.println("Here is a list of newly available commands:");
            System.out.println("create <NAME> - create game with given name");
            System.out.println("list - list all current games");
            System.out.println("join <ID> [WHITE|BLACK] - join a game with given ID on given team");
            System.out.println("observe <ID> - observe game with given ID");
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
        System.out.println("You are registered and logged in as " + userData.username());
        System.out.println();
        return helpPrompt();
    }

    public String logout() throws ResponseException {
        System.out.println("logging out...");
        server.logout(authToken);
        state = State.SIGNEDOUT;
        System.out.println("Successfully logged out");
        System.out.println();
        return helpPrompt();
    }

    public String createGame(String... params) throws ResponseException {
        System.out.println("Creating game...");
        var gameData = server.create(params[0],authToken);
        return "Successfully created game called " + gameData.gameName();
    }

    public String listGames() throws ResponseException {
        System.out.println("Here is a list of all active games: ");
        var gameList = server.listAllGames(authToken);
        return "games: " + gameList;
    }

    public String joinGame(String... params) throws ResponseException {
        System.out.println("Joining game... ");
        int gameID = Integer.parseInt(params[0]);
        var request = new JoinRequest(params[1].toUpperCase(),gameID);
        System.out.println("join request is" + request);
        server.join(request,authToken);

        var gameData = server.getGame(gameID, authToken);
        System.out.println("Printing board from White perspective");
        printBoard(gameData.game(),true);
        System.out.println("Printing board from Black perspective");
        printBoard(gameData.game(),false);
        return "Successfully joined game " + gameID;
    }

    public String observeGame(String... params) throws ResponseException {
        System.out.println("Joining game as observer...");
        int gameID = Integer.parseInt(params[0]);
        var gameData = server.getGame(gameID, authToken);
        printBoard(gameData.game(),true);
        return "Now observing game " + gameID;
    }

    public void printBoard(ChessGame game, boolean whitePerspective) {
        var board = game.getBoard();

        int[] rows = whitePerspective ? new int[]{8,7,6,5,4,3,2,1} : new int[]{1,2,3,4,5,6,7,8};
        int[] cols = whitePerspective ? new int[]{1,2,3,4,5,6,7,8} : new int[]{8,7,6,5,4,3,2,1};
        printColumnLabels(cols);
        for (int row : rows) {
            System.out.print(" " + row + " ");
            for (int col : cols) {
                ChessPosition currentPos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPos);

                String squareColor = (row + col) % 2 != 0 ? EscapeSequences.SET_BG_COLOR_DARK_GREY : EscapeSequences.SET_BG_COLOR_LIGHT_GREY;
                String pieceString = EscapeSequences.EMPTY;
                if (piece != null) {
                    pieceString = getPieceSymbol(piece);
                }
                System.out.print(squareColor + pieceString);
            }
            System.out.print(EscapeSequences.RESET_BG_COLOR + " " + row + "\n");
        }
        printColumnLabels(cols);
    }

    public String getPieceSymbol(ChessPiece piece) {
        boolean isWhite = piece.getTeamColor() == ChessGame.TeamColor.WHITE;

        return switch (piece.getPieceType()) {
            case KING -> isWhite ? EscapeSequences.WHITE_KING : EscapeSequences.BLACK_KING;
            case QUEEN -> isWhite ? EscapeSequences.WHITE_QUEEN : EscapeSequences.BLACK_QUEEN;
            case BISHOP -> isWhite ? EscapeSequences.WHITE_BISHOP : EscapeSequences.BLACK_BISHOP;
            case KNIGHT -> isWhite ? EscapeSequences.WHITE_KNIGHT : EscapeSequences.BLACK_KNIGHT;
            case ROOK -> isWhite ? EscapeSequences.WHITE_ROOK : EscapeSequences.BLACK_ROOK;
            case PAWN -> isWhite ? EscapeSequences.WHITE_PAWN : EscapeSequences.BLACK_PAWN;
        };
    }

    public void printColumnLabels(int[] cols) {
        System.out.print(" ");
        for (int col : cols) {
            char label = (char) ('a' + col - 1);
            System.out.print(EscapeSequences.EMPTY.charAt(0) + " " + label + " ");
        }
        System.out.println();
    }

}
