package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Knight extends Piece {

    public Knight(Team playerType,
                  final int pieceXPosition,
                  final int pieceYPosition,
                  final boolean isFirstMove) {
        super(PieceType.KNIGHT, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.KNIGHT.toString();
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 2,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 2, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 2, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, -2,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, -2,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -2, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -2, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 2,this));

        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        return new Knight(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }

    @Override
    public Piece movePiece(Move move, boolean isPromote) {
        return new Knight(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }
}
