package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> queenMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);


        return queenMoves;
    }
}
