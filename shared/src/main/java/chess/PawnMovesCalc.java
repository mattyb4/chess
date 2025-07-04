package chess;

import java.util.ArrayList;
import java.util.Collection;

public class PawnMovesCalc implements PieceMovesCalc {
    @Override
    public Collection<ChessMove> calculateMoves(ChessBoard board, ChessPosition currentPosition) {
        Collection<ChessMove> pawnMoves = new ArrayList<>();
        int row = currentPosition.getRow();
        int col = currentPosition.getColumn();
        int newRow = row;
        int newCol = col;
        var myPiece = board.getPiece(currentPosition);

        //white moves
        if(myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            newRow++;
            var newPosition = new ChessPosition(newRow,newCol);
            var obstructivePiece = board.getPiece(newPosition);
            if(col > 1){
                var capturePositionLeft = new ChessPosition(newRow, col -1);
                if(board.getPiece(capturePositionLeft) != null && board.getPiece(capturePositionLeft).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if(newRow == 8) {
                        promotePawn(pawnMoves,currentPosition,capturePositionLeft);
                    }
                    else {
                        pawnMoves.add(new ChessMove(currentPosition,capturePositionLeft,null));
                    }
                }
            }
            if(col < 8){
                var capturePositionRight = new ChessPosition(newRow, col + 1);
                if(board.getPiece(capturePositionRight) != null && board.getPiece(capturePositionRight).getTeamColor() == ChessGame.TeamColor.BLACK) {
                    if(newRow == 8) {
                        promotePawn(pawnMoves,currentPosition,capturePositionRight);
                    }
                    else {
                        pawnMoves.add(new ChessMove(currentPosition,capturePositionRight,null));
                    }
                }
            }
            if(obstructivePiece == null && newRow != 8) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                if(newRow == 3){
                    newRow++;
                    newPosition = new ChessPosition(newRow,newCol);
                    obstructivePiece = board.getPiece(newPosition);
                    if(obstructivePiece == null) {
                        pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                    }
                }
            }
            if(obstructivePiece == null && newRow == 8) {
                promotePawn(pawnMoves,currentPosition,newPosition);
            }

        }
        //black moves
        if(myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
            newRow--;
            var newPosition = new ChessPosition(newRow,newCol);
            var obstructivePiece = board.getPiece(newPosition);
            if(col > 1){
                var capturePositionLeft = new ChessPosition(newRow, col - 1);
                if(board.getPiece(capturePositionLeft) != null && board.getPiece(capturePositionLeft).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if(newRow == 1) {
                        promotePawn(pawnMoves,currentPosition,capturePositionLeft);
                    }
                    else {
                        pawnMoves.add(new ChessMove(currentPosition,capturePositionLeft,null));
                    }
                }
            }
            if(col < 8){
                var capturePositionRight = new ChessPosition(newRow, col + 1);
                if(board.getPiece(capturePositionRight) != null && board.getPiece(capturePositionRight).getTeamColor() == ChessGame.TeamColor.WHITE) {
                    if(newRow == 1) {
                        promotePawn(pawnMoves,currentPosition,capturePositionRight);
                    }
                    else {
                        pawnMoves.add(new ChessMove(currentPosition,capturePositionRight,null));
                    }
                }
            }
            if(obstructivePiece == null && newRow != 1) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                if(newRow == 6){
                    newRow--;
                    newPosition = new ChessPosition(newRow,newCol);
                    obstructivePiece = board.getPiece(newPosition);
                    if(obstructivePiece == null) {
                        pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
                    }
                }
            }
            if(obstructivePiece == null && newRow == 1) {
                promotePawn(pawnMoves,currentPosition,newPosition);
            }

        }
        return pawnMoves;
    }
    public void promotePawn(Collection<ChessMove> pawnMoves, ChessPosition currentPosition, ChessPosition newPosition) {
        pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.QUEEN));
        pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.KNIGHT));
        pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.BISHOP));
        pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.ROOK));
    }
}
