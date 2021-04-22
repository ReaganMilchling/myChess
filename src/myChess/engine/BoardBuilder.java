package myChess.engine;

import myChess.piece.Empty;
import myChess.piece.Piece;
import myChess.player.Team;

import java.util.Collection;

public class BoardBuilder {

    private Square[][] chessBoard;
    private Team playersTurn;

    public BoardBuilder() {
        this.chessBoard = new Square[8][8];
    }

    public Board build() {
        return new Board(this);
    }

    //getters
    public Square[][] getChessBoard() {
        return this.chessBoard;
    }

    public Team getPlayersTurn() {
        return this.playersTurn;
    }

    //setters
    public void setNextPlayerTurn(final Team next) {
        this.playersTurn = next;
    }

    public void setBoardUnchanged(Piece changedPiece, Collection<Piece> playerPieces, Collection<Piece> opponentPieces) {

        for (final Piece piece : playerPieces) {
            if (!changedPiece.equals(piece)) {
                this.setPiece(piece);
            }
        }
        for (final Piece piece : opponentPieces) {
            this.setPiece(piece);
        }

    }

    public void setNull() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (this.chessBoard[i][j] == null || this.chessBoard[i][j].getPiece() == null) {
                    this.chessBoard[i][j] = new Square(new Empty(i, j));
                }
            }
        }
    }

    public BoardBuilder setPiece(Piece piece) {
        chessBoard[piece.getPieceXPosition()][piece.getPieceYPosition()] = new Square(piece);
        return this;
    }
}