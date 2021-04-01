package myChess.engine;

import myChess.piece.Piece;

public abstract class Move {

    protected final Board board;
    protected final int destinationXPos;
    protected final int destinationYPos;
    protected final Piece movedPiece;

    public Move(final Board board,
                final int x,
                final int y,
                final Piece piece) {
        this.board = board;
        this.destinationXPos = x;
        this.destinationYPos = y;
        this.movedPiece = piece;
    }

    //getters
    public int getCurrentXPos(){
        return this.movedPiece.getPieceXPosition();
    }
    public int getCurrentYPos() {
        return this.movedPiece.getPieceYPosition();
    }
    public int getDestinationXPos() {
        return this.destinationXPos;
    }
    public int getDestinationYPos() {
        return this.destinationYPos;
    }
    public Piece getMovedPiece() {
        return this.movedPiece;
    }

    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        for (final Piece piece : this.board.getCurrentPlayer().getPieces()) {
            if (!this.movedPiece.equals(piece)) {
                builder.setPiece(piece);
            }
        }
        for (final Piece piece : this.board.getCurrentPlayer().getOpponent().getPieces()) {
            builder.setPiece(piece);
        }
        if (this.getDestinationYPos() == 0 || this.getDestinationYPos() == 7) {
            builder.setPiece(this.movedPiece.movePiece(this, true));
        } else {
            builder.setPiece(this.movedPiece.movePiece(this));
        }
        builder.setMove(this.board.getCurrentPlayer().getOpponent().getTeam());
        builder.setNull();
        return builder.build();
    }

    public abstract String toString();

    public static class NullMove extends Move {

        public NullMove() {
            super(null, -1, -1, null);
        }

        @Override
        public String toString() {
            return "f";
        }
    }

    public static class NormalMove extends Move {

        public NormalMove(final Board board, int x, int y, Piece piece) {
            super(board, x, y, piece);
        }

        @Override
        public String toString() {
            return movedPiece.toString() + (8 - destinationYPos) + Utils.convertToFile(destinationXPos);
        }
    }

    public static class AttackMove extends Move {

        protected final Piece attackedPiece;

        public AttackMove(final Board board, int x, int y, Piece piece, Piece attackedPiece) {
            super(board, x, y, piece);
            this.attackedPiece = attackedPiece;
        }

        @Override
        public String toString() {
            return movedPiece.toString() + "x" + (8 - destinationYPos) + Utils.convertToFile(destinationXPos);
        }
    }

}
