package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> pawnMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);



        //white move forward standard
        if(newX > 0 && newX != 2 && newX < 8 && newY > 0 && newY < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        newX = x;
        newY = y;

        //black move forward standard
        if(newX > 0 && newX != 7 && newX < 8 && newY > 0 && newY < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        newX = x;
        newY = y;

        //white move forward from starting position (can move up to two spaces)
        if(newX == 2 && newY > 0 && newY < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                //check if next space is also free
                newX = newX + 1;
                newPosition = new ChessPosition(newX,newY);
                if(board.getPiece(newPosition) == null) {
                    pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
        }
        newX = x;
        newY = y;

        //black move forward from starting position (can move up to two spaces)
        if(newX == 7 && newY > 0 && newY < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                //check if next space is also free
                newX = newX - 1;
                newPosition = new ChessPosition(newX,newY);
                if(board.getPiece(newPosition) == null) {
                    pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
        }
        newX = x;
        newY = y;

        return pawnMoves;
    }
}
