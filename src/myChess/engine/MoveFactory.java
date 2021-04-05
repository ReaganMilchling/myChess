package myChess.engine;

import myChess.piece.Piece;

public class MoveFactory {
    private final Board transitionBoard;

    public MoveFactory(final Board board,
                       final Piece piece,
                       final int destinationX,
                       final int destinationY,
                       final Piece attackedPiece) {
        //sets the move type

        Move move;
        if (attackedPiece.getPieceType() == Piece.PieceType.EMPTY) {
            move = new Move.NormalMove(board, destinationX, destinationY, piece);
        } else {
            move = new Move.AttackMove(board, destinationX, destinationY, piece, attackedPiece);
        }
        if (board.getCurrentPlayer().isMoveLegal(move)) {
            //makes the move and sets it equal to transition board
            this.transitionBoard = move.execute();
            //todo log the move to stack
        } else {
            //resets the transition board to the original board
            //boards are meant to be immutable
            this.transitionBoard = board;
        }
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }
}
