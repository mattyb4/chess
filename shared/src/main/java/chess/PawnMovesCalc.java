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
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition,null));
            }
        }
        newX = x;
        newY = y;

        //black move forward standard
        if(newX > 0 && newX != 7 && newX != 2 && newX < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK){
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

        //white pawn queen promotion
        if(newX == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.QUEEN));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.QUEEN));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.QUEEN));
            }
        }
        newX = x;
        newY = y;

        //white pawn bishop promotion
        if(newX == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.BISHOP));
            }
        }
        newX = x;
        newY = y;

        //white pawn knight promotion
        if(newX == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.KNIGHT));
            }
        }
        newX = x;
        newY = y;

        //white pawn rook promotion
        if(newX == 7 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            newX = newX + 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x+1,newY-1);
            var testPositionRight = new ChessPosition(x+1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.ROOK));
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
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.QUEEN));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.QUEEN));
            }
        }
        newX = x;
        newY = y;

        //black pawn bishop promotion
        if(newX == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.BISHOP));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.BISHOP));
            }
        }
        newX = x;
        newY = y;

        //black pawn knight promotion
        if(newX == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.KNIGHT));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.KNIGHT));
            }
        }
        newX = x;
        newY = y;

        //black pawn rook promotion
        if(newX == 2 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            newX = newX - 1;
            var newPosition = new ChessPosition(newX,newY);
            var testPositionLeft = new ChessPosition(x-1,newY-1);
            var testPositionRight = new ChessPosition(x-1,newY+1);
            if(newY>1) {
                if (board.getPiece(testPositionLeft) != null) {
                    if (board.getPiece(testPositionLeft).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionLeft, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(newY<8) {
                if (board.getPiece(testPositionRight) != null) {
                    if (board.getPiece(testPositionRight).getTeamColor() != myPiece.getTeamColor()) {
                        pawnMoves.add(new ChessMove(currentPosition, testPositionRight, ChessPiece.PieceType.ROOK));
                    }
                }
            }
            if(board.getPiece(newPosition) == null) {
                pawnMoves.add(new ChessMove(currentPosition,newPosition, ChessPiece.PieceType.ROOK));
            }
        }
        newX = x;
        newY = y;

        //white pawn capture
        if(newX > 0 && newX != 2 && newX != 7 && newX < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
            var testPositionLeft = new ChessPosition(newX+1,newY-1);
            var testPositionRight = new ChessPosition(newX+1,newY+1);
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
        }
        newX = x;
        newY = y;

        //black pawn capture
        if(newX > 0 && newX != 7 && newX != 2 && newX < 8 && myPiece.getTeamColor() == ChessGame.TeamColor.BLACK) {
            var testPositionLeft = new ChessPosition(newX-1,newY-1);
            var testPositionRight = new ChessPosition(newX-1,newY+1);
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
        }


        return pawnMoves;
    }
}
