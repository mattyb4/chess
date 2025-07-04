package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();
        ArrayList<ChessMove> rookMoves = new ArrayList<>();
        int newRow = row;
        int newCol = col;
        var myPiece = board.getPiece(currentPosition);
        //move up
        while (newRow < 8) {
            newRow++;
            var newPosition = new ChessPosition(newRow, newCol);
            //check if piece hits another piece
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                  break; //stop short of hitting own piece
                }
                rookMoves.add(new ChessMove(currentPosition,newPosition,null));
                break; //capture opposing piece
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        //reset position to starting position
        newRow = row;
        newCol = col;
        //move left
        while (newCol > 1) {
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;
            }
        }
        newRow = row;
        newCol = col;
        //move right
        while (newCol < 8) {
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;
            }
        }
        newRow = row;
        newCol = col;
        //move down
        while (newRow > 1) {
            newRow--;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;
            }
        }
        return rookMoves;
    }
}
