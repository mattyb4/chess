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

        //move straight up
        while (newY > 0 && newY < 8 && newX > 0 && newX < 8) {
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
                rookMoves.add(new ChessMove(currentPosition,newPosition,null));
                break;
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));
            newX = newX+1;


        }
        newX = x;
        newY = y;

        //move left
        while (newY > 0 && newY < 8 && newX > 0 && newX <= 8) {
            newY = newY-1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));

            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move right
        while (newY > 0 && newY < 7 && newX > 0 && newX <= 8) {
            newY = newY+1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));

            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down
        while (newY > 0 && newY <= 8 && newX > 1 && newX < 8) {
            newX = newX-1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));

            if(board.getPiece(newPosition) != null) break;
        }

        return rookMoves;
    }
}
