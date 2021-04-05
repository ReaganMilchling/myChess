package myChess.engine;

import myChess.piece.Piece;

public class Square {

    protected final Piece piece;

    public Square(final Piece piece) {
        this.piece = piece;
    }

    //getters
    public Piece getPiece() {
        return this.piece;
    }
    public boolean isOccupied() {
        return !this.piece.getPieceType().isEmpty();
    }

    @Override
    public String toString() {
        if (this.getPiece().getPlayerTeam().isBlack()) {
            return getPiece().toString().toLowerCase();
        } else {
            return getPiece().toString();
        }
    }
}
