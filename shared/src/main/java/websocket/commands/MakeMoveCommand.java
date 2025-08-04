package websocket.commands;

import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand{
    private ChessMove move;

    public MakeMoveCommand(ChessMove move, String authToken, Integer gameID) {
        super(CommandType.MAKE_MOVE, authToken, gameID);
        this.move = move;
    }
}
