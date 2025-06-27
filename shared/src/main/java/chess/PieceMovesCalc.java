package chess;
import java.util.Collection;
import java.util.ArrayList;

public interface PieceMovesCalc {

    Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition startPosition);

}
