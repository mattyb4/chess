import chess.*;
import ui.LoginConsole;
import ui.ServerFacade;

public class Main {
    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);
        var console = new LoginConsole("http://localhost:8080");
        console.run();
    }
}