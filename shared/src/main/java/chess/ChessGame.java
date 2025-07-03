package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private TeamColor teamTurn;
    private ChessBoard board;
    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //get the piece type at startPosition, then call move calc for that type. Don't add anything that turns isInCheck() true
        ChessPiece piece = board.getPiece(startPosition);
        var potentialMoves = piece.pieceMoves(board, startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();
        for (var move : potentialMoves){
            var movingPiece = board.getPiece(move.getStartPosition());
            var capturedPiece = board.getPiece(move.getEndPosition());

            board.addPiece(move.getEndPosition(),movingPiece);
            board.addPiece(move.getStartPosition(),null);

            boolean inCheck = isInCheck(teamTurn);
            System.out.println(move);
            isInCheck(teamTurn);
            board.addPiece(move.getStartPosition(), movingPiece);
            board.addPiece(move.getEndPosition(),capturedPiece);

            if(!inCheck){
                validMoves.add(move);
                System.out.println("not in check. added valid move");
            }
            else {
                System.out.println("in check. invalid move. Did not add");
            }
        }
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        var movingPiece = board.getPiece(startPosition);

        board.addPiece(endPosition,movingPiece);
        board.addPiece(startPosition,null);

        if(getTeamTurn() == TeamColor.WHITE) {
            setTeamTurn(TeamColor.BLACK);
        }
        else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        //true if possible endPosition of any piece includes King's current position
        //find position of King
        var kingPosition = new ChessPosition(1,1);
        for(int row = 1; row <= 8; row++){
            for(int col = 1; col <= 8; col++) {
                var currentPiece = board.getPiece(new ChessPosition(row,col));
                if(currentPiece != null) {
                    if (currentPiece.getTeamColor() == teamColor && currentPiece.getPieceType() == ChessPiece.PieceType.KING) {
                        kingPosition = new ChessPosition(row, col);
                        System.out.println(kingPosition);
                        break;
                    }
                }
            }
        }
        //Check for any endPositions that match up with the King's current position
        for(int row = 1; row <= 8; row++) {
            for(int col = 1; col <= 8; col++) {
                var currentPosition = new ChessPosition(row,col);
                var enemyPiece = board.getPiece(currentPosition);
                if(enemyPiece != null && enemyPiece.getTeamColor() != teamColor) {
                    for (var possibleMoves : enemyPiece.pieceMoves(board, currentPosition)) {
                        if (possibleMoves.getEndPosition().equals(kingPosition)) {
                            System.out.println(enemyPiece);
                            return true;
                        }
                    }
                }

            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        //isInCheck is true and validMoves is null for every position on the board
        boolean noValidMoves = true;
        System.out.println("checkmate not implemented yet");
        //set up for loop to check every position for valid moves and set noValidMoves to false if any are found
        return isInCheck(teamColor) && noValidMoves;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //isInCheck is false and validMoves is null for every position on the board
        boolean noValidMoves = true;
        System.out.println("Stalemate not implemented yet");
        //set up for loop to check every position for valid moves and set noValidMoves to false if any are found
        return !isInCheck(teamColor) && noValidMoves;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }
}
