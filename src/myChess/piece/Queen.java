package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Queen extends Piece {

    public Queen(Team playerType,
                 final int pieceXPosition,
                 final int pieceYPosition,
                 final boolean isFirstMove) {
        super(PieceType.QUEEN, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.QUEEN.toString();
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        //straight
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, 1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, -1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 0, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 0, 8, this));
        //diagonals
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, -1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, -1, 8, this));

        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Queen(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }
}
