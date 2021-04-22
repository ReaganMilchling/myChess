package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;

import java.util.Collection;
import java.util.Collections;

public class Empty extends Piece {

    public Empty(int pieceXPosition,
                 int pieceYPosition) {
        super(PieceType.EMPTY, null, pieceXPosition, pieceYPosition, false);
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        return Collections.emptyList();
    }

    @Override
    public Piece movePiece(Move move) {
        return null;
    }

    @Override
    public String toString() {
        return "-";
    }
}
