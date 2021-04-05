package myChess.engine;

import myChess.piece.Piece;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Utils {

    //these variables are not used currently
    public static final int A_FILE = 0;
    public static final int B_FILE = 1;
    public static final int C_FILE = 2;
    public static final int D_FILE = 3;
    public static final int E_FILE = 4;
    public static final int F_FILE = 5;
    public static final int G_FILE = 6;
    public static final int H_FILE = 7;
    public static final int EIGHTH_RANK = 0;
    public static final int SEVENTH_RANK = 1;
    public static final int SIXTH_RANK = 2;
    public static final int FIFTH_RANK = 3;
    public static final int FOURTH_RANK = 4;
    public static final int THIRD_RANK = 5;
    public static final int SECOND_RANK = 6;
    public static final int FIRST_RANK = 7;

    private Utils() {
        throw new RuntimeException("You cannot instantiate me!");
    }

    public static String convertToFile(int num) {
        return switch (num) {
            case 0 -> "A";
            case 1 -> "B";
            case 2 -> "C";
            case 3 -> "D";
            case 4 -> "E";
            case 5 -> "F";
            case 6 -> "G";
            case 7 -> "H";
            default -> "";
        };
    }

    public static Collection<Move> calculatePawnAttackHelper(Board board, int xOff, int yOff, Piece piece) {
        //For pawns
        //helper function to reduce code and make it easier to read
        final List<Move> legalMovesHelper = new ArrayList<>();

        //todo check if move leaves king in check
        //try/catch to know if we are on the board or not
        try {
            int x = piece.getPieceXPosition() + xOff;
            int y = piece.getPieceYPosition() + piece.getPlayerTeam().getOffset() * yOff;
            if (board.getSquare(x, y).isOccupied()) {
                if (board.getSquare(x, y).getPiece().getPlayerTeam() != piece.getPlayerTeam()) {
                    legalMovesHelper.add(new Move.AttackMove(board, x, y, piece, board.getSquare(x,y).getPiece()));
                }
            }
        } catch (IndexOutOfBoundsException ignored) {

        }
        return legalMovesHelper;
    }

    public static Collection<Move> calculateMovesHelper(Board board, int xOff, int yOff, Piece piece) {
        //For pieces that don't move far
        //helper function to reduce code and make it easier to read
        final List<Move> legalMovesHelper = new ArrayList<>();

        //todo check if move leaves king in check
        //try/catch to know if we are on the board or not
        try {
            int x = piece.getPieceXPosition() + xOff;
            int y = piece.getPieceYPosition() + piece.getPlayerTeam().getOffset() * yOff;
            if (board.getSquare(x, y).isOccupied()) {
                if (board.getSquare(x, y).getPiece().getPlayerTeam() != piece.getPlayerTeam()) {
                    legalMovesHelper.add(new Move.AttackMove(board, x, y, piece, board.getSquare(x,y).getPiece()));
                }
            } else {
                legalMovesHelper.add(new Move.NormalMove(board, x, y, piece));
            }
        } catch (IndexOutOfBoundsException ignored){

        }
        return legalMovesHelper;
    }

    public static Collection<Move> calculateMovesHelper(Board board, int xOff, int yOff, int distance, Piece piece) {
        //For Bishops, queen, and rook
        //helper function to reduce code and make it easier to read
        final List<Move> legalMovesHelper = new ArrayList<>();

        //todo check if move leaves king in check
        for (int i = 1; i < distance; i++) {
            //try/catch to know if we are on the board or not
            try {
                int x = piece.getPieceXPosition() + i * xOff;
                int y = piece.getPieceYPosition() + i * piece.getPlayerTeam().getOffset() * yOff;
                if (board.getSquare(x, y).isOccupied()) {
                    if (board.getSquare(x, y).getPiece().getPlayerTeam() != piece.getPlayerTeam()) {
                        legalMovesHelper.add(new Move.AttackMove(board, x, y, piece, board.getSquare(x,y).getPiece()));
                    }
                    break;
                } else {
                    legalMovesHelper.add(new Move.NormalMove(board, x, y, piece));
                }
            } catch (IndexOutOfBoundsException e){
                break;
            }
        }
        return legalMovesHelper;
    }
}
