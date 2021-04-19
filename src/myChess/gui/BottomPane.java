package myChess.gui;

import javafx.scene.layout.StackPane;

public class BottomPane extends StackPane {

    public BottomPane() {

        this.setPrefSize(1200, 150);
        this.setStyle("-fx-padding: 15;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #282828;" +
                "-fx-background-color: #606060;");
    }
}
