package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.player.Team;

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

    //abstract classes
    public abstract Collection<Move> calculateMoves(final Board board);
    public abstract Piece movePiece(Move move);
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
        PAWN(1, "p", '\u265F', '\u2659'){
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

            @Override
            public boolean isPawn() {
                return true;
            }
        },
        ROOK(5, "r", '\u265C', '\u2656') {
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

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        BISHOP(3, "b", '\u265D', '\u2657') {
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

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        KNIGHT(3, "n", '\u265E', '\u2658') {
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

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        QUEEN(9, "q", '\u265B', '\u2655') {
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

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        KING(1000, "k", '\u265A', '\u2654') {
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

            @Override
            public boolean isPawn() {
                return false;
            }
        },
        EMPTY(0, "e", '0', '0') {
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

            @Override
            public boolean isPawn() {
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
        public abstract boolean isPawn();
    }
}
