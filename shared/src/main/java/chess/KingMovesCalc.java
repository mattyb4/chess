package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();
        ArrayList<ChessMove> kingMoves = new ArrayList<>();
        int newRow = row;
        int newCol = col;
        //move up
        if(newRow < 8) {
            newRow++;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newRow = row; //reset values to original coordinates
        //move up right
        if(newRow < 8 && newCol < 8) {
            newRow++;
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move up left
        if(newRow < 8 && newCol > 1) {
            newRow++;
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move right
        if(newCol < 8) {
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newCol = col;
        //move left
        if(newCol > 1) {
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newCol = col;
        //move down
        if(newRow > 1) {
            newRow--;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        //move down right
        if(newRow > 1 && newCol < 8) {
            newRow--;
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        newRow = row;
        newCol = col;
        //move down left
        if(newRow > 1 && newCol > 1) {
            newRow--;
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            moveKing(kingMoves,board,currentPosition,newPosition);
        }
        return kingMoves;
    }
    public void moveKing(Collection<ChessMove> kingMoves,ChessBoard board, ChessPosition currentPosition, ChessPosition newPosition) {
        var myPiece = board.getPiece(currentPosition);
        if(board.getPiece(newPosition) != null) {
            if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                kingMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        else {
            kingMoves.add(new ChessMove(currentPosition, newPosition, null));
        }
    }
}
