package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    public King(Team playerType,
                final int pieceXPosition,
                final int pieceYPosition,
                final boolean isFirstMove) {
        super(PieceType.KING, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public Collection<Move> calculateMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        //straight
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 0,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 0,this));
        //diagonals
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, -1,this));

        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new King(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }

    @Override
    public Piece movePiece(Move move, boolean isPromote) {
        return new King(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }
}
