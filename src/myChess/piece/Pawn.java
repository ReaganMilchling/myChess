package myChess.piece;

import myChess.engine.Utils;
import myChess.player.Team;
import myChess.engine.Board;
import myChess.engine.Move;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Pawn extends Piece {

    public Pawn(Team playerType,
                final int pieceXPosition,
                final int pieceYPosition,
                final boolean isFirstMove) {
        super(PieceType.PAWN, playerType, pieceXPosition, pieceYPosition, isFirstMove);
    }

    @Override
    public String toString() {
        return PieceType.PAWN.toString();
    }

    @Override
    public Collection<Move> calculateMoves(Board board) {
        final List<Move> legalMoves = new ArrayList<>();

        //normal movement
        try {
            int x = this.getPieceXPosition();
            int y = this.getPieceYPosition() + this.getPlayerTeam().getOffset();
            if (!board.getSquare(x, y).isOccupied()) {
                legalMoves.add(new Move.NormalMove(board, x, y, this));
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        //attacking moves
        legalMoves.addAll(Utils.calculatePawnAttackHelper(board, -1, 1, this));
        legalMoves.addAll(Utils.calculatePawnAttackHelper(board, 1, 1, this));
        //pawn jump
        if (this.isFirstMove) {
            //try/catch to know if we are on the board or not
            try {
                int x = this.getPieceXPosition();
                int y = this.getPieceYPosition() + this.getPlayerTeam().getOffset() * 2;
                int y1 = this.getPieceYPosition() + this.getPlayerTeam().getOffset();
                if (!board.getSquare(x, y1).isOccupied()) {
                    if (!board.getSquare(x, y).isOccupied()) {
                        legalMoves.add(new Move.NormalMove(board, x, y, this));
                    }
                }
            } catch (IndexOutOfBoundsException ignored){

            }
        }
        //todo add en passant attack move here
        return legalMoves;
    }

    @Override
    public Piece movePiece(Move move) {
        if (move.getDestinationYPos() == 0 || move.getDestinationYPos() == 7) {
            //todo add horse as well as queen
            return new Queen(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
        } else {
            return new Pawn(move.getMovedPiece().playerTeam, move.getDestinationXPos(), move.getDestinationYPos(), false);
        }
    }
}