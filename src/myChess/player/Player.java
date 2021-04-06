package myChess.player;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.piece.Piece;

import java.util.Collection;

public abstract class Player {

    protected final Board board;
    protected final Collection<Move> legalMoves;
    protected final Collection<Move> opponentLegalMoves;
    protected final Collection<Move> checkMoves;
    protected final Collection<Move> opponentCheckMoves;

    public Player(final Board board,
                  final Collection<Move> moves,
                  final Collection<Move> oppMoves) {
        this.board = board;
        this.legalMoves = moves;
        this.opponentLegalMoves = oppMoves;
        this.checkMoves = null;
        this.opponentCheckMoves = null;
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public abstract Player getOpponent();
    public abstract Team getTeam();
    public abstract Collection<Piece> getPieces();


    public static class WhitePlayer extends Player{

        public WhitePlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves) {
            super(board, moves, oppMoves);
        }

        @Override
        public Player getOpponent() {
            return this.board.getBlackPlayer();
        }

        @Override
        public Team getTeam() {
            return Team.WHITE;
        }

        @Override
        public Collection<Piece> getPieces() {
            return this.board.getWhitePlayerPieces();
        }
    }

    public static class BlackPlayer extends Player {

        public BlackPlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves) {
            super(board, moves, oppMoves);
        }

        @Override
        public Player getOpponent() {
            return this.board.getWhitePlayer();
        }

        @Override
        public Team getTeam() {
            return Team.BLACK;
        }

        @Override
        public Collection<Piece> getPieces() {
            return this.board.getBlackPlayerPieces();
        }
    }
}
