package myChess.gui;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Table {
    //This class is a mess, forgive me I am not a UI programmer

    private Board chessBoard;
    private chessPane centerPane;

    private final int GUI_SIZE;
    private final int BOARD_SIZE;
    private final int SQUARE_SIZE;
    private final int IMAGE_SIZE;
    private final int DOT_SIZE;
    private final int FONT_SIZE;

    private int BoardDirection;

    private Square selectedSquare;
    private Square destinationSquare;
    private Piece movedPiece;
    private Piece destinationPiece;
    private Collection<Square> redSquares = new ArrayList<>();

    private final String lightColor;
    private final String darkColor;
    private final String lightRedColor;
    private final String darkRedColor;

    public Table() {
        this.chessBoard = new Board();
        this.BoardDirection = 1;
        this.GUI_SIZE = 1200;
        this.BOARD_SIZE = 920;
        this.SQUARE_SIZE = BOARD_SIZE / 8;
        this.IMAGE_SIZE = (int)(SQUARE_SIZE * 0.8);
        this.DOT_SIZE = IMAGE_SIZE / 5;
        this.FONT_SIZE = BOARD_SIZE / 50;
        this.lightColor = "#FFFACD";
        this.darkColor = "#6B8E23";
        this.lightRedColor = "#FA8072";
        this.darkRedColor = "#B22222";
    }

    public Parent createContent() {

        BorderPane root = new BorderPane();
        this.centerPane = new chessPane();
        TopPane topPane = new TopPane();
        BottomPane bottomPane = new BottomPane();
        LeftPane leftPane = new LeftPane();
        RightPane rightPane = new RightPane();
        root.setCenter(this.centerPane);
        root.setTop(topPane);
        root.setBottom(bottomPane);
        root.setLeft(leftPane);
        root.setRight(rightPane);
        root.setPrefSize(GUI_SIZE, GUI_SIZE);

        return root;
    }

    public class TopPane extends HBox {

        public TopPane() {
            createButtons();
            this.setPrefSize(1200, 150);
            this.setAlignment(Pos.BASELINE_CENTER);
            this.setStyle("-fx-padding: 15;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 0;" +
                    "-fx-border-radius: 0;" +
                    "-fx-border-color: #282828;" +
                    "-fx-background-color: #606060;");
        }

        private void createButtons() {
            Button restart = new Button("Restart");
            restart.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    chessBoard = new Board();
                    BoardDirection = 1;
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            centerPane.drawBoard();
                        }
                    });
                }
            });
            Button flipBoard = new Button("Flip Board");
            flipBoard.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                    BoardDirection *= -1;

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            centerPane.drawBoard();
                        }
                    });
                }
            });

            //buttons to view previous moves
            Button jumpBack = new Button("Jump to Start");
            jumpBack.setAlignment(Pos.CENTER_RIGHT);
            Button moveBack = new Button("Move back one");
            moveBack.setAlignment(Pos.CENTER_RIGHT);
            Button moveForward = new Button("Move forward one");
            moveForward.setAlignment(Pos.CENTER_RIGHT);
            Button jumpForward = new Button("Jump to end");
            jumpForward.setAlignment(Pos.CENTER_RIGHT);


            this.getChildren().addAll(restart, flipBoard, jumpBack, moveBack, moveForward, jumpForward);

        }
    }

    private class chessPane extends GridPane {
        chessPane() {
            setPadding(new Insets(10, 10, 10, 10));
            setVgap(0);
            setHgap(0);
            setAlignment(Pos.CENTER);
            setPrefSize(BOARD_SIZE, BOARD_SIZE);
            //setMaxSize(BOARD_SIZE, BOARD_SIZE);
            drawBoard();
            this.setStyle("-fx-padding: 0;" +
                    "-fx-border-style: solid inside;" +
                    "-fx-border-width: 2;" +
                    "-fx-border-insets: 0;" +
                    "-fx-border-radius: 0;" +
                    "-fx-border-color: #282828;" +
                    "-fx-background-color: #606060;");
        }

        public void drawBoard() {
            this.getChildren().clear();
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    final SquarePane squarePane = new SquarePane(i, j);
                    int abcdOffset = 7;
                    if (BoardDirection == 1) {
                        this.add(squarePane, i, j, 1, 1);
                        abcdOffset = 7;
                    } else if (BoardDirection == -1) {
                        this.add(squarePane, i, 8-j, 1, 1);
                        abcdOffset = 0;
                    }
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
                    if (j == abcdOffset) {
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
                    if (!redSquares.isEmpty()) {
                        for (Square square : redSquares) {
                            if (square.getPiece().getPieceXPosition() == i) {
                                if (square.getPiece().getPieceYPosition() == j) {
                                    squarePane.setRedSquare();
                                }
                            }
                        }
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
                            redSquares.clear();
                            selectedSquare = chessBoard.getSquare(xPos, yPos);
                            movedPiece = selectedSquare.getPiece();
                            if (movedPiece.getPieceType().isEmpty()) {
                                selectedSquare = null;
                            }
                        }
                        else if (selectedSquare.getPiece().getPieceXPosition() == xPos &&
                                selectedSquare.getPiece().getPieceYPosition() == yPos) {
                            selectedSquare = null;
                            destinationSquare = null;
                            movedPiece = null;
                        }
                        else if (chessBoard.getSquare(xPos, yPos).getPiece().getPlayerTeam() ==
                                   chessBoard.getCurrentPlayer().getTeam()) {
                            redSquares.clear();
                            selectedSquare = chessBoard.getSquare(xPos, yPos);
                            movedPiece = selectedSquare.getPiece();
                        }
                        else {
                            redSquares.clear();
                            destinationSquare = chessBoard.getSquare(xPos, yPos);
                            destinationPiece = destinationSquare.getPiece();
                            //creates the move
                            MoveFactory mf = new MoveFactory(chessBoard,
                                                 getMove(chessBoard, selectedSquare.getPiece(), destinationPiece));
                            chessBoard = mf.getTransitionBoard();

                            selectedSquare = null;
                            destinationSquare = null;
                            movedPiece = null;

                        }
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                centerPane.drawBoard();
                            }
                        });
                    }
                    if (e.getButton() == MouseButton.SECONDARY) {
                        //right click to add red squares and deselect all
                        redSquares.add(chessBoard.getSquare(xPos, yPos));
                        selectedSquare = null;
                        destinationSquare = null;
                        movedPiece = null;

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                centerPane.drawBoard();
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
            setImage(board);
            highlightLegalMoves(board);
        }

        public Move getMove(final Board board, Piece start, Piece destination) {
            //goes through legal moves of the piece specified and looks for the correct destination
            for (final Move move : getPieceLegalMoves(board)){
                if (move.getMovedPiece().equals(start) &&
                        move.getDestinationXPos() == destination.getPieceXPosition() &&
                        move.getDestinationYPos() == destination.getPieceYPosition()) {
                    return move;
                }
            }
            return null;
        }

        public void highlightLegalMoves(Board board) {
            for (final Move move : getPieceLegalMoves(board)){
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

        private Collection<Move> getPieceLegalMoves(final Board board) {
            List<Move> legalMoves = new ArrayList<>();
            if (movedPiece != null) {
                if (movedPiece.getPieceType() != Piece.PieceType.EMPTY &&
                    movedPiece.getPlayerTeam() == board.getCurrentPlayer().getTeam()) {
                    List<Move> moves = new ArrayList<>(movedPiece.calculateMoves(board));
                    for (Move m : moves) {
                        if (board.getCurrentPlayer().isMoveLegal(m)) {
                            legalMoves.add(m);
                        }
                    }
                }
            }
            return legalMoves;
        }

        public void setRedSquare() {
            if (xPos % 2 == yPos % 2) {
                this.setStyle("-fx-background-color: " + lightRedColor);
            } else {
                this.setStyle("-fx-background-color: " + darkRedColor);
            }
        }

        public void setColor() {
            if (xPos % 2 == yPos % 2) {
                this.setStyle("-fx-background-color: " + lightColor);
            } else {
                this.setStyle("-fx-background-color: " + darkColor);
            }
        }

        public void setImage(final Board board) {
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
