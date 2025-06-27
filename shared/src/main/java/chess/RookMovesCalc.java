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

        while (newX < 8) {
            newX++;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving up");
            System.out.println(newX);
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                  break;
                }
                rookMoves.add(new ChessMove(currentPosition,newPosition,null));
                break;
            }
            rookMoves.add(new ChessMove(currentPosition,newPosition,null));

        }

        /*for (int i = newX + 1; i<8; i++){
            var newPosition = new ChessPosition(i+1,newY+1);
            System.out.println("checking space");
            ChessPiece piece = board.getPiece(newPosition);
            System.out.println("moving up");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
            if (piece != null) {
                if(piece.getTeamColor() != myPiece.getTeamColor()) {
                    rookMoves.add(new ChessMove(currentPosition, newPosition, null));
                }
                break;
            }
            rookMoves.add(new ChessMove(currentPosition, newPosition, null));
        }*/
        newX = x;
        newY = y;

        //move left
        while (newY > 1) {
            newY--;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving left");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
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
        while (newY < 8) {
            newY++;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving right");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
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
        while (newX > 1) {
            newX = newX-1;
            var newPosition = new ChessPosition(newX,newY);
            System.out.println("moving down");
            System.out.println(new ChessMove(currentPosition,newPosition,null));
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
