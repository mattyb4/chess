package chess;

import java.util.List;

public interface MoveCalculator {
    List<ChessMove> calcMoves(ChessBoard board, ChessPiece piece);
}
