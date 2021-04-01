package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rook extends Piece {

    public Rook(Team playerType,
                final int pieceXPosition,
                final int pieceYPosition,
                final boolean isFirstMove) {
        super(PieceType.ROOK, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.ROOK.toString();
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, 1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, -1, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 0, 8, this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 0, 8, this));

        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Rook(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }

    @Override
    public Piece movePiece(Move move, boolean isPromote) {
        return new Rook(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }
}
