package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> kingMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);

        //move up
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null)); //capture piece
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null)); //stop short of piece
        }
        newX = x; //reset values to original coordinates
        newY = y;

        //move up right
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX + 1;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move up left
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX + 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move right
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move left
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move down
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move down right
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX - 1;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move down left
        if(newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX - 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() != myPiece.getTeamColor()){
                    kingMoves.add(new ChessMove(currentPosition,newPosition,null));
                }
            }
            else kingMoves.add(new ChessMove(currentPosition,newPosition,null));
        }

        return kingMoves;
    }
}
