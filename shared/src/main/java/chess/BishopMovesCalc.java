package chess;
import java.util.Collection;
import java.util.ArrayList;


public class BishopMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        int newRow = row;
        int newCol = col;
        var myPiece = board.getPiece(currentPosition);
        //move up and to the right
        while (newRow < 8 && newCol < 8) {
            newRow++;
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {//check for obstacle piece
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;//stop short of own piece
                }
            }
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;//capture piece
            }
        }
        newRow = row; //reset to original position
        newCol = col;
        //move down and to the right
        while (newRow > 1 && newCol < 8) {
            newRow--;
            newCol++;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;//capture piece
            }
        }
        newRow = row;
        newCol = col;
        //move up and to the left
        while (newRow < 8 && newCol > 1) {
            newRow++;
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;//capture piece
            }
        }
        newRow = row;
        newCol = col;
        //move down and to the left
        while (newRow > 1 && newCol > 1) {
            newRow--;
            newCol--;
            var newPosition = new ChessPosition(newRow, newCol);
            if(board.getPiece(newPosition) != null) {
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;
                }
            }
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) {
                break;//capture piece
            }
        }
        return bishopMoves;
    }
}
