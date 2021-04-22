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
        this.legalMoves = doesMoveLeaveInCheck(moves);
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
        //System.out.println(board.toString());
        this.king = findKing();
        this.opponentLegalMoves = oppMoves;
        this.legalMoves = moves;
    }

    public Collection<Move> doesMoveLeaveInCheck(Collection<Move> moves) {
        //checks the incoming moves and returns only the moves that do not leave the players kind in check
        final List<Move> updatedMoves = new ArrayList<>();
        boolean seesKing = false;
        for (Move move : moves) {

            if (!move.getMovedPiece().getPieceType().isKing() && !isCheck(move)) {
                Board test = move.testForChecks(getPieces(), getOpponentPieces());
                Collection<Move> newOppMoves = test.getCurrentPlayer().getOpponent().getLegalMoves();
                for (Move a : newOppMoves) {
                    if (a instanceof Move.AttackMove) {
                        Move.AttackMove b = (Move.AttackMove)a;
                        if (b.getAttackedPiece().getPieceType().isKing()) {
                            seesKing = true;
                        }
                    }
                }
                if (seesKing) {
                    seesKing = false;
                } else {
                    updatedMoves.add(move);
                }
            } else if (move.getMovedPiece().getPieceType().isKing()) {
                if (kingNotInCheck(this.board, move, this.king)) {
                    updatedMoves.add(move);
                }
            } else {
                updatedMoves.add(move);
            }
        }
        return updatedMoves;
    }

    public static boolean kingNotInCheck(Board board, Move move, Piece king) {
        //finds out whether a king can move to a certain square
        Board test;
        if (king.getPlayerTeam().isBlack()) {
            test = move.testForChecks(board.getBlackPlayerPieces(), board.getWhitePlayerPieces());
        } else {
            test = move.testForChecks(board.getWhitePlayerPieces(), board.getBlackPlayerPieces());
        }

        Collection<Move> newOppMoves = test.getCurrentPlayer().getOpponent().getLegalMoves();
        for (Move a : newOppMoves) {
            if (a instanceof Move.AttackMove) {
                Move.AttackMove b = (Move.AttackMove)a;
                if (b.getAttackedPiece().getPieceType().isKing()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isCheck(Move move) {
        //finds out whether a move is a check
        if (move instanceof Move.AttackMove) {
            Move.AttackMove m = (Move.AttackMove) move;
            //attacked pieces can only be opposite team, no need to check if opposite team
            return m.getAttackedPiece().getPieceType().isKing();
        }
        return false;
    }

    private Piece findKing() {
        //finds the players king
        for (Piece p : this.playerPieces) {
            if (p.getPieceType().isKing()) {
                return p;
            }
        }
        throw new RuntimeException("Illegal Game State");
    }

    public boolean isMoveLegal(final Move move) {
        return this.legalMoves.contains(move);
    }

    //getters
    public Collection<Move> getLegalMoves() {
        return this.legalMoves;
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

    //abstract classes
    public abstract Player getOpponent();
    public abstract Team getTeam();

    //player classes
    public static class WhitePlayer extends Player{

        public WhitePlayer(final Board board,
                           final Collection<Move> moves,
                           final Collection<Move> oppMoves,
                           final Collection<Piece> pieces,
                           final Collection<Piece> oppPieces) {
            super(board, moves, oppMoves, pieces, oppPieces);
        }

        public WhitePlayer(final Board board,
                           final Collection<Move> moves,
                           final Collection<Move> oppMoves,
                           final Collection<Piece> pieces,
                           final Collection<Piece> oppPieces,
                           final boolean isTest) {
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

        public BlackPlayer(final Board board,
                           final Collection<Move> moves,
                           final Collection<Move> oppMoves,
                           final Collection<Piece> pieces,
                           final Collection<Piece> oppPieces) {
            super(board, moves, oppMoves, pieces, oppPieces);

        }

        public BlackPlayer(final Board board,
                           final Collection<Move> moves,
                           final Collection<Move> oppMoves,
                           final Collection<Piece> pieces,
                           final Collection<Piece> oppPieces,
                           final boolean isTest) {
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
