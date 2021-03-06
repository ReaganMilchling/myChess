package myChess.engine;

import myChess.piece.Empty;
import myChess.piece.Piece;
import myChess.piece.Rook;

import java.util.Collection;

public abstract class Move {

    protected final Board board;
    protected final int destinationXPos;
    protected final int destinationYPos;
    protected final Piece movedPiece;
    //todo check for checks here

    public Move(final Board board,
                final int x,
                final int y,
                final Piece piece) {
        this.board = board;
        this.destinationXPos = x;
        this.destinationYPos = y;
        this.movedPiece = piece;
    }

    public Board testForChecks(Collection<Piece> currentPlayer, Collection<Piece> opponent) {
        //this function returns a copy of the board class, with the move made
        //this allows for an easy check if the moved piece will leave the king in check
        final BoardBuilder builder = new BoardBuilder();
        builder.setBoardUnchanged(this.movedPiece, currentPlayer, opponent);

        if (!(this instanceof AttackMove)){
            builder.setPiece(this.movedPiece.movePiece(this));
        } else {
            AttackMove m = (AttackMove) this;
            if (!m.getAttackedPiece().getPieceType().isKing()) {
                builder.setPiece(this.movedPiece.movePiece(this));
            }
        }

        builder.setNextPlayerTurn(movedPiece.getPlayerTeam());
        builder.setNull();

        return new Board(builder, movedPiece.getPlayerTeam().isWhite());
    }

    public Board execute() {
        final BoardBuilder builder = new BoardBuilder();
        builder.setBoardUnchanged(this.movedPiece,
                this.board.getCurrentPlayer().getPieces(),
                this.board.getCurrentPlayer().getOpponent().getPieces());
        builder.setPiece(this.movedPiece.movePiece(this));
        builder.setNextPlayerTurn(this.board.getCurrentPlayer().getOpponent().getTeam());
        builder.setNull();

        if (this.isCastle()) {
            //castling logic
            //resets rook on corner to empty tile and sets the rook correctly
            if (this.getDestinationXPos() == 2) {
                builder.setPiece(new Empty(0, this.getCurrentYPos()));
                builder.setPiece(new Rook(this.getMovedPiece().getPlayerTeam(), 3, this.getCurrentYPos(), false));
            } else {
                builder.setPiece(new Empty(7, this.getCurrentYPos()));
                builder.setPiece(new Rook(this.getMovedPiece().getPlayerTeam(), 5, this.getCurrentYPos(), false));
            }
        }
        builder.setMove(this.toString());
        return builder.build();
    }

    //getters
    public Board getBoard() {
        return this.board;
    }

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

    //abstract classes
    public abstract String toString();
    public abstract boolean equals(Object obj);
    public abstract boolean isCastle();

    //subclasses
    public static class NormalMove extends Move {

        public NormalMove(final Board board, int x, int y, Piece piece) {
            super(board, x, y, piece);
        }

        @Override
        public String toString() {
            String p = this.getMovedPiece().toString();
            if (this.movedPiece.getPieceType().isPawn()) {
                return Utils.convertToFile(destinationXPos) + (8 - destinationYPos);
            }
            return p + Utils.convertToFile(destinationXPos) + (8 - destinationYPos);
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
        public boolean isCastle() {
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
            String p = this.getMovedPiece().toString();

            if (this.movedPiece.getPieceType().isPawn()) {
                return "x" + Utils.convertToFile(destinationXPos) + (8 - destinationYPos);
            }
            return p + "x" + Utils.convertToFile(destinationXPos) + (8 - destinationYPos);
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
        public boolean isCastle() {
            return false;
        }
    }

    public static class CastleMove extends Move {

        protected final Piece movedRook;

        public CastleMove(Board board, int x, int y, Piece king, Piece rook) {
            super(board, x, y, king);
            this.movedRook = rook;
        }

        @Override
        public String toString() {
            if (this.destinationXPos == 6) {
                return "0-0";
            } else {
                return "0-0-0";
            }
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
            if (otherMove.isCastle()) {
                return this.getDestinationYPos() == otherMove.getDestinationYPos() &&
                        this.getDestinationXPos() == otherMove.getDestinationXPos() &&
                        this.getMovedPiece().equals(otherMove.getMovedPiece());
            }
            return false;
        }

        @Override
        public boolean isCastle() {
            return true;
        }
    }
}
