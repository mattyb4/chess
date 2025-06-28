package chess;
import java.util.Collection;
import java.util.ArrayList;


public class BishopMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        int newX = x;
        int newY = y;
        var myPiece = board.getPiece(currentPosition);

        //move up and to the right
        while (newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX+1;
            newY = newY+1;
            var newPosition = new ChessPosition(newX,newY);
            if(board.getPiece(newPosition) != null) {//check for obstacle piece
                if(board.getPiece(newPosition).getTeamColor() == myPiece.getTeamColor()){
                    break;//stop short of own piece
                }
            }
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;//capture piece
        }
        newX = x; //reset to original position
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
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
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
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
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
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
            if(board.getPiece(newPosition) != null) break;
        }

        return bishopMoves;
    }
}
