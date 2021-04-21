package myChess.player;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Player {

    protected final Board board;
    protected final Collection<Move> legalMoves;
    protected final Collection<Move> opponentLegalMoves;
    protected final Collection<Piece> playerPieces;
    protected final Collection<Piece> oppPieces;
    protected final boolean isInCheck;
    protected final Piece king;

    public Player(final Board board,
                  final Collection<Move> moves,
                  final Collection<Move> oppMoves,
                  final Collection<Piece> pieces,
                  final Collection<Piece> oppPieces) {
        this.board = board;
        this.playerPieces = pieces;
        this.oppPieces = oppPieces;
        this.king = findKing();
        this.opponentLegalMoves = oppMoves;
        this.isInCheck = !Player.calculateAttacksOnTile(getKing().getPieceXPosition(), getKing().getPieceYPosition(), oppMoves).isEmpty();
        //this.legalMoves = moves;
        this.legalMoves = doesMoveLeaveInCheck(board, moves, oppMoves);
    }

    public Player(final Board board,
                  final Collection<Move> moves,
                  final Collection<Move> oppMoves,
                  final Collection<Piece> pieces,
                  final Collection<Piece> oppPieces,
                  final boolean isTest) {
        //this class doesn't call the leaves king in check method
        this.board = board;
        this.playerPieces = pieces;
        this.oppPieces = oppPieces;
        this.king = findKing();
        this.opponentLegalMoves = oppMoves;
        this.legalMoves = moves;
        this.isInCheck = isTest;
    }

    //todo more to do here
    protected static Collection<Move> calculateAttacksOnTile(int x, int y, Collection<Move> moves) {
        final List<Move> attackMoves = new ArrayList<>();
        for (final Move move : moves) {
            if (x == move.getDestinationXPos() && y == move.getDestinationYPos()) {
                attackMoves.add(move);
            }
        }
        return attackMoves;
    }

    public Collection<Move> doesMoveLeaveInCheck(Board board, Collection<Move> moves, Collection<Move> oppMoves) {
        //checks the incoming moves and returns only the moves that do not leave the players kind in check
        final List<Move> updatedMoves = new ArrayList<>();
        boolean seesKing = false;
        for (Move move : moves) {

            if (!move.getMovedPiece().getPieceType().isKing()) {
                Board test = move.testForChecks(getPieces(), getOpponentPieces());
                Collection<Move> newOppMoves = test.getCurrentPlayer().getOpponent().getLegalMoves();
                for (Move a : newOppMoves) {
                    if (a instanceof Move.AttackMove) {
                        Move.AttackMove b = (Move.AttackMove)a;
                        if (b.getAttackedPiece().equals(this.getKing())) {
                            seesKing = true;
                        }
                    }
                }
                if (seesKing) {
                    seesKing = false;
                } else {
                    updatedMoves.add(move);
                }
            } else {
                updatedMoves.add(move);
            }
        }
        return updatedMoves;
    }

    private Piece findKing() {
        for (Piece p : this.playerPieces) {
            if (p.getPieceType().isKing()) {
                //System.out.println(p);
                return p;
            }
        }
        throw new RuntimeException("Illegal Game State");
    }

    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    public Piece getKing() {
        return king;
    }

    public Collection<Piece> getPieces(){
        return this.playerPieces;
    }

    public Collection<Piece> getOpponentPieces(){
        return this.oppPieces;
    }

    public abstract Player getOpponent();
    public abstract Team getTeam();

    public static class WhitePlayer extends Player{

        public WhitePlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves, Collection<Piece> pieces, Collection<Piece> oppPieces) {
            super(board, moves, oppMoves, pieces, oppPieces);

        }

        public WhitePlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves, Collection<Piece> pieces, Collection<Piece> oppPieces, boolean isTest) {
            super(board, moves, oppMoves, pieces, oppPieces, isTest);

        }

        @Override
        public Player getOpponent() {
            return this.board.getBlackPlayer();
        }

        @Override
        public Team getTeam() {
            return Team.WHITE;
        }
    }

    public static class BlackPlayer extends Player {

        public BlackPlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves, Collection<Piece> pieces, Collection<Piece> oppPieces) {
            super(board, moves, oppMoves, pieces, oppPieces);

        }

        public BlackPlayer(Board board, Collection<Move> moves, Collection<Move> oppMoves, Collection<Piece> pieces, Collection<Piece> oppPieces, boolean isTest) {
            super(board, moves, oppMoves, pieces, oppPieces, isTest);
        }

        @Override
        public Player getOpponent() {
            return this.board.getWhitePlayer();
        }

        @Override
        public Team getTeam() {
            return Team.BLACK;
        }
    }
}
