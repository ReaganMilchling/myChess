package myChess.piece;

import myChess.engine.Board;
import myChess.engine.Move;
import myChess.engine.Utils;
import myChess.player.Player;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class King extends Piece {

    public King(Team playerType,
                final int pieceXPosition,
                final int pieceYPosition,
                final boolean isFirstMove) {
        super(PieceType.KING, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.KING.toString();
    }

    @Override
    public Collection<Move> calculateMoves(final Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        //straight
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 0, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 0,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 0,this));
        //diagonals
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, 1, -1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, 1,this));
        legalMoves.addAll(Utils.calculateMovesHelper(board, -1, -1,this));
        //king side
        legalMoves.addAll(calculateKingSideCastle(board, this));
        //queen side
        legalMoves.addAll(calculateQueenSideCastle(board, this));

        return legalMoves;
    }

    public Collection<Move> calculateKingSideCastle(final Board board, final Piece king) {
        final List<Move> legalMovesHelper = new ArrayList<>();
        int x = king.getPieceXPosition();
        int y = king.getPieceYPosition();

        try {
            if (king.isFirstMove()){
                final Piece rook = board.getSquare(x + 3, y).getPiece();
                if (rook.getPieceType().isRook() && rook.isFirstMove()) {
                    if (!board.getSquare(x + 1, y).isOccupied() && !board.getSquare(x + 2, y).isOccupied()) {
                        if (Player.kingNotInCheck(board, new Move.NormalMove(board, x, y, king), king)) {
                            if (Player.kingNotInCheck(board, new Move.NormalMove(board, x + 1, y, king), king) &&
                                    Player.kingNotInCheck(board, new Move.NormalMove(board, x + 2, y, king), king)) {
                                legalMovesHelper.add(new Move.CastleMove(board, x + 2, y, king, rook));
                            }
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored){

        }
        return legalMovesHelper;
    }

    public Collection<Move> calculateQueenSideCastle(final Board board, final Piece king) {
        final List<Move> legalMovesHelper = new ArrayList<>();
        int x = king.getPieceXPosition();
        int y = king.getPieceYPosition();

        try {
            if (king.isFirstMove()){
                final Piece rook = board.getSquare(x - 4, y).getPiece();
                if (rook.getPieceType().isRook() && rook.isFirstMove()) {
                    if (!board.getSquare(x - 1, y).isOccupied() && !board.getSquare(x - 2, y).isOccupied() && !board.getSquare(x - 3, y).isOccupied()) {
                        if (Player.kingNotInCheck(board, new Move.NormalMove(board, x, y, king), king)) {
                            if (Player.kingNotInCheck(board, new Move.NormalMove(board, x - 1, y, king), king) &&
                                    Player.kingNotInCheck(board, new Move.NormalMove(board, x - 2, y, king), king) &&
                                    Player.kingNotInCheck(board, new Move.NormalMove(board, x - 3, y, king), king)) {
                                legalMovesHelper.add(new Move.CastleMove(board, x - 2, y, king, rook));
                            }
                        }
                    }
                }
            }
        } catch (IndexOutOfBoundsException ignored){

        }
        return legalMovesHelper;
    }

    @Override
    public Piece movePiece(Move move) {
        return new King(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
    }
}
