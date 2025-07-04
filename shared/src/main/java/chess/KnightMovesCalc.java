package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();
        ArrayList<ChessMove> knightMoves = new ArrayList<>();
        int newRow = row;
        int newCol = col;
        //move right, up two
        if(newRow < 7 && newCol < 8) {
            newRow = newRow + 2;
            newCol = newCol + 1;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row; //reset to original position
        newCol = col;
        //move up, right two
        if(newRow < 8 && newCol < 7) {
            newRow = newRow + 1;
            newCol = newCol + 2;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move left, up two
        if(newRow < 7 && newCol > 1) {
            newRow = newRow + 2;
            newCol = newCol - 1;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move up, left two
        if(newRow < 8 && newCol > 2) {
            newRow = newRow + 1;
            newCol = newCol - 2;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move down, right two
        if(newRow > 1 && newCol < 7) {
            newRow = newRow - 1;
            newCol = newCol + 2;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move right, down two
        if(newRow > 2 && newCol < 8) {
            newRow = newRow - 2;
            newCol = newCol + 1;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move left, down two
        if(newRow > 2 && newCol > 1) {
            newRow = newRow - 2;
            newCol = newCol - 1;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move down, left two
        if(newRow > 1 && newCol > 2) {
            newRow = newRow - 1;
            newCol = newCol - 2;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKnight(knightMoves,board,currentPosition,newPosition);
        }
        return knightMoves;
    }
    public void moveKnight(Collection<ChessMove> knightMoves, ChessBoard board, ChessPosition currentPosition, ChessPosition newPosition){
        var myPiece = board.getPiece(currentPosition);
        if(board.getPiece(newPosition) != null) {
            if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        else {
            knightMoves.add(new ChessMove(currentPosition, newPosition, null));
        }
    }
}
