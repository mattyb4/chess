package chess;

import java.util.ArrayList;
import java.util.Collection;

public class QueenMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> queenMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);

        //Combining Bishop and Rook movement calcs

        //bishop calcs
        //move up and to the right
        while (newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX+1;
            newY = newY+1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break; //stop short of own piece
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break; //capture piece
        }
        newX = x;//reset to original position
        newY = y;

        //move up and to the left
        while (newX > 1 && newX <= 8 && newY > 0 && newY < 8) {
            newX = newX - 1;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down and to the right
        while (newX > 0 && newX < 8 && newY > 1 && newY <= 8) {
            newX = newX + 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down and to the left
        while (newX > 1 && newX <= 8 && newY > 1 && newY <= 8) {
            newX = newX - 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;


        //rook calcs
        //move up
        while (newX < 8) {
            newX++;
            var newPosition = new ChessPosition(newX,newY);
            //check if piece hits another piece
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break; //stop short of hitting own piece
                }
                queenMoves.add(new ChessMove(currentPosition,newPosition,null));
                break; //capture opposing piece
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));

        }
        //reset position to starting position
        newX = x;
        newY = y;

        //move left
        while (newY > 1) {
            newY--;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;

        }
        newX = x;
        newY = y;

        //move right
        while (newY < 8) {
            newY++;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down
        while (newX > 1) {
            newX = newX-1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            queenMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }

        return queenMoves;
    }
}
