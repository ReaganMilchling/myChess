package myChess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myChess.engine.Board;
import myChess.gui.Table;


public class Chess extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Table table = new Table();
        Scene scene = new Scene(table.createContent());

        Board board = new Board();
        //System.out.println(board.toString());

//        Collection<Move> moves = board.getSquare(1,1).getPiece().calculateMoves(board);
//        System.out.println(board.getSquare(1,1).getPiece().toStringPos());
//        if (moves != null) {System.out.println(moves.toString());}
//        Collection<Move> moves2 = board.getSquare(0,1).getPiece().calculateMoves(board);
//        System.out.println(moves2.toString());

        primaryStage.setTitle("myChess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
