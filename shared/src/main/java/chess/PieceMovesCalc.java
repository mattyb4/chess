package chess;
import java.util.Collection;
import java.util.ArrayList;

public interface PieceMovesCalc {
    static Collection<ChessMove> calcMoves(ChessBoard board, ChessPosition startPosition, ArrayList<ChessMove> calcedMoves) {
        Collection<ChessMove> moves = new ArrayList<>();
        for (ChessMove move : calcedMoves) {
            //System.out.println("in for loop");
            //System.out.println(move);
            ChessPosition start = move.getStartPosition();
            ChessPosition end = move.getEndPosition();
            ChessPiece.PieceType promotion = move.getPromotionPiece();
            moves.add(new ChessMove(start,end,promotion));
        }
        //var possiblePosition = new ChessPosition(1,8);
        //moves.add(new ChessMove(startPosition,possiblePosition,null));
        return moves;
    }
}
