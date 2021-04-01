package myChess.engine;

import myChess.piece.Empty;
import myChess.piece.Piece;
import myChess.player.Team;

public class BoardBuilder {
    //todo refactor this class
    Square[][] chessBoard;
    Team playersTurn;

    public BoardBuilder() {
        this.chessBoard = new Square[8][8];
    }

    public BoardBuilder setMove(final Team next) {
        this.playersTurn = next;
        return this;
    }

    public BoardBuilder setPiece(Piece piece) {
        chessBoard[piece.getPieceXPosition()][piece.getPieceYPosition()] = new Square(piece);
        return this;
    }

    public BoardBuilder setNull() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.chessBoard[i][j] == null || this.chessBoard[i][j].getPiece() == null) {
                    this.chessBoard[i][j] = new Square(new Empty(i, j));
                }
            }
        }
        return this;
    }

    public Board build() {
        return new Board(this);
    }
}