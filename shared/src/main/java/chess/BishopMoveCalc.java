package chess;

import java.util.ArrayList;
import java.util.List;

public class BishopMoveCalc implements MoveCalculator {
    @Override
    public List<ChessMove> calcMoves(ChessBoard board, ChessPosition position) {
        List<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{1,1},{1,-1}, {-1,1}, {-1,-1}}; //will move the piece diagonally

        ChessPiece piece = board.getPiece(position);
        if (piece == null) {
            return moves;
        }

        int row = position.getRow();
        int col = position.getColumn();

        for (int[] dir : directions) {
            int newRow = row;
            int newCol = col;

            while (true) {
                newRow += dir[0];
                newCol += dir[1];

                if(newRow > 8 || newRow < 1 || newCol > 8 || newCol < 1) {
                    //check if piece reached the edge of board
                    break;
                }

                ChessPosition newPosition = new ChessPosition(newRow, newCol);
                ChessPiece occupiedSquare = board.getPiece(newPosition);

                if(occupiedSquare == null) {
                    moves.add(new ChessMove(position, newPosition, null)); //valid move
                } else {
                    break; //stop at first occupied square
                }
            }
        }
        return moves;
    }
}
