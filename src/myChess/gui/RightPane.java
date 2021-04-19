package myChess.gui;

import javafx.scene.layout.VBox;

public class RightPane extends VBox {

    public RightPane() {
        this.setMinSize(150, 900);
        this.setStyle("-fx-padding: 15;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 0;" +
                "-fx-border-radius: 0;" +
                "-fx-border-color: #282828;" +
                "-fx-background-color: #606060;");
    }
}
