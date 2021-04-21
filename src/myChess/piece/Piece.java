package myChess.piece;

import myChess.player.Team;
import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;

import java.util.Collection;

public abstract class Piece {

    protected final PieceType pieceType;
    protected final Team playerTeam;
    protected final int pieceXPosition;
    protected final int pieceYPosition;
    protected final boolean isFirstMove;

    Piece(final PieceType pieceType,
          final Team playerType,
          final int pieceXPosition,
          final int pieceYPosition,
          final boolean isFirstMove) {
        this.pieceType = pieceType;
        this.playerTeam = playerType;
        this.pieceXPosition = pieceXPosition;
        this.pieceYPosition = pieceYPosition;
        this.isFirstMove = isFirstMove;
    }

    //getters
    public int getPieceXPosition() {
        return this.pieceXPosition;
    }
    public int getPieceYPosition() {
        return this.pieceYPosition;
    }
    public boolean isFirstMove() {
        return this.isFirstMove;
    }
    public Team getPlayerTeam() {
        return this.playerTeam;
    }
    public PieceType getPieceType() {
        return this.pieceType;
    }
    public String toStringPos() {
        return PieceType.PAWN.toString() + (8 - this.pieceYPosition) + Utils.convertToFile(this.pieceXPosition);
    }
    //abstract classes
    public abstract Collection<Move> calculateMoves(final Board board);
    public abstract Piece movePiece(Move move);
    public abstract Piece movePiece(Move move, boolean isPromote);
    public abstract String toString();

    @Override
    public boolean equals(final Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof Piece)) {
            return false;
        }
        final Piece otherPiece = (Piece) other;
        return this.getPieceXPosition() == otherPiece.getPieceXPosition() &&
               this.getPieceXPosition() ==  otherPiece.getPieceYPosition() &&
               this.getPieceType() == otherPiece.getPieceType() &&
               this.getPlayerTeam() == otherPiece.getPlayerTeam() &&
               this.isFirstMove() == otherPiece.isFirstMove();
    }

    public enum PieceType {
        PAWN(1, "P", '\u265F', '\u2659'){
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        ROOK(5, "R", '\u265C', '\u2656') {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return true;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        BISHOP(3, "B", '\u265D', '\u2657') {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KNIGHT(3, "N", '\u265E', '\u2658') {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        QUEEN(9, "Q", '\u265B', '\u2655') {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        },
        KING(1000, "K", '\u265A', '\u2654') {
            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return true;
            }
        },
        EMPTY(0, "E", '0', '0') {
            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public boolean isRook() {
                return false;
            }

            @Override
            public boolean isKing() {
                return false;
            }
        };

        private final int pieceValue;
        private final String pieceName;
        private final char blackChar;
        private final char whiteChar;

        PieceType(int i, String b, char blackChar, char whiteChar) {
            this.pieceValue = i;
            this.pieceName = b;
            this.blackChar = blackChar;
            this.whiteChar = whiteChar;
        }

        @Override
        public String toString() {
            return this.pieceName;
        }
        public int getPieceValue() {
            return this.pieceValue;
        }
        public char getBlackChar() {
            return this.blackChar;
        }
        public char getWhiteChar() {
            return this.whiteChar;
        }

        public abstract boolean isEmpty();
        public abstract boolean isRook();
        public abstract boolean isKing();
    }
}
