package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KnightMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> knightMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);


        //move right, up two
        while(newX > 0 && newX < 7 && newY > 0 && newY < 8) {
            newX = newX + 2;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving right, up two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move up, right two
        while(newX > 0 && newX < 8 && newY > 0 && newY < 7) {
            newX = newX + 1;
            newY = newY + 2;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving up, right two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move left, up two
        while(newX > 0 && newX < 7 && newY > 0 && newY < 8) {
            newX = newX + 2;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving left, up two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move up, left two
        while(newX > 0 && newX < 8 && newY > 1 && newY < 8) {
            newX = newX + 1;
            newY = newY - 2;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving up, left two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down, right two
        while(newX > 0 && newX < 8 && newY > 0 && newY < 7) {
            newX = newX - 1;
            newY = newY + 2;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving down, right two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move right, down two
        while(newX > 1 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX - 2;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving right, down two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move left, down two
        while(newX > 1 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX - 2;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving left, down two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;

        //move down, left two
        while(newX > 0 && newX < 8 && newY > 1 && newY < 8) {
            newX = newX - 1;
            newY = newY - 2;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving down, left two");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            knightMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }
        newX = x;
        newY = y;



        return knightMoves;
    }
}
