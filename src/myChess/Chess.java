package myChess;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import myChess.gui.Table;


public class Chess extends Application {
    public Table table = new Table();

    @Override
    public void start(Stage primaryStage) throws Exception {


        Scene scene = new Scene(table.createContent());

        primaryStage.setTitle("myChess");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
