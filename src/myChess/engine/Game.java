package myChess.engine;

import java.util.Collection;

public class Game {

    protected Collection<Board> previousBoards;
    protected Board currentBoard;

    public Game() {

    }

    public Collection<Board> getPreviousBoards() {
        return previousBoards;
    }

    public Board getCurrentBoard() {
        return currentBoard;
    }

    public void setPreviousBoards(Collection<Board> previousBoards) {
        this.previousBoards = previousBoards;
    }

    public void setCurrentBoard(Board currentBoard) {
        this.currentBoard = currentBoard;
    }
}
