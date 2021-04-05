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

        builder.setBoardUnchanged(this.movedPiece,
                                  this.board.getCurrentPlayer().getPieces(),
                                  this.board.getCurrentPlayer().getOpponent().getPieces());

        if (this.getDestinationYPos() == 0 || this.getDestinationYPos() == 7) {
            builder.setPiece(this.movedPiece.movePiece(this, true));
        } else {
            builder.setPiece(this.movedPiece.movePiece(this));
        }
        builder.setNextPlayerTurn(this.board.getCurrentPlayer().getOpponent().getTeam());
        builder.setNull();
        return builder.build();
    }

    public abstract String toString();
    public abstract boolean equals(Object obj);
    public abstract boolean isAttacking();

    public static class NormalMove extends Move {

        public NormalMove(final Board board, int x, int y, Piece piece) {
            super(board, x, y, piece);
        }

        @Override
        public String toString() {
            return movedPiece.toString() + (8 - destinationYPos) + Utils.convertToFile(destinationXPos);
        }
        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Move)) {
                return false;
            }
            final Move otherMove = (Move) other;
            return this.getDestinationYPos() == otherMove.getDestinationYPos() &&
                    this.getDestinationXPos() == otherMove.getDestinationXPos() &&
                    this.getMovedPiece().equals(otherMove.getMovedPiece());
        }

        @Override
        public boolean isAttacking() {
            return false;
        }
    }

    public static class AttackMove extends Move {

        protected final Piece attackedPiece;

        public AttackMove(final Board board, int x, int y, Piece piece, Piece attackedPiece) {
            super(board, x, y, piece);
            this.attackedPiece = attackedPiece;
        }

        public Piece getAttackedPiece() {
            return this.attackedPiece;
        }

        @Override
        public String toString() {
            return movedPiece.toString() + "x" + (8 - destinationYPos) + Utils.convertToFile(destinationXPos);
        }

        @Override
        public boolean equals(final Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Move)) {
                return false;
            }
            final Move otherMove = (Move) other;
            return this.getDestinationYPos() == otherMove.getDestinationYPos() &&
                    this.getDestinationXPos() == otherMove.getDestinationXPos() &&
                    this.getMovedPiece().equals(otherMove.getMovedPiece());
        }

        @Override
        public boolean isAttacking() {
            return true;
        }
    }

}
