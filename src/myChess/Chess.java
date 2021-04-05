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

        primaryStage.setTitle("myChess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
