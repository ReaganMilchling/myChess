package myChess.engine;

import myChess.piece.*;
import myChess.player.Player;
import myChess.player.Team;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Board {

    protected final Square[][] gameBoard;
    protected final STATE gameState;
    protected final Collection<Piece> BlackPlayerPieces;
    protected final Collection<Piece> WhitePlayerPieces;

    protected final Collection<Move> BlackPlayerLegalMoves;
    protected final Collection<Move> WhitePlayerLegalMoves;

    protected final Player.WhitePlayer whitePlayer;
    protected final Player.BlackPlayer blackPlayer;
    protected final Player currentPlayer;

    public Board() {
        //this class creates an entirely new board
        this.gameBoard = createBoard();
        this.BlackPlayerPieces = calculatePieces(this.gameBoard, Team.BLACK);
        this.WhitePlayerPieces = calculatePieces(this.gameBoard, Team.WHITE);
        this.BlackPlayerLegalMoves = calculateLegalMoves(BlackPlayerPieces);
        this.WhitePlayerLegalMoves = calculateLegalMoves(WhitePlayerPieces);
        this.whitePlayer = new Player.WhitePlayer(this, WhitePlayerLegalMoves, BlackPlayerLegalMoves, WhitePlayerPieces, BlackPlayerPieces);
        this.blackPlayer = new Player.BlackPlayer(this, BlackPlayerLegalMoves, WhitePlayerLegalMoves, BlackPlayerPieces, WhitePlayerPieces);
        this.currentPlayer = whitePlayer;
        this.gameState = STATE.PLAYING;
    }

    public Board(BoardBuilder builder) {
        //this class creates a new board based off the prior board in BoardBuilder
        this.gameBoard = createNewBoard(builder);
        this.BlackPlayerPieces = calculatePieces(this.gameBoard, Team.BLACK);
        this.WhitePlayerPieces = calculatePieces(this.gameBoard, Team.WHITE);
        this.BlackPlayerLegalMoves = calculateLegalMoves(BlackPlayerPieces);
        this.WhitePlayerLegalMoves = calculateLegalMoves(WhitePlayerPieces);
        this.whitePlayer = new Player.WhitePlayer(this, WhitePlayerLegalMoves, BlackPlayerLegalMoves, WhitePlayerPieces, BlackPlayerPieces);
        this.blackPlayer = new Player.BlackPlayer(this, BlackPlayerLegalMoves, WhitePlayerLegalMoves, BlackPlayerPieces, WhitePlayerPieces);
        if (builder.getPlayersTurn().isWhite()) {
            this.currentPlayer = whitePlayer;
        } else {
            this.currentPlayer = blackPlayer;
        }
        this.gameState = calculateGameState();
    }

    public Board(BoardBuilder builder, boolean isWhite) {
        //this is solely for testing checks
        this.gameBoard = createNewBoard(builder);
        this.BlackPlayerPieces = calculatePieces(this.gameBoard, Team.BLACK);
        this.WhitePlayerPieces = calculatePieces(this.gameBoard, Team.WHITE);
        this.BlackPlayerLegalMoves = calculateLegalMoves(BlackPlayerPieces);
        this.WhitePlayerLegalMoves = calculateLegalMoves(WhitePlayerPieces);
        this.whitePlayer = new Player.WhitePlayer(this, WhitePlayerLegalMoves, BlackPlayerLegalMoves, WhitePlayerPieces, BlackPlayerPieces, true);
        this.blackPlayer = new Player.BlackPlayer(this, BlackPlayerLegalMoves, WhitePlayerLegalMoves, BlackPlayerPieces, WhitePlayerPieces, true);
        if (isWhite) {
            this.currentPlayer = whitePlayer;
        } else {
            this.currentPlayer = blackPlayer;
        }
        this.gameState = STATE.PLAYING;
    }

    //getters
    public STATE getGameState() {
        return this.gameState;
    }

    public Player getWhitePlayer() {
        return this.whitePlayer;
    }

    public Player getBlackPlayer() {
        return this.blackPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public Collection<Piece> getWhitePlayerPieces() {
        return this.WhitePlayerPieces;
    }

    public Collection<Piece> getBlackPlayerPieces() {
        return this.BlackPlayerPieces;
    }

    public Square[][] getBoard() {
        return this.gameBoard;
    }

    public Square getSquare(int x, int y) {
        return gameBoard[x][y];
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s.append(String.format("%3s", gameBoard[j][i].toString()));
            }
            s.append("\n");
        }
        return s.toString();
    }

    public STATE calculateGameState() {
        if (currentPlayer.getLegalMoves().isEmpty()) {
            if (currentPlayer.getTeam().isWhite() && currentPlayer.isInCheck()) {
                System.out.println(currentPlayer.isInCheck());
                return STATE.BLACKCHECKMATE;
            } else if (currentPlayer.getTeam().isBlack() && currentPlayer.isInCheck()) {
                System.out.println(currentPlayer.isInCheck());
                return STATE.WHITECHECKMATE;
            } else {
                return STATE.STALEMATE;
            }
        }
        return STATE.PLAYING;
    }

    private Collection<Piece> calculatePieces(Square[][] board, Team player) {
        final List<Piece> pieces = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[j][i].isOccupied()) {
                    final Piece piece = board[j][i].getPiece();
                    if (piece.getPlayerTeam() == player) {
                        pieces.add(piece);
                    }
                }
            }
        }
        return pieces;
    }

    private Collection<Move> calculateLegalMoves(Collection<Piece> pieces) {
        final List<Move> moves = new ArrayList<>();
        for (Piece piece : pieces) {
            moves.addAll(piece.calculateMoves(this));
        }
        return moves;
    }

    private Square[][] createBoard() {
        Square[][] board = new Square[8][8];
        board[0][0] = new Square(new Rook(Team.BLACK, 0, 0, true));
        board[1][0] = new Square(new Knight(Team.BLACK, 1, 0, true));
        board[2][0] = new Square(new Bishop(Team.BLACK, 2, 0, true));
        board[3][0] = new Square(new Queen(Team.BLACK, 3, 0, true));
        board[4][0] = new Square(new King(Team.BLACK, 4, 0, true));
        board[5][0] = new Square(new Bishop(Team.BLACK, 5, 0, true));
        board[6][0] = new Square(new Knight(Team.BLACK, 6, 0, true));
        board[7][0] = new Square(new Rook(Team.BLACK, 7, 0, true));

        board[0][7] = new Square(new Rook(Team.WHITE,0, 7, true));
        board[1][7] = new Square(new Knight(Team.WHITE, 1, 7, true));
        board[2][7] = new Square(new Bishop(Team.WHITE,2, 7, true));
        board[3][7] = new Square(new Queen(Team.WHITE,3, 7, true));
        board[4][7] = new Square(new King(Team.WHITE, 4, 7, true));
        board[5][7] = new Square(new Bishop(Team.WHITE, 5, 7, true));
        board[6][7] = new Square(new Knight(Team.WHITE, 6, 7, true));
        board[7][7] = new Square(new Rook(Team.WHITE, 7, 7, true));

        for (int i = 0; i < 8; i++) {
            //adding pawns
            board[i][1] = new Square(new Pawn(Team.BLACK, i, 1, true));
            board[i][6] = new Square(new Pawn(Team.WHITE, i, 6, true));
            //setting all other squares to null
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    board[i][j] = new Square(new Empty(i, j));
                }
            }
        }
        return board;
    }

    private Square[][] createNewBoard(BoardBuilder builder) {
        return builder.getChessBoard();
    }

    public enum STATE {
        STALEMATE() {
            @Override
            public boolean isGameOver() {
                return true;
            }

            @Override
            public boolean isCheckmate() {
                return false;
            }
        },
        WHITECHECKMATE() {
            @Override
            public boolean isGameOver() {
                return true;
            }

            @Override
            public boolean isCheckmate() {
                return true;
            }
        },
        BLACKCHECKMATE() {
            @Override
            public boolean isGameOver() {
                return true;
            }

            @Override
            public boolean isCheckmate() {
                return true;
            }
        },
        DRAW() {
            @Override
            public boolean isGameOver() {
                return true;
            }

            @Override
            public boolean isCheckmate() {
                return false;
            }
        },
        PLAYING() {
            @Override
            public boolean isGameOver() {
                return false;
            }

            @Override
            public boolean isCheckmate() {
                return false;
            }
        };

        public abstract boolean isGameOver();
        public abstract boolean isCheckmate();
    }
}