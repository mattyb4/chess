package chess;
import java.util.Collection;
import java.util.ArrayList;

import static java.lang.Math.abs;

public class BishopMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        int x = currentPosition.getRow();
        int y = currentPosition.getColumn();
        ArrayList<ChessMove> bishopMoves = new ArrayList<>();
        int newX = x;
        int newY = y;

        //move up and to the right
        while (newX > 0 && newX < 8 && newY > 0 && newY < 8) {
            newX = newX+1;
            newY = newY+1;
            var newPosition = new ChessPosition(newX,newY);
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move up and to the left
        while (newX > 1 && newX <= 8 && newY > 0 && newY < 8) {
            newX = newX - 1;
            newY = newY + 1;
            var newPosition = new ChessPosition(newX,newY);
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move down and to the right
        while (newX > 0 && newX < 8 && newY > 1 && newY <= 8) {
            newX = newX + 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
        }
        newX = x;
        newY = y;

        //move down and to the left
        while (newX > 1 && newX <= 8 && newY > 1 && newY <= 8) {
            newX = newX - 1;
            newY = newY - 1;
            var newPosition = new ChessPosition(newX,newY);
            bishopMoves.add(new ChessMove(currentPosition,newPosition,null));
        }

        //var testPosition = new ChessPosition(2,3);
        //bishopMoves.add(new ChessMove(currentPosition,testPosition,null));
        //var test2 = new ChessPosition(9,9);
        //bishopMoves.add(new ChessMove(currentPosition,test2,null));

        return bishopMoves;
    }
}
