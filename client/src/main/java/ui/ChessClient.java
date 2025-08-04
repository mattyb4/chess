package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import model.GameData;
import model.GameSumm;
import model.JoinRequest;
import model.UserData;
import ui.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChessClient {
    private final ServerFacade server;
    private final String serverUrl;
    private String userName = null;
    private State state = State.SIGNEDOUT;
    private String authToken;
    private ChessGame game;
    private List<GameSumm> gameList = new ArrayList<>();
    private boolean isWhite;
    private ChessGame currentGame;

    public ChessClient(String serverUrl) {
        server = new ServerFacade(serverUrl);
        this.serverUrl = serverUrl;
    }

    public enum State {
        SIGNEDOUT,
        SIGNEDIN,
        GAMEPLAY
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
            else if (state == State.SIGNEDIN) {
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
            else { //state is presumably GAMEPLAY
                return switch (cmd) {
                    case "redraw" -> printBoard(currentGame,isWhite);
                    case "leave" -> leaveGame();
                    case "move" -> makeMove(params);
                    case "resign" -> resignGame();
                    case "highlight" -> possibleMoves(params);
                    case "help" -> helpPrompt();
                    default -> "Invalid input - type 'help' to see list of valid inputs";
                };
            }
        } catch (ResponseException ex) {
            return ex.getMessage();
        }
    }

    public String login(String... params) throws ResponseException {
        if (params.length != 2) {
            return "Error: invalid inputs. Format must be 'login <USERNAME> <PASSWORD>'";
        }
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
        else if (state == State.SIGNEDIN){
            System.out.println("Here is a list of newly available commands:");
            System.out.println("create <NAME> - create game with given name");
            System.out.println("list - list all current games");
            System.out.println("join <ID> [WHITE|BLACK] - join a game with given ID on given team");
            System.out.println("observe <ID> - observe game with given ID");
            System.out.println("logout - logout of program");
            System.out.println("quit - quit program");
            System.out.println("help - list possible commands");
        }
        else { //state is presumably GAMEPLAY
            System.out.println("Here is a list of gameplay commands:");
            System.out.println("redraw - redraws chessboard");
            System.out.println("leave - leaves game");
            System.out.println("move - input what move you want to make");
            System.out.println("resign - forfeit game");
            System.out.println("highlight - highlights legal moves for given piece");
            System.out.println("help - list possible commands");
        }
        return "What would you like to do?";
    }

    public String register(String... params) throws ResponseException {
        if (params.length != 3) {
            return "Error: invalid inputs. Format must be 'register <USERNAME> <PASSWORD> <EMAIL>'";
        }
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
        if (params.length < 1) {
            return "Error: no game name specified";
        }
        if (params.length > 1) {
            return "Error: too many inputs";
        }
        System.out.println("Creating game...");
        var gameData = server.create(params[0],authToken);
        gameList = new ArrayList<>(server.listAllGames(authToken));
        return "Successfully created game called " + gameData.gameName();
    }

    public String listGames() throws ResponseException {
        gameList = new ArrayList<>(server.listAllGames(authToken));
        StringBuilder output = new StringBuilder("Here is a list of all active games:\n");

        //this block of code reformats the JSON GameSumm list to look normal
        int i = 1;
        for (GameSumm game : gameList) {
            output.append(String.format("Game %d - Name: %s | White: %s | Black: %s%n",
                    i++, game.gameName(),
                    game.whiteUsername() != null ? game.whiteUsername() : "<no active player>",
                    game.blackUsername() != null ? game.blackUsername() : "<no active player>"
            ));
        }

        return output.toString();
    }

    public String joinGame(String... params) throws ResponseException {
        if (params.length != 2) {
            return "Error: invalid inputs. Format must be 'join <ID> [WHITE|BLACK]'";
        }
        System.out.println("Joining game... ");

        int index;
        try {
            index = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Error: invalid game ID. Please enter a number.";
        }
        if (index < 1 || index > gameList.size()) {
            return "Error: no game number " + index + ". Use 'list' to see valid games.";
        }
        //get gameID from that game on the list
        int gameID = gameList.get(index - 1).gameID();
        String teamColor = params[1].toUpperCase();

        var request = new JoinRequest(teamColor,gameID);
        server.join(request,authToken);
        var gameData = server.getGame(gameID, authToken);
        currentGame = gameData.game();
        //print board from your team's perspective
        isWhite = teamColor.equals("WHITE");
        printBoard(currentGame,isWhite);
        state = State.GAMEPLAY;

        System.out.println("Successfully joined game " + gameID + " as " + teamColor);
        System.out.println();
        return helpPrompt();
    }

    public String observeGame(String... params) throws ResponseException {
        if (params.length < 1) {
            return "Error: no game ID specified";
        }
        if (params.length > 1) {
            return "Error: too many inputs";
        }
        System.out.println("Joining game as observer...");
        int gameID;
        try {
            gameID = Integer.parseInt(params[0]);
        } catch (NumberFormatException e) {
            return "Error: invalid game ID. Please enter a number.";
        }
        var gameData = server.getGame(gameID, authToken);
        printBoard(gameData.game(),true); //will always observe from White's perspective
        state = State.GAMEPLAY;
        return "Now observing game " + gameID + " from White perspective";
    }

    public String printBoard(ChessGame game, boolean whitePerspective) {
        var board = game.getBoard();
        //if the player is White team, print row numbers in descending order - otherwise print the other way
        int[] rows = whitePerspective ? new int[]{8,7,6,5,4,3,2,1} : new int[]{1,2,3,4,5,6,7,8};
        int[] cols = whitePerspective ? new int[]{1,2,3,4,5,6,7,8} : new int[]{8,7,6,5,4,3,2,1};
        //print the column letters
        printColumnLabels(cols);
        for (int row : rows) {
            System.out.print(" " + row + " ");
            for (int col : cols) {
                ChessPosition currentPos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currentPos);

                String squareColor = (row + col) % 2 != 0 ? EscapeSequences.SET_BG_COLOR_LIGHT_GREY : EscapeSequences.SET_BG_COLOR_DARK_GREY;
                String pieceString = EscapeSequences.EMPTY;
                if (piece != null) {
                    pieceString = getPieceSymbol(piece);
                }
                System.out.print(squareColor + pieceString);
            }
            System.out.print(EscapeSequences.RESET_BG_COLOR + " " + row + "\n");
        }
        printColumnLabels(cols); //print the column letters on the bottom of the board
        return "current game state";
    }

    //created this method to simplify placing proper pieces on the board from EscapeSequences
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

    //created this method to help more consistently place column letter labels on the board
    public void printColumnLabels(int[] cols) {
        System.out.print(" ");
        for (int col : cols) {
            char label = (char) ('a' + col - 1);
            System.out.print(EscapeSequences.EMPTY.charAt(0) + " " + label + " ");
        }
        System.out.println();
    }

    //GAMEPLAY methods

    public String redrawBoard() {
        return "redraw not implemented";
    }

    public String leaveGame() {
        state = State.SIGNEDIN;
        return "leaveGame not implemented. Returned to SIGNEDIN state.";
    }

    public String makeMove(String... params) {
        return "makeMove not implemented";
    }

    public String resignGame() {
        return "resign not implemented.";
    }

    public String possibleMoves(String... params) {
        return "highlight not implemented";
    }
}
