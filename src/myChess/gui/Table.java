package myChess.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import myChess.engine.*;
import myChess.piece.Piece;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;

public class Table {

    private Board chessBoard;
    private BoardPane boardPane;

    private final int GUI_SIZE;
    private final int BOARD_SIZE;
    private final int SQUARE_SIZE;
    private final int IMAGE_SIZE;
    private final int DOT_SIZE;
    private final int FONT_SIZE;

    private Square selectedSquare;
    private Square destinationSquare;
    private Piece movedPiece;
    private Piece destinationPiece;

    private final String lightColor;
    private final String darkColor;

    public Table() {
        this.chessBoard = new Board();
        this.GUI_SIZE = 1200;
        this.BOARD_SIZE = 900;
        this.SQUARE_SIZE = BOARD_SIZE / 8;
        this.IMAGE_SIZE = (int)(SQUARE_SIZE * 0.8);
        this.DOT_SIZE = IMAGE_SIZE / 5;
        this.FONT_SIZE = BOARD_SIZE / 50;
        this.lightColor = "#FFFACD";
        this.darkColor = "#6B8E23";
    }

    public Parent createContent() {

        BorderPane root = new BorderPane();
        this.boardPane = new BoardPane();
        root.setCenter(boardPane);

        root.setPrefSize(GUI_SIZE, GUI_SIZE);

        return root;
    }

    private class BoardPane extends GridPane {
        BoardPane() {
            setPadding(new Insets(10, 10, 10, 10));
            setVgap(0);
            setHgap(0);
            setAlignment(Pos.CENTER);
            setPrefSize(BOARD_SIZE, BOARD_SIZE);
            drawBoard(chessBoard);
        }

        public void drawBoard(final Board board) {
            this.getChildren().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    final SquarePane squarePane = new SquarePane(i, j);
                    this.add(squarePane, i, j, 1, 1);
                    if (i == 0) {
                        Text text = new Text(String.valueOf(8 - j));
                        squarePane.getChildren().add(text);
                        if (0 == j % 2) {
                            text.setFill(Paint.valueOf(darkColor));
                        } else {
                            text.setFill(Paint.valueOf(lightColor));
                        }
                        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE));
                        StackPane.setAlignment(text, Pos.TOP_LEFT);
                    }
                    if (j == 7) {
                        Text text = new Text(Utils.convertToFile(i));
                        squarePane.getChildren().add(text);
                        if (i % 2 == j % 2) {
                            text.setFill(Paint.valueOf(darkColor));
                        } else {
                            text.setFill(Paint.valueOf(lightColor));
                        }
                        text.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, FONT_SIZE));
                        StackPane.setAlignment(text, Pos.BOTTOM_RIGHT);
                    }

                }
            }

        }
    }

    private class SquarePane extends StackPane {

        final int xPos;
        final int yPos;

        SquarePane(int x, int y) {
            this.xPos = x;
            this.yPos = y;

            addEventFilter(MouseEvent.MOUSE_CLICKED, new javafx.event.EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent e) {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        if (selectedSquare == null) {
                            selectedSquare = chessBoard.getSquare(xPos, yPos);
                            movedPiece = selectedSquare.getPiece();
                            if (movedPiece.getPieceType().isEmpty()) {
                                selectedSquare = null;
                            }
                        } else if (chessBoard.getSquare(xPos, yPos).getPiece().getPlayerTeam() ==
                                   chessBoard.getCurrentPlayer().getTeam()) {
                            selectedSquare = chessBoard.getSquare(xPos, yPos);
                            movedPiece = selectedSquare.getPiece();
                        } else {
                            destinationSquare = chessBoard.getSquare(xPos, yPos);
                            destinationPiece = destinationSquare.getPiece();
                            MoveFactory mf = new MoveFactory(chessBoard,
                                                             movedPiece,
                                                             destinationSquare.getPiece().getPieceXPosition(),
                                                             destinationSquare.getPiece().getPieceYPosition(),
                                                             destinationPiece);
                            chessBoard = mf.getTransitionBoard();
                            selectedSquare = null;
                            destinationSquare = null;
                            movedPiece = null;

                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                boardPane.drawBoard(chessBoard);
                            }
                        });
                    }
                }
            });
            setPrefSize(SQUARE_SIZE, SQUARE_SIZE);
            drawTile(chessBoard);
        }

        public void drawTile(final Board board) {
            setColor();
            assignImage(board);
            highlightLegalMoves(board);
        }

        public void highlightLegalMoves(Board board) {
            for (final Move move : getLegalMoves(board)){
                if (move.getDestinationXPos() == this.xPos && move.getDestinationYPos() == this.yPos) {
                    try {
                        BufferedImage image = ImageIO.read(new File("res/greendot.png"));
                        WritableImage img = SwingFXUtils.toFXImage(image, null);
                        ImageView imageView = new ImageView(img);
                        imageView.setFitHeight(DOT_SIZE);
                        imageView.setFitWidth(DOT_SIZE);
                        this.getChildren().add(imageView);
                        setAlignment(imageView, Pos.CENTER);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private Collection<Move> getLegalMoves(final Board board) {
            if (movedPiece != null) {
                if (movedPiece.getPieceType() != Piece.PieceType.EMPTY &&
                    movedPiece.getPlayerTeam() == board.getCurrentPlayer().getTeam()) {
                    return movedPiece.calculateMoves(board);
                }
            }
            return Collections.emptyList();
        }

        public void setColor() {
            if (xPos % 2 == yPos % 2) {
                this.setStyle("-fx-background-color: " + lightColor);
            } else {
                this.setStyle("-fx-background-color: " + darkColor);
            }
        }
        public void assignImage(final Board board) {
            getChildren().clear();
            if (board.getSquare(xPos, yPos).isOccupied()) {
                try {
                    BufferedImage image = ImageIO.read(new File("res/images/" +
                            board.getSquare(xPos, yPos).getPiece().getPlayerTeam().toString().charAt(0) +
                            board.getSquare(xPos, yPos).getPiece().toString() + ".png"));
                    WritableImage img = SwingFXUtils.toFXImage(image, null);
                    ImageView imageView = new ImageView(img);
                    imageView.setFitHeight(IMAGE_SIZE);
                    imageView.setFitWidth(IMAGE_SIZE);
                    this.getChildren().add(imageView);
                    setAlignment(imageView, Pos.CENTER);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
