package myChess.engine;

public class MoveFactory {
    private final Board transitionBoard;

    public MoveFactory(final Board board,
                       final Move move) {
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
