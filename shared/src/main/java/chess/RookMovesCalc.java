package chess;

import java.util.ArrayList;
import java.util.Collection;

public class RookMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> rookMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);

        //move up
        while (newX < 8) {
            newX++;
            var newPosition = new ChessPosition(newX,newY);
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
        newX = x;
        newY = y;

        //move left
        while (newY > 1) {
            newY--;
            var newPosition = new ChessPosition(newX,newY);
            //check if piece hits another piece
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break; //stop short of hitting own piece
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break; //capture opposing piece

        }
        newX = x; //reset position to starting position
        newY = y;

        //move right
        while (newY < 8) {
            newY++;
            var newPosition = new ChessPosition(newX,newY);
            //check if piece hits another piece
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break; //stop short of hitting own piece
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break; //capture opposing piece
        }
        newX = x; //reset position to starting position
        newY = y;

        //move down
        while (newX > 1) {
            newX = newX-1;
            var newPosition = new ChessPosition(newX,newY);
            //check if piece hits another piece
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break; //stop short of hitting own piece
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break; //capture opposing piece

        }

        return rookMoves;
    }
}
