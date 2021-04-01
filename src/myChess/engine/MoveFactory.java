package myChess.engine;

public class MoveFactory {
    //todo refactor this class
    private final Board transitionBoard;
    private final Move move;
    private final MoveStatus moveStatus;

    public MoveFactory(final Board board,
                       final Move move,
                       final MoveStatus moveStatus) {
        this.transitionBoard = board;
        this.move = move;
        this.moveStatus = moveStatus;
    }

    public static Move createMove(final Board board,
                                  final int currentX,
                                  final int currentY,
                                  final int destinationX,
                                  final int destinationY) {
        for (final Move move : board.getAllLegalMoves()) {
            if (move.getDestinationYPos() == destinationY &&
                    move.getDestinationXPos() == destinationX &&
                    move.getCurrentYPos() == currentY &&
                    move.getCurrentXPos() == currentX) {
                return move;
            }
        }
        return new Move.NullMove();
    }

    public MoveStatus getMoveStatus() {
        return this.moveStatus;
    }

    public Board getTransitionBoard() {
        return this.transitionBoard;
    }

    public enum MoveStatus {
        DONE{
            @Override
            public boolean isDone() {
                return true;
            }
        },
        ILLEGAL{
            @Override
            public boolean isDone() {
                return false;
            }
        };

        public abstract boolean isDone();
    }
}
