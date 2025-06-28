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
        if(newX > 0 && newX != 2 && newX != 7 && newX < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {//check for capturable piece on left
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, null));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {//check for capturable piece on right
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, null));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {//check that the space in front is empty
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        newX = x; //reset to original position
        newY = y;


        //black move forward standard
        if(newX > 0 && newX != 7 && newX != 2 && newX < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, null));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, null));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        newX = x;
        newY = y;

        //white move forward from starting position (can move up to two spaces)
        if(newX == 2 &&  myPiece.getTeamColor() == ChessGame.TeamColor.WHITE){ //make sure pawn is in starting row
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, null));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, null));
                    }
                }
            }
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
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, null));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, null));
                    }
                }
            }
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

        //white pawn promotion
        if(newX == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) { //check pawn is able to move to promotion space
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        //Account for all promotion options
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition, newPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(currentPosition, newPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(currentPosition, newPosition, ChessPiece.PieceType.KNIGHT));
                pawnMoves.add(new ChessMove(currentPosition, newPosition, ChessPiece.PieceType.ROOK));
            }
        }
        newX = x;
        newY = y;


        //black pawn queen promotion
        if(newX == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.QUEEN));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.BISHOP));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.KNIGHT));
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.QUEEN));
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.BISHOP));
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.KNIGHT));
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.ROOK));
            }
        }


        return pawnMoves;
    }
}
